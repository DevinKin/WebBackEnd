package cn.devinkin.listener.web.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MySessionListener implements HttpSessionListener{
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        //Session创建了
        System.out.println("Session创建了");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //Session销毁了
        System.out.println("Session销毁了");
    }
}
