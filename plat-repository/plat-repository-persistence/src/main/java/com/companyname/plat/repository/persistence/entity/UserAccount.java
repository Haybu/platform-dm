/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.repository.persistence.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hmohamed
 */
@Entity
@Table(name = "app_user_accounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAccount.findAll", query = "SELECT a FROM UserAccount a"),
    @NamedQuery(name = "UserAccount.findById", query = "SELECT a FROM UserAccount a WHERE a.id = :id"),
    @NamedQuery(name = "UserAccount.findByLoginName", query = "SELECT a FROM UserAccount a WHERE a.loginName = :loginName"),
    @NamedQuery(name = "UserAccount.findByLoginPassword", query = "SELECT a FROM UserAccount a WHERE a.loginPassword = :loginPassword"),
    @NamedQuery(name = "UserAccount.findByCreateDate", query = "SELECT a FROM UserAccount a WHERE a.createDate = :createDate"),
    @NamedQuery(name = "UserAccount.findByLockedDate", query = "SELECT a FROM UserAccount a WHERE a.lockedDate = :lockedDate"),
    @NamedQuery(name = "UserAccount.findByValidateDate", query = "SELECT a FROM UserAccount a WHERE a.validateDate = :validateDate"),
    @NamedQuery(name = "UserAccount.findByIsAccountExpired", query = "SELECT a FROM UserAccount a WHERE a.isAccountExpired = :isAccountExpired"),
    @NamedQuery(name = "UserAccount.findByIsCredentialExpired", query = "SELECT a FROM UserAccount a WHERE a.isCredentialExpired = :isCredentialExpired"),
    @NamedQuery(name = "UserAccount.findByIsAccountValid", query = "SELECT a FROM UserAccount a WHERE a.isAccountValid = :isAccountValid"),
    @NamedQuery(name = "UserAccount.findByIsAccountLocked", query = "SELECT a FROM UserAccount a WHERE a.isAccountLocked = :isAccountLocked"),
    @NamedQuery(name = "UserAccount.findByAccountExpiredDate", query = "SELECT a FROM UserAccount a WHERE a.accountExpiredDate = :accountExpiredDate"),
    @NamedQuery(name = "UserAccount.findByCredentialExpiredDate", query = "SELECT a FROM UserAccount a WHERE a.credentialExpiredDate = :credentialExpiredDate")})
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "login_name")
    private String loginName;
    @Column(name = "login_password")
    private String loginPassword;
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "locked_date")
    @Temporal(TemporalType.DATE)
    private Date lockedDate;
    @Column(name = "validate_date")
    @Temporal(TemporalType.DATE)
    private Date validateDate;
    @Basic(optional = false)
    @Column(name = "is_account_expired")
    private boolean isAccountExpired;
    @Basic(optional = false)
    @Column(name = "is_credential_expired")
    private boolean isCredentialExpired;
    @Basic(optional = false)
    @Column(name = "is_account_valid")
    private boolean isAccountValid;
    @Basic(optional = false)
    @Column(name = "is_account_locked")
    private boolean isAccountLocked;
    @Basic(optional = false)
    @Column(name = "salt")
    private String salt;
    @Column(name = "account_expired_date")
    @Temporal(TemporalType.DATE)
    private Date accountExpiredDate;
    @Column(name = "credential_expired_date")
    @Temporal(TemporalType.DATE)
    private Date credentialExpiredDate;
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    @ManyToOne
    private UserProfile userProfile;
    @OneToMany(mappedBy = "userAccount")
    private Collection<GroupMember> appGroupMembersCollection;

    public UserAccount() {
    }

    public UserAccount(Long id) {
        this.id = id;
    }

    public UserAccount(Long id, boolean isAccountExpired, boolean isCredentialExpired, boolean isAccountValid, boolean isAccountLocked) {
        this.id = id;
        this.isAccountExpired = isAccountExpired;
        this.isCredentialExpired = isCredentialExpired;
        this.isAccountValid = isAccountValid;
        this.isAccountLocked = isAccountLocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLockedDate() {
        return lockedDate;
    }

    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    public Date getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(Date validateDate) {
        this.validateDate = validateDate;
    }

    public boolean getIsAccountExpired() {
        return isAccountExpired;
    }

    public void setIsAccountExpired(boolean isAccountExpired) {
        this.isAccountExpired = isAccountExpired;
    }

    public boolean getIsCredentialExpired() {
        return isCredentialExpired;
    }

    public void setIsCredentialExpired(boolean isCredentialExpired) {
        this.isCredentialExpired = isCredentialExpired;
    }

    public boolean getIsAccountValid() {
        return isAccountValid;
    }

    public void setIsAccountValid(boolean isAccountValid) {
        this.isAccountValid = isAccountValid;
    }

    public boolean getIsAccountLocked() {
        return isAccountLocked;
    }

    public void setIsAccountLocked(boolean isAccountLocked) {
        this.isAccountLocked = isAccountLocked;
    }

    public Date getAccountExpiredDate() {
        return accountExpiredDate;
    }

    public void setAccountExpiredDate(Date accountExpiredDate) {
        this.accountExpiredDate = accountExpiredDate;
    }

    public Date getCredentialExpiredDate() {
        return credentialExpiredDate;
    }

    public void setCredentialExpiredDate(Date credentialExpiredDate) {
        this.credentialExpiredDate = credentialExpiredDate;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    
    public void setSalt(String str) {
        this.salt = str;
    }
    
    public String getSalt() {
        return this.salt;
    }

    @XmlTransient
    public Collection<GroupMember> getAppGroupMembersCollection() {
        return appGroupMembersCollection;
    }

    public void setAppGroupMembersCollection(Collection<GroupMember> appGroupMembersCollection) {
        this.appGroupMembersCollection = appGroupMembersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.UserAccount[ id=" + id + " ]";
    }
    
}
