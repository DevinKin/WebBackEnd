package cn.devinkin.jk.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Dept implements Serializable{
    /**
     DEPT_ID              varchar2(40)                    not null,
     DEPT_NAME            varchar2(50),
     PARENT_ID            varchar2(40),
     STATE                NUMBER(6,0),
     constraint PK_DEPT_P primary key (DEPT_ID)
     */

    private String id;
    // 部门与用户，一对多
    private Set<User> users = new HashSet<User>(0);
    // 部门名称
    private String deptName;
    // 父部门，自关联，子部门与父部门的关系，多对一
    private Dept parent;
    // 状态，1代表部门启用，0代表停用
    private Integer state;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Dept getParent() {
        return parent;
    }

    public void setParent(Dept parent) {
        this.parent = parent;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
