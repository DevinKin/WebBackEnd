package cn.devinkin.listener.domain;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

public class User1 implements HttpSessionActivationListener,Serializable{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User1(int id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
        //钝化
        System.out.println("User对象写入磁盘，钝化");
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        //活化
        System.out.println("User对象读入内存，活化");
    }
}
