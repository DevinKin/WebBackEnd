package cn.devinkin.listener.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //创建操作
        System.out.println("ServletContext创建了！");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //销毁操作
        System.out.println("ServletContext销毁了！");
    }
}
