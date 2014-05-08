/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.companyname.plat.repository.persistence.entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmohamed
 */
@Entity
@Table(name = "app_user_address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAddress.findAll", query = "SELECT a FROM UserAddress a"),
    @NamedQuery(name = "UserAddress.findById", query = "SELECT a FROM UserAddress a WHERE a.id = :id"),
    @NamedQuery(name = "UserAddress.findByStreet1", query = "SELECT a FROM UserAddress a WHERE a.street1 = :street1"),
    @NamedQuery(name = "UserAddress.findByStreet2", query = "SELECT a FROM UserAddress a WHERE a.street2 = :street2"),
    @NamedQuery(name = "UserAddress.findByCity", query = "SELECT a FROM UserAddress a WHERE a.city = :city"),
    @NamedQuery(name = "UserAddress.findByState", query = "SELECT a FROM UserAddress a WHERE a.state = :state"),
    @NamedQuery(name = "UserAddress.findByCounty", query = "SELECT a FROM UserAddress a WHERE a.county = :county"),
    @NamedQuery(name = "UserAddress.findByCountry", query = "SELECT a FROM UserAddress a WHERE a.country = :country"),
    @NamedQuery(name = "UserAddress.findByZip", query = "SELECT a FROM UserAddress a WHERE a.zip = :zip"),
    @NamedQuery(name = "UserAddress.findByIsPrimary", query = "SELECT a FROM UserAddress a WHERE a.isPrimary = :isPrimary")})
public class UserAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "street1")
    private String street1;
    @Column(name = "street2")
    private String street2;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "county")
    private String county;
    @Column(name = "country")
    private String country;
    @Column(name = "zip")
    private String zip;
    @Basic(optional = false)
    @Column(name = "isPrimary")
    private boolean isPrimary;
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne
    private SysAddressTypes typeId;
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserProfile userProfile;

    public UserAddress() {
    }

    public UserAddress(Long id) {
        this.id = id;
    }

    public UserAddress(Long id, boolean isPrimary) {
        this.id = id;
        this.isPrimary = isPrimary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public SysAddressTypes getTypeId() {
        return typeId;
    }

    public void setTypeId(SysAddressTypes typeId) {
        this.typeId = typeId;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
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
        if (!(object instanceof UserAddress)) {
            return false;
        }
        UserAddress other = (UserAddress) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.UserAddress[ id=" + id + " ]";
    }
    
}
