package com.devinkin.demo2;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 模型驱动的方式
 * 实现ModelDriven接口
 * 必须手动实例化对象(需要自己new好)
 * @author king
 */
public class Regist3Action extends ActionSupport implements ModelDriven<User>{
    // 必须要手动实例化
    private User user = new User();

    @Override
    public String execute() throws Exception {
        System.out.println(user);
        return NONE;
    }

    @Override
    public User getModel() {
        return user;
    }
}
