/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.repository.persistence.dao;

import com.companyname.plat.repository.PlatPersistenceComponentApplication;
import com.companyname.plat.repository.persistence.entity.GroupAuth;
import com.companyname.plat.repository.persistence.entity.GroupMember;
import com.companyname.plat.repository.persistence.entity.OrganizationGroup;
import com.companyname.plat.repository.persistence.entity.UserAccount;
import com.companyname.plat.repository.persistence.entity.UserProfile;
import java.util.List;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 *
 * @author hmohamed
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = 
        PlatPersistenceComponentApplication.class)
public class RepoIntegrationTest {
    
     private static final Logger logger = 
            Logger.getLogger(RepoIntegrationTest.class.getName());
    
    @Autowired
    ProfileRepository profileRepo;
    
    @Autowired
    AccountRepository accountRepository; 
    
    @Autowired
    GroupMembersRepository groupMembersRepository;        
    
    @Autowired
    GroupAuthorizationRepository authRepository; 

    @Test
    public void findsFirstPageOfPersons() 
    {     
        logger.info("Running users' profiles repository test");        
	Page<UserProfile> profiles = 
                profileRepo.findAll(new PageRequest(0, 10));
        assertThat(profiles.getTotalElements(), is(greaterThan(1L)));
    }
    
    @Test
    public void findsFirstPageOfAccounts() 
    {            
        logger.info("Running users' accounts repository test");
	Page<UserAccount> accounts = accountRepository.findAll(new PageRequest(0, 10));
        assertThat(accounts.getTotalElements(), is(greaterThan(1L)));
    }
    
    @Test
    public void findOneAccounts() 
    {            
        logger.info("Running users' accounts repository test");
	List<UserAccount> accounts = accountRepository.findByLoginName("toy");
        assertThat(accounts.size(), is(1));
    }
    
    @Test
    public void findGroupMemebers() 
    {
        logger.info("Running users' group member repository test");
        List<GroupMember> groups = groupMembersRepository.findByUserAccountId(1);
        assertThat(groups.size(), is(greaterThan(1)));
    }
    
    @Test
    public void findAuthorities() 
    {     
        logger.info("Running users' authorization repository test");
        
        OrganizationGroup group = new OrganizationGroup();
        group.setId(1L);
        
        List<GroupAuth> authorizations = authRepository
                    .findByGroup(group);
        
        assertThat(authorizations.size(), is(greaterThan(0)));
    }
    
}
