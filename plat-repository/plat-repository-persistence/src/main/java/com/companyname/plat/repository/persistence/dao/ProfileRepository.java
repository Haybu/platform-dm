/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.repository.persistence.dao;

import com.companyname.plat.repository.persistence.entity.UserProfile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hmohamed
 */
@Repository
public interface ProfileRepository extends BaseRepository <UserProfile, Long> {
    
}
