package com.devinkin.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Action的父类
 *
 * @author king
 */
public class BaseAction extends ActionSupport {
    // 属性驱动的方式;
    // 当前页，默认值就是1
    private Integer pageCode = 1;

    public void setPageCode(Integer pageCode) {
        if (pageCode == null) {
            this.pageCode = 1;
        } else {
            this.pageCode = pageCode;
        }
    }

    // 每页显示的数据的条数
    private Integer pageSize = 2;

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            this.pageSize = 2;
        } else {
            this.pageSize = pageSize;
        }
    }

    public Integer getPageCode() {
        return pageCode;
    }

    public Integer getPageSize() {
        return pageSize;
    }


    /**
     * 调用值栈对象的set方法
     */
    public void setVs(String key, Object obj) {
        ActionContext.getContext().getValueStack().set(key, obj);
    }


    /**
     * 调用值栈对象的st方法
     */
    public void pushVs(Object obj) {
        ActionContext.getContext().getValueStack().push(obj);
    }
}
