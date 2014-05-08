/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.repository.persistence.dao;

import com.companyname.plat.repository.persistence.ProfileRepository;
import com.companyname.plat.repository.persistence.PlatPersistenceComponentApplication;
import com.companyname.plat.repository.persistence.entity.UserProfile;
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
public class ProfileRepoIntegrationTest {
    
    @Autowired
	ProfileRepository profileRepo;

	@Test
	public void findsFirstPageOfPersons() 
        {            
		Page<UserProfile> profiles = 
                        profileRepo.findAll(new PageRequest(0, 10));
		assertThat(profiles.getTotalElements(), is(greaterThan(1L)));
	}
    
}
