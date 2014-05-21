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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "app_organization_groups")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrganizationGroup.findAll", query = "SELECT a FROM OrganizationGroup a"),
    @NamedQuery(name = "OrganizationGroup.findById", query = "SELECT a FROM OrganizationGroup a WHERE a.id = :id"),
    @NamedQuery(name = "OrganizationGroup.findByGroupName", query = "SELECT a FROM OrganizationGroup a WHERE a.groupName = :groupName"),
    @NamedQuery(name = "OrganizationGroup.findByDescription", query = "SELECT a FROM OrganizationGroup a WHERE a.description = :description")})
public class OrganizationGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "org_id", referencedColumnName = "id")
    @ManyToOne
    private UserOrganization orgId;
    @OneToMany(mappedBy = "group")
    private Collection<GroupAuth> appGroupAuthCollection;
    @OneToMany(mappedBy = "group")
    private Collection<GroupMember> appGroupMembersCollection;

    public OrganizationGroup() {
    }

    public OrganizationGroup(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String _name) {
        this.groupName = _name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserOrganization getOrgId() {
        return orgId;
    }

    public void setOrgId(UserOrganization orgId) {
        this.orgId = orgId;
    }

    @XmlTransient
    public Collection<GroupAuth> getAppGroupAuthCollection() {
        return appGroupAuthCollection;
    }

    public void setAppGroupAuthCollection(Collection<GroupAuth> appGroupAuthCollection) {
        this.appGroupAuthCollection = appGroupAuthCollection;
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
        if (!(object instanceof OrganizationGroup)) {
            return false;
        }
        OrganizationGroup other = (OrganizationGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.AppOrganizationGroup[ id=" + id + " ]";
    }
    
}
