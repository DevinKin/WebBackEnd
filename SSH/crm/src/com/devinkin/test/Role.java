package com.devinkin.test;

import com.alibaba.fastjson.annotation.JSONField;

public class Role {
    private String rname;
    private Person person;

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
