package com.devinkin.action2;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 编写客户的Action的类
 * @author king
 */
public class CustomerAction extends ActionSupport{
    /**
     * 保存客户
     * @return
     */
    public String save() {
        System.out.println("保存客户。。。");
        return NONE;
    }

    /**
     * 删除客户
     * @return
     */
    public String delete() {
        System.out.println("删除客户。。。");
        return NONE;
    }
}
