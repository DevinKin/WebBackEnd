package com.devinkin.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统角色
 * @author king
 */
public class Role {
    private Long rid;
    private String name;

    private Set<User> users = new HashSet<User>();

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
