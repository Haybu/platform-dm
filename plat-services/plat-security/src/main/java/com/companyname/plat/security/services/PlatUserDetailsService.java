/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.security.services;

import com.companyname.plat.repository.persistence.dao.AccountRepository;
import com.companyname.plat.repository.persistence.dao.GroupAuthorizationRepository;
import com.companyname.plat.repository.persistence.dao.GroupMembersRepository;
import com.companyname.plat.repository.persistence.entity.GroupAuth;
import com.companyname.plat.repository.persistence.entity.GroupMember;
import com.companyname.plat.repository.persistence.entity.OrganizationGroup;
import com.companyname.plat.repository.persistence.entity.UserAccount;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author hmohamed
 */
@Service
public class PlatUserDetailsService 
        extends PlatCommonService
        implements UserDetailsService 
{
    private static final Logger logger = 
            Logger.getLogger(PlatUserDetailsService.class.getName()); 
    
    @Autowired
    AccountRepository accountRepository;        
    
    @Autowired
    GroupMembersRepository groupMembersRepository;        
    
    @Autowired
    GroupAuthorizationRepository authRepository;        
    
    @Override
    public UserDetails loadUserByUsername(String loginName) 
            throws UsernameNotFoundException 
    {
       logger.info("Loading account for user name:  " + loginName);
       
       UserAccount account = this.getUserAccount(accountRepository, loginName);
       
       List<GroupMember> groups = groupMembersRepository.findByUserAccountId(account.getId());
       Collection<GrantedAuthority> authorizations = getUserAuth(groups); 
       
       logger.info("User priciple information: ");
       logger.info("Login Name:             " + loginName);
       logger.info("Password hash:          " + account.getLoginPassword());
       logger.info("is Account Valid:       " + account.getIsAccountValid() );
       logger.info("is account expired:     " + account.getIsCredentialExpired());
       logger.info("is account locked:      " + account.getIsAccountLocked());
       logger.info("Roles:                  " + authorizations);
       
        
        UserDetails user = new User(loginName, account.getLoginPassword()
                , account.getIsAccountValid()  // enabled?
                , !account.getIsAccountExpired()  // accountNonExpired?
                , !account.getIsCredentialExpired()  // credentialsNonExpired?
                , !account.getIsAccountLocked()  // accountNonBlocked?
             , authorizations);   //Collection<? extends GrantedAuthority>               

        return user;  
    }
       
    private Collection <GrantedAuthority> getUserAuth(List<GroupMember> groupMembers) 
    {
        logger.info("Loading roles for user's groups:  " + groupMembers);
        
        Map<Long, GrantedAuthority> allAuth = new HashMap<> ();
        OrganizationGroup group = null;
        List<GroupAuth> authorizations = null;
        
        for (GroupMember membership: groupMembers) 
        {            
            group = membership.getGroup();
            logger.info("Retrieving roles for group: " + group);
            
            authorizations = authRepository
                    .findByGroup(group);
            
            String val = null;
            for (GroupAuth auth : authorizations) {
                val = auth.getRole().getName().toUpperCase().replace("ROLE_", "");  
                val = "ROLE_" + val;
                logger.info("user has role: " + val);
                allAuth.put(group.getId(), new SimpleGrantedAuthority(val));
            }
        }
        
        return allAuth.values();
    }
    
    public void setAccountRepository(AccountRepository repo) {
        this.accountRepository = repo;
    }
    
    public void setGroupMembersRepository(GroupMembersRepository repo) {
        groupMembersRepository = repo;
    }
    
    public void setGroupAuthorizationRepository(GroupAuthorizationRepository repo) {
        authRepository = repo;
    }
    
}
