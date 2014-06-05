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
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hmohamed
 */
@Entity
@Table(name = "oauth_code")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OauthCode.findAll", query = "SELECT o FROM OauthCode o"),
    @NamedQuery(name = "OauthCode.findByCode", query = "SELECT o FROM OauthCode o WHERE o.code = :code")})
public class OauthCode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Lob
    @Column(name = "authentication")
    private byte[] authentication;

    public OauthCode() {
    }

    public OauthCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OauthCode)) {
            return false;
        }
        OauthCode other = (OauthCode) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.OauthCode[ code=" + code + " ]";
    }
    
}
