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
@Table(name = "app_sys_address_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysAddressTypes.findAll", query = "SELECT a FROM SysAddressTypes a"),
    @NamedQuery(name = "SysAddressTypes.findById", query = "SELECT a FROM SysAddressTypes a WHERE a.id = :id"),
    @NamedQuery(name = "SysAddressTypes.findByName", query = "SELECT a FROM SysAddressTypes a WHERE a.name = :name"),
    @NamedQuery(name = "SysAddressTypes.findByDescription", query = "SELECT a FROM SysAddressTypes a WHERE a.description = :description")})
public class SysAddressTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "typeId")
    private Collection<UserAddress> appUserAddressCollection;

    public SysAddressTypes() {
    }

    public SysAddressTypes(Long id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof SysAddressTypes)) {
            return false;
        }
        SysAddressTypes other = (SysAddressTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.AppSysAddressTypes[ id=" + id + " ]";
    }
    
}
