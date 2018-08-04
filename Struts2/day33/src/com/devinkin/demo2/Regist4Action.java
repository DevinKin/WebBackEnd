package com.devinkin.demo2;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.util.List;

/**
 * 属性驱动的方式，把数据封装到List集合中
 * @author king
 */
public class Regist4Action extends ActionSupport {
    // 必须要手动实例化
    private List<User> list;

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    @Override
    public String execute() throws Exception {
        for (User user : list) {
            System.out.println(user);
        }
        return NONE;
    }

}
