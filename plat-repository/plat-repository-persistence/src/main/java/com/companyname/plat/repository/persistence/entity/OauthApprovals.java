/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.repository.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmohamed
 */
@Entity
@Table(name = "oauth_approvals")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OauthApprovals.findAll", query = "SELECT o FROM OauthApprovals o"),
    @NamedQuery(name = "OauthApprovals.findByUserId", query = "SELECT o FROM OauthApprovals o WHERE o.oauthApprovalsPK.userId = :userId"),
    @NamedQuery(name = "OauthApprovals.findByClientId", query = "SELECT o FROM OauthApprovals o WHERE o.oauthApprovalsPK.clientId = :clientId"),
    @NamedQuery(name = "OauthApprovals.findByScope", query = "SELECT o FROM OauthApprovals o WHERE o.scope = :scope"),
    @NamedQuery(name = "OauthApprovals.findByStatus", query = "SELECT o FROM OauthApprovals o WHERE o.status = :status"),
    @NamedQuery(name = "OauthApprovals.findByExpiresAt", query = "SELECT o FROM OauthApprovals o WHERE o.expiresAt = :expiresAt"),
    @NamedQuery(name = "OauthApprovals.findByLastModifiedAt", query = "SELECT o FROM OauthApprovals o WHERE o.lastModifiedAt = :lastModifiedAt")})
public class OauthApprovals implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OauthApprovalsPK oauthApprovalsPK;
    @Column(name = "scope")
    private String scope;
    @Column(name = "status")
    private String status;
    @Column(name = "expiresAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;
    @Column(name = "lastModifiedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedAt;

    public OauthApprovals() {
    }

    public OauthApprovals(OauthApprovalsPK oauthApprovalsPK) {
        this.oauthApprovalsPK = oauthApprovalsPK;
    }

    public OauthApprovals(String userId, String clientId) {
        this.oauthApprovalsPK = new OauthApprovalsPK(userId, clientId);
    }

    public OauthApprovalsPK getOauthApprovalsPK() {
        return oauthApprovalsPK;
    }

    public void setOauthApprovalsPK(OauthApprovalsPK oauthApprovalsPK) {
        this.oauthApprovalsPK = oauthApprovalsPK;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oauthApprovalsPK != null ? oauthApprovalsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OauthApprovals)) {
            return false;
        }
        OauthApprovals other = (OauthApprovals) object;
        if ((this.oauthApprovalsPK == null && other.oauthApprovalsPK != null) || (this.oauthApprovalsPK != null && !this.oauthApprovalsPK.equals(other.oauthApprovalsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.OauthApprovals[ oauthApprovalsPK=" + oauthApprovalsPK + " ]";
    }
    
}
