/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.security.services;

import com.companyname.plat.repository.persistence.AccountRepository;
import com.companyname.plat.repository.persistence.GroupAuthorizationRepository;
import com.companyname.plat.repository.persistence.GroupMembersRepository;
import com.companyname.plat.repository.persistence.entity.GroupAuth;
import com.companyname.plat.repository.persistence.entity.GroupMember;
import com.companyname.plat.repository.persistence.entity.OrganizationGroup;
import com.companyname.plat.repository.persistence.entity.UserAccount;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author hmohamed
 */
public class PlatUserDetailsService implements UserDetailsService
{
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
        
       List<UserAccount> accounts = accountRepository.findByLoginName(loginName);
       
       if (accounts == null) {
           throw new UsernameNotFoundException("No user account found");
       } 
       
       if (accounts.size() > 1 ) {
           throw new UsernameNotFoundException(
                   "More than one active account is found");
       }             
       
       UserAccount account = accounts.get(0);                      
       
       List<GroupMember> groups = groupMembersRepository.findByUserAccountId(account.getId());
       Collection<GrantedAuthority> authorizations = getUserAuth(groups);              
        
        UserDetails user = new User(loginName, account.getLoginPassword()
                , account.getIsAccountValid()  // enabled?
                , account.getIsAccountExpired()  // accountNonExpired?
                , account.getIsCredentialExpired()  // credentialsNonExpired?
                , account.getIsAccountLocked()  // accountNonBlocked?
             , authorizations);   //Collection<? extends GrantedAuthority>               

        return user;  
    }
       
    private Collection <GrantedAuthority> getUserAuth(List<GroupMember> groupMembers) 
    {
        Map<Long, GrantedAuthority> allAuth = new HashMap<> ();
        OrganizationGroup group = null;
        List<GroupAuth> authorizations = null;
        
        for (GroupMember membership: groupMembers) {
            group = membership.getGroup();
                        
            authorizations = authRepository
                    .findByGroup(group.getName());
            
            for (GroupAuth auth : authorizations) {
                allAuth.put(group.getId(), new SimpleGrantedAuthority(auth.getRole().getName()));
            }
        }
        
        return allAuth.values();
    }
}
