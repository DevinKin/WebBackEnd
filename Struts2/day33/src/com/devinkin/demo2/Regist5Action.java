package com.devinkin.demo2;

import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.Map;

/**
 * 属性驱动的方式，把数据封装到List集合中
 * @author king
 */
public class Regist5Action extends ActionSupport {

    private Map<String, User> map;

    public Map<String, User> getMap() {
        return map;
    }

    public void setMap(Map<String, User> map) {
        this.map = map;
    }

    @Override
    public String execute() throws Exception {
        System.out.println(map);
        return NONE;
    }

}
