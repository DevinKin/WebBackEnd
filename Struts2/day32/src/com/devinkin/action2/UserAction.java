package com.devinkin.action2;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 动态方法访问方式
 * @author king
 */
public class UserAction extends ActionSupport{
    /**
     * 保存客户
     * @return
     */
    public String save() {
        System.out.println("保存用户。。。");
        return null;
    }

    /**
     * 删除客户
     * @return
     */
    public String delete() {
        System.out.println("删除用户。。。");
        return null;
    }

}
