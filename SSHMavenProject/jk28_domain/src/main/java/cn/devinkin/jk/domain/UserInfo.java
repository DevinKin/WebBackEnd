package cn.devinkin.jk.domain;

import java.util.Date;

public class UserInfo extends BaseEntity{

    /**
     * USER_INFO_ID   VARCHAR2(40) NOT NULL,
     * NAME           VARCHAR2(40),
     * MANAGER_ID     VARCHAR2(40),
     * JOIN_DATE      TIMESTAMP,
     * SALARY         NUMERIC(8,2),
     * BIRTHDAY       TIMESTAMP,
     * GENDER         CHAR(1),
     * STATION        VARCHAR2(20),
     * TELEPHONE      VARCHAR2(100),
     * DEGREE         INT,
     * REMARK         VARCHAR2(600),
     * ORDER_NO       INT,
     * EMAIL          VARCHAR2(40),
     */

    private String id;
    // 姓名
    private String name;
    // 直属领导，用户与直属领导，多对一
    private User manager;
    // 入职时间
    private Date joinDate;
    // 薪水
    private Double salary;
    // 生日
    private Date birthday;
    // 性别
    private String gender;
    // 岗位
    private String station;
    // 电话
    private String telephone;
    // 等级
    private Integer degree;
    // 说明信息
    private String remark;
    // 排序号
    private String orderNo;
    // 邮箱地址
    private String email;

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

    public User getManager() {
        return manager;
    }

    public void setManager(User managerId) {
        this.manager = managerId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
