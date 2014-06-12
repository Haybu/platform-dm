package com.companyname.services;

/**
 * Created by hmohamed on 6/11/14.
 */

import java.net.URI;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


public class PlatRequestMatcher implements RequestMatcher, BeanNameAware
{
    private static final Log logger = LogFactory.getLog(PlatRequestMatcher.class);

    private static final String MATCH_ALL = "/**";

    private final Matcher matcher;

    private final String pattern;

    private final HttpMethod httpMethod;
    private String name;
    private String hostname;


    /**
     * Creates a matcher with the specific pattern which will match all HTTP methods.
     *
     * @param pattern the ant pattern to use for matching
     */
    public PlatRequestMatcher(String pattern) {
        this(pattern, null);
    }


    public PlatRequestMatcher(String pattern, String hostname) {
        this(pattern, null, hostname);
    }

    /**
     * Creates a matcher with the supplied pattern which will match all HTTP methods.
     *
     * @param pattern    the ant pattern to use for matching
     * @param httpMethod the HTTP method. The {@code matches} method will return false if the incoming request doesn't
     *                   have the same method.
     */
    public PlatRequestMatcher(String pattern, String httpMethod, String hostname)
    {
        Assert.hasText(pattern, "Pattern cannot be null or empty");

        if (pattern.equals(MATCH_ALL) || pattern.equals("**")) {
            pattern = MATCH_ALL;
            matcher = null;
        } else {
            pattern = pattern.toLowerCase();

            // If the pattern ends with {@code /**} and has no other wildcards, then optimize to a sub-path match
            if (pattern.endsWith(MATCH_ALL) && pattern.indexOf('?') == -1 &&
                    pattern.indexOf("*") == pattern.length() - 2) {
                matcher = new SubpathMatcher(pattern.substring(0, pattern.length() - 3));
            } else {
                matcher = new SpringAntMatcher(pattern);
            }
        }

        this.pattern = pattern;
        this.httpMethod = StringUtils.hasText(httpMethod) ? HttpMethod.valueOf(httpMethod) : null;
        this.hostname = hostname;
    }

    /**
     * Returns true if the configured pattern (and HTTP-Method) match those of the supplied request.
     *
     * @param request the request to match against. The ant pattern will be matched against the
     *                {@code servletPath} + {@code pathInfo} of the request.
     */
    public boolean matches(HttpServletRequest request) {

        if (httpMethod != null && httpMethod != HttpMethod.valueOf(request.getMethod())) {
            if (logger.isDebugEnabled()) {
                logger.debug("Request '" + request.getMethod() + " " + getRequestPath(request) + "'"
                        + " doesn't match '" + httpMethod + " " + pattern);
            }

            return false;
        }


        logger.info("Test to match configured host: " + this.getHostname());
        if (matchHostName(request)) {
            logger.info("request host name matches: " + this.getHostname());
            return true;
        }

        /**
        //try to match the url pattern
        if (matchPattern(request)) {
            logger.info("request pattern matches: " + this.getPattern());
            return true;
        }
         */

        /**
        if(matchHostName(request) && matchPattern(request)) {
            logger.info("request host name matches :" + this.getHostname() + " , and matches pattern: " + this.getPattern());
            return true;
        }
         */

        logger.info("request host name does NOT match: " + this.getHostname());
        return false;
    }

    private boolean matchPattern(HttpServletRequest request) {
        if (pattern != null) {

            String url = getRequestPath(request);

            if (pattern.equals(MATCH_ALL)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Request '" + url + "' matched by universal pattern '/**'");
                }

                return true;
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Checking match of request : '" + url + "'; against '" + pattern + "'");
            }

            boolean matched = matcher.matches(url);

            HttpSession session = request.getSession();

            if (matched) {
                return true;
            }
        }

        return false;
    }

    private boolean matchHostName(HttpServletRequest request) {
        // try to determine if we should match on hostname

         String requestServerName = request.getServerName(); //+":"+request.getServerPort();
         if (StringUtils.hasText(requestServerName) && StringUtils.hasText(this.getHostname())) {
             logger.info("current request host: " + requestServerName);
            if (this.getHostname().indexOf(requestServerName) > -1) {
                return true;
            }
         }

        return false;
    }

    private boolean matchHostName_original(HttpServletRequest request) {
        // try to determine if we should match on hostname
        if (this.hostname != null) {

            String hostheader = request.getHeader("host");
            if (StringUtils.hasText(hostheader)) {
                logger.info("host header: " + hostheader);

                try {
                    //for safety use a URI object to parse
                    URI uri = new URI("host://" + hostheader);
                    String host = uri.getHost();
                    if (StringUtils.hasText(host)) {
                        String[] parts = StringUtils.split(host, ".");
                        //there is a hostname to match against
                        if (parts != null) {
                            final String test = parts[0];
                            logger.info("test to match host part: " + test);
                            //PlatRequestMatcher validMatcher = requestMatcherHostnameMap.get(StringUtils.uncapitalize(test));
                            //if hostname matches this filter then we are good
                            if (test.equalsIgnoreCase(this.hostname)) {
                                return true;
                            }
                        }
                    }
                } catch (Exception e) {
                    //noop
                }
            }
        }
        return false;
    }

    private String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();

        if (request.getPathInfo() != null) {
            url += request.getPathInfo();
        }

        url = url.toLowerCase();

        return url;
    }

    public String getPattern() {
        return pattern;
    }

    public String getHostname() {
        return this.hostname;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlatRequestMatcher)) {
            return false;
        }
        PlatRequestMatcher other = (PlatRequestMatcher) obj;
        return this.pattern.equals(other.pattern) &&
                this.httpMethod == other.httpMethod;
    }

    @Override
    public int hashCode() {
        int code = 31 ^ pattern.hashCode();
        if (httpMethod != null) {
            code ^= httpMethod.hashCode();
        }
        return code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ant [pattern='").append(pattern).append("'");

        if (httpMethod != null) {
            sb.append(", ").append(httpMethod);
        }

        sb.append("]");

        return sb.toString();
    }

    private static interface Matcher {
        boolean matches(String path);
    }

    private static class SpringAntMatcher implements Matcher {
        private static final AntPathMatcher antMatcher = new AntPathMatcher();

        private final String pattern;

        private SpringAntMatcher(String pattern) {
            this.pattern = pattern;
        }

        public boolean matches(String path) {
            return antMatcher.match(pattern, path);
        }
    }

    /**
     * Optimized matcher for trailing wildcards
     */
    private static class SubpathMatcher implements Matcher {
        private final String subpath;
        private final int length;

        private SubpathMatcher(String subpath) {
            assert !subpath.contains("*");
            this.subpath = subpath;
            this.length = subpath.length();
        }

        public boolean matches(String path) {
            return path.startsWith(subpath) && (path.length() == length || path.charAt(length) == '/');
        }
    }

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

}
