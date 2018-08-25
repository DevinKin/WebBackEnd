package cn.devinkin.jk.domain;

import java.util.HashSet;
import java.util.Set;

public class Role extends BaseEntity{
    /**
     ROLE_ID    VARCHAR2(40)    NOT NULL,
     NAME       VARCHAR2(30).
     REMARK     VARCHAR2(200),
     ORDER_NO   INT,
     */
    private String id;
    // 角色名
    private String name;
    // 备注
    private String remark;
    // 排序号
    private String orderNo;

    // 用户与角色，多对多
    private Set<User> users = new HashSet<User>(0);
    // 角色与模块，多对多
    private Set<Module> modules = new HashSet<Module>(0);

    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
