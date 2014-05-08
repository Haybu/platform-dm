/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.repository.persistence.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author hmohamed
 */
@Entity
@Table(name = "app_user_organization")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserOrganization.findAll", query = "SELECT a FROM UserOrganization a"),
    @NamedQuery(name = "UserOrganization.findById", query = "SELECT a FROM UserOrganization a WHERE a.id = :id"),
    @NamedQuery(name = "UserOrganization.findByName", query = "SELECT a FROM UserOrganization a WHERE a.name = :name"),
    @NamedQuery(name = "UserOrganization.findByPhoneNumber", query = "SELECT a FROM UserOrganization a WHERE a.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "UserOrganization.findByFaxNumber", query = "SELECT a FROM UserOrganization a WHERE a.faxNumber = :faxNumber"),
    @NamedQuery(name = "UserOrganization.findByStreet1", query = "SELECT a FROM UserOrganization a WHERE a.street1 = :street1"),
    @NamedQuery(name = "UserOrganization.findByStreet2", query = "SELECT a FROM UserOrganization a WHERE a.street2 = :street2"),
    @NamedQuery(name = "UserOrganization.findByCity", query = "SELECT a FROM UserOrganization a WHERE a.city = :city"),
    @NamedQuery(name = "UserOrganization.findByState", query = "SELECT a FROM UserOrganization a WHERE a.state = :state"),
    @NamedQuery(name = "UserOrganization.findByZip", query = "SELECT a FROM UserOrganization a WHERE a.zip = :zip"),
    @NamedQuery(name = "UserOrganization.findByCounty", query = "SELECT a FROM UserOrganization a WHERE a.county = :county"),
    @NamedQuery(name = "UserOrganization.findByCountry", query = "SELECT a FROM UserOrganization a WHERE a.country = :country"),
    @NamedQuery(name = "UserOrganization.findByEmailAddress", query = "SELECT a FROM UserOrganization a WHERE a.emailAddress = :emailAddress")})
public class UserOrganization implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "fax_number")
    private String faxNumber;
    @Column(name = "street1")
    private String street1;
    @Column(name = "street2")
    private String street2;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zip")
    private String zip;
    @Column(name = "county")
    private String county;
    @Column(name = "country")
    private String country;
    @Column(name = "email_address")
    private String emailAddress;
    @OneToMany(mappedBy = "orgId")
    private Collection<OrganizationGroup> appOrganizationGroupsCollection;

    public UserOrganization() {
    }

    public UserOrganization(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @XmlTransient
    public Collection<OrganizationGroup> getAppOrganizationGroupsCollection() {
        return appOrganizationGroupsCollection;
    }

    public void setAppOrganizationGroupsCollection(Collection<OrganizationGroup> appOrganizationGroupsCollection) {
        this.appOrganizationGroupsCollection = appOrganizationGroupsCollection;
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
        if (!(object instanceof UserOrganization)) {
            return false;
        }
        UserOrganization other = (UserOrganization) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.UserOrganization[ id=" + id + " ]";
    }
    
}
