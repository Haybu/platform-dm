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
@Table(name = "oauth_refresh_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OauthRefreshToken.findAll", query = "SELECT o FROM OauthRefreshToken o"),
    @NamedQuery(name = "OauthRefreshToken.findByTokenId", query = "SELECT o FROM OauthRefreshToken o WHERE o.tokenId = :tokenId")})
public class OauthRefreshToken implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "token_id")
    private String tokenId;
    @Lob
    @Column(name = "token")
    private byte[] token;
    @Lob
    @Column(name = "authentication")
    private byte[] authentication;

    public OauthRefreshToken() {
    }

    public OauthRefreshToken(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
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
        hash += (tokenId != null ? tokenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OauthRefreshToken)) {
            return false;
        }
        OauthRefreshToken other = (OauthRefreshToken) object;
        if ((this.tokenId == null && other.tokenId != null) || (this.tokenId != null && !this.tokenId.equals(other.tokenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.companyname.plat.repository.persistence.entity.OauthRefreshToken[ tokenId=" + tokenId + " ]";
    }
    
}
