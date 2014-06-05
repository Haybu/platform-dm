/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.repository.persistence.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author hmohamed
 */
@Embeddable
public class OauthApprovalsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "userId")
    private String userId;
    @Basic(optional = false)
    @Column(name = "clientId")
    private String clientId;

    public OauthApprovalsPK() {
    }

    public OauthApprovalsPK(String userId, String clientId) {
        this.userId = userId;
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        hash += (clientId != null ? clientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OauthApprovalsPK)) {
            return false;
        }
        OauthApprovalsPK other = (OauthApprovalsPK) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        if ((this.clientId == null && other.clientId != null) || (this.clientId != null && !this.clientId.equals(other.clientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.OauthApprovalsPK[ userId=" + userId + ", clientId=" + clientId + " ]";
    }
    
}
