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
@Table(name = "app_sys_roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysRoles.findAll", query = "SELECT a FROM SysRoles a"),
    @NamedQuery(name = "SysRoles.findById", query = "SELECT a FROM SysRoles a WHERE a.id = :id"),
    @NamedQuery(name = "SysRoles.findByDescription", query = "SELECT a FROM SysRoles a WHERE a.description = :description"),
    @NamedQuery(name = "SysRoles.findByName", query = "SELECT a FROM SysRoles a WHERE a.name = :name")})
public class SysRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "role")
    private Collection<GroupAuth> appGroupAuthCollection;

    public SysRoles() {
    }

    public SysRoles(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<GroupAuth> getAppGroupAuthCollection() {
        return appGroupAuthCollection;
    }

    public void setAppGroupAuthCollection(Collection<GroupAuth> appGroupAuthCollection) {
        this.appGroupAuthCollection = appGroupAuthCollection;
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
        if (!(object instanceof SysRoles)) {
            return false;
        }
        SysRoles other = (SysRoles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.AppSysRoles[ id=" + id + " ]";
    }
    
}
