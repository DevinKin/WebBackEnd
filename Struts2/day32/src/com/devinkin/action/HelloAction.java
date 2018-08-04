package com.devinkin.action;

/**
 * Struts2框架都是用Action类处理用户的请求
 * @author king
 */
public class HelloAction {
    /**
     * Action类中的方法签名有要求的，必须这么做
     * public 公有的
     * 必须有返回值，必须为String类型
     * 方法名可以是任意的，但是不能有参数列表
     * 页面的跳转：
     *  1. return "要转发的页面" return "ok"
     *  2. 需要在struts.xml配置文件中，配置跳转的页面
     */
    public String sayHello() {
        System.out.println("Hello Struts2!!");
        return "ok";
    }

    public String execute() {
        System.out.println("method方法的默认值是execute");
        return null;
    }
}
