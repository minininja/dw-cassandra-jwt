package org.dorkmaster.auth.model;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.List;

/**
 * Author: mjackson
 * Date: 11/8/15 8:23 AM
 */
@Table(keyspace="authtest",name="users")
public class User {
    private Boolean enabled;
    private String password;
    private List<String> roles;
    @PartitionKey
    private String username;

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void  setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
