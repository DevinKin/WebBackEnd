package cn.devinkin.jk.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User extends BaseEntity{
    private String id;
    // 用于与部门，多对一
    private Dept dept;
    // 用户与用户扩展信息，一对一
    private UserInfo userInfo;
    // 用户名
    private String userName;
    // 密码
    private String password;
    // 状态
    private Integer state;
    // 用户与角色，多对多
    private Set<Role> roles = new HashSet<Role>(0);

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
