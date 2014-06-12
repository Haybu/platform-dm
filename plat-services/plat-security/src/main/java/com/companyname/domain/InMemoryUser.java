package com.companyname.domain;

/**
 * Created by hmohamed on 6/11/14.
 */
public class InMemoryUser {
    String userName;
    String password;
    String[] roles;

    public InMemoryUser() {
        this(null, null, null);
    }

    public InMemoryUser(String _u) {
        this(_u, null, null);
    }

    public InMemoryUser(String _u, String _p) {
        this(_u, _p, null);
    }

    public InMemoryUser(String _u, String _p, String[] _r){
        setUserName(_u);
        setPassword(_p);
        setRoles(_r);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
