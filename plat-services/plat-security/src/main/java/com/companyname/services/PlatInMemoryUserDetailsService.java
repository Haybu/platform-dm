package com.companyname.services;

import com.companyname.domain.InMemoryUser;
import com.companyname.plat.repository.persistence.entity.GroupAuth;
import com.companyname.plat.repository.persistence.entity.GroupMember;
import com.companyname.plat.repository.persistence.entity.OrganizationGroup;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by hmohamed on 6/11/14.
 */
@Service("inMemoryUserDetailsService")
public class PlatInMemoryUserDetailsService  implements UserDetailsService
{

    private List<InMemoryUser> users = null;

    private static final Logger logger =
            Logger.getLogger(PlatInMemoryUserDetailsService.class.getName());

    public PlatInMemoryUserDetailsService() {
        users = new ArrayList<>();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InMemoryUser fromMemory = findUserByID(username);

        if (fromMemory == null) {
            return null;
        }

        UserDetails user = new User(username, fromMemory.getPassword()
                , true  // enabled?
                , true  // accountNonExpired?
                , true  // credentialsNonExpired?
                , true  // accountNonBlocked?
                , this.getUserAuth(fromMemory.getRoles()));   //Collection<? extends GrantedAuthority>

        return user;
    }

    private Collection<GrantedAuthority> getUserAuth(String[] roles)
    {
        logger.info("Loading roles for user");

        if (roles == null) {
            return null;
        }

        Set<GrantedAuthority> allAuth = new LinkedHashSet<>();

         String val = null;
         for (String role : roles) {
             val = role.toUpperCase().replace("ROLE_", "");
             val = "ROLE_" + val;
             logger.info("user has role: " + val);
             allAuth.add(new SimpleGrantedAuthority(val));
         }
        return allAuth;
    }

    private InMemoryUser findUserByID(String username) {

        if (users == null) {
            return null;
        }

        for(InMemoryUser user: users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public List<InMemoryUser> getUsers() {
        return users;
    }

    public void setUsers(List<InMemoryUser> users) {
        this.users = users;
    }

    public void addUser(InMemoryUser user) {
        getUsers().add(user);
    }
}
