/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.repository.persistence;

import com.companyname.plat.repository.persistence.entity.UserAccount;
import java.util.List;

/**
 *
 * @author hmohamed
 */

public interface AccountRepository extends BaseRepository <UserAccount, Long> {
    
    public List<UserAccount> findByLoginName(String loginName);
    
}
