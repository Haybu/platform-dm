/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.repository.persistence;

import com.companyname.plat.repository.persistence.entity.GroupMember;
import java.util.List;

/**
 *
 * @author hmohamed
 */
public interface GroupMembersRepository 
        extends BaseRepository <GroupMember, Long> {
    
    public List<GroupMember> findByUserAccountId(long id);
}
