package com.companyname.providers;

import com.companyname.domain.InMemoryUser;
import com.companyname.extension.PlatAuthentication;
import com.companyname.services.PlatInMemoryUserDetailsService;
import com.companyname.services.PlatUserAuthenticationCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by hmohamed on 6/11/14.
 */
@Service
public class InMemoryAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider
{

    private static final Logger logger =
            Logger.getLogger(InMemoryAuthenticationProvider.class.getName());

    @Autowired
    @Qualifier("inMemoryUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    PlatUserAuthenticationCache cache;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException
    {
        logger.info("InMemory authentication provider starts");

        // Determine username and password
        String username = (authentication.getPrincipal() == null) ?
                "NONE_PROVIDED" : authentication.getName();

        String credentials = (authentication.getPrincipal() == null) ?
                "NONE_PROVIDED" : (String)authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        logger.info("In memory authenticating for user: " + authentication.getName() + " and password: " + authentication.getCredentials());

        if (userDetails == null ) {
            throw new UsernameNotFoundException(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "No User Found");
        }
        else if(!userDetails.getUsername().equals(authentication.getName()) || !userDetails.getPassword().equals(authentication.getCredentials())) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            //return null;
        } else {
            logger.info("user is authenticated successfully from the memory store");
            Authentication goodAuthentication
                    = new UsernamePasswordAuthenticationToken(username, credentials, userDetails.getAuthorities());
            // cache it
            logger.info("caching the user authentication token after success in memory login");
            cache.add(goodAuthentication);

            // build platform authentication object
            Authentication platformAuthentication = PlatAuthentication.getPlatAuthentication(authentication);
            ((PlatAuthentication)platformAuthentication).setUserCredentials(credentials);
            return platformAuthentication;
        }

    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException
    {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return userDetailsService.loadUserByUsername(username);
    }

    public void addUser(InMemoryUser user) {
        ((PlatInMemoryUserDetailsService)userDetailsService).addUser(user);
    }
}
