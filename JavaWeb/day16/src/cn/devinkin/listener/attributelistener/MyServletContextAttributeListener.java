package cn.devinkin.listener.attributelistener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextAttributeListener implements ServletContextAttributeListener{
    @Override
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
        //添加
        System.out.println("在ServletConext中添加了一个属性，属性名为" +  servletContextAttributeEvent.getName());
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
        //移除
        System.out.println("在ServletConext中移除了一个属性，属性名为" + servletContextAttributeEvent.getName());
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
        //替换
        System.out.println("在ServletConext中替换了一个属性，属性名为" + servletContextAttributeEvent.getName());
    }
}
