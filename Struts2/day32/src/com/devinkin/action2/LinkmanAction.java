package com.devinkin.action2;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 通配符的方式
 */
public class LinkmanAction extends ActionSupport{
    /**
     * 保存客户
     * @return
     */
    public String save() {
        System.out.println("保存联系人。。。");
        return "saveOK";
    }

    /**
     * 删除客户
     * @return
     */
    public String delete() {
        System.out.println("删除联系人。。。");
        return "deleteOK";
    }
}
