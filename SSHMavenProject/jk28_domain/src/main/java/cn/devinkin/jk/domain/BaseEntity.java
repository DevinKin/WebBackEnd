package cn.devinkin.jk.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable{
    // 创建者id
    protected String createBy;
    // 创建者所在的部门的id
    protected String createDept;
    //创建时间
    protected Date createTime;
    // 更新者的id
    protected String updateBy;
    // 更新时间
    protected Date updateTime;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDept() {
        return createDept;
    }

    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
