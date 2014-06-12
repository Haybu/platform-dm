package com.companyname.handlers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * not in use
 *
 * Created by hmohamed on 6/12/14.
 */
public class PlatAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger =
            Logger.getLogger(PlatAccessDeniedHandler.class.getName());

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException
    {
        logger.info("access denied handler invoked.");
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        // if user logged in but not enough authorization
        if (authentication != null && authentication.isAuthenticated()) {
            logger.info("user still logged in. application at " + request.getContextPath()
                    + " will log this user out.");
            request.logout();
            //request.setAttribute("role_error", "unauthorized request");
        }
    }
}
