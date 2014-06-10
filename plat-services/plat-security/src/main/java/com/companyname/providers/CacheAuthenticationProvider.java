package com.companyname.providers;

import com.companyname.services.PlatUserAuthenticationCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.logging.Logger;

/**
 * Created by hmohamed on 6/10/14.
 */
@Service
public class CacheAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider
{

    private static final Logger logger =
            Logger.getLogger(CacheAuthenticationProvider.class.getName());

    @Autowired
    PlatUserAuthenticationCache cache;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        Assert.notNull(cache, "Platform Authentication Cache cannot be null");

        return cache.authenticateFromCache(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return null;
    }


}
