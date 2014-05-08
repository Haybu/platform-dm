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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "app_user_profiles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserProfile.findAll", query = "SELECT a FROM UserProfile a"),
    @NamedQuery(name = "UserProfile.findByLastName", query = "SELECT a FROM UserProfile a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "UserProfile.findByFirstName", query = "SELECT a FROM UserProfile a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "UserProfile.findByMiddleName", query = "SELECT a FROM UserProfile a WHERE a.middleName = :middleName"),
    @NamedQuery(name = "UserProfile.findByDateOfBirth", query = "SELECT a FROM UserProfile a WHERE a.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "UserProfile.findById", query = "SELECT a FROM UserProfile a WHERE a.id = :id"),
    @NamedQuery(name = "UserProfile.findByGender", query = "SELECT a FROM UserProfile a WHERE a.gender = :gender"),
    @NamedQuery(name = "UserProfile.findByEmailAddress", query = "SELECT a FROM UserProfile a WHERE a.emailAddress = :emailAddress"),
    @NamedQuery(name = "UserProfile.findByCellPhoneNumber", query = "SELECT a FROM UserProfile a WHERE a.cellPhoneNumber = :cellPhoneNumber"),
    @NamedQuery(name = "UserProfile.findByOfficePhoneNumber", query = "SELECT a FROM UserProfile a WHERE a.officePhoneNumber = :officePhoneNumber"),
    @NamedQuery(name = "UserProfile.findByFaxNumber", query = "SELECT a FROM UserProfile a WHERE a.faxNumber = :faxNumber")})
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "gender")
    private Character gender;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "cell_phone_number")
    private String cellPhoneNumber;
    @Column(name = "office_phone_number")
    private String officePhoneNumber;
    @Column(name = "fax_number")
    private String faxNumber;
    @OneToMany(mappedBy = "userProfile")
    private Collection<UserAccount> appUserAccountsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userProfile")
    private Collection<UserAddress> appUserAddressCollection;

    public UserProfile() {
    }

    public UserProfile(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public void setOfficePhoneNumber(String officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    @XmlTransient
    public Collection<UserAccount> getAppUserAccountsCollection() {
        return appUserAccountsCollection;
    }

    public void setAppUserAccountsCollection(Collection<UserAccount> appUserAccountsCollection) {
        this.appUserAccountsCollection = appUserAccountsCollection;
    }

    @XmlTransient
    public Collection<UserAddress> getAppUserAddressCollection() {
        return appUserAddressCollection;
    }

    public void setAppUserAddressCollection(Collection<UserAddress> appUserAddressCollection) {
        this.appUserAddressCollection = appUserAddressCollection;
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
        if (!(object instanceof UserProfile)) {
            return false;
        }
        UserProfile other = (UserProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.UserProfile[ id=" + id + " ]";
    }
    
}
