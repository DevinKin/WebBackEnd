package com.devinkin.action1;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 编写Action类继承ActionSupport类，ActionSupport类已经实现了Action和一些其他接口
 * @author king
 */
public class Demo3Action extends ActionSupport{
    @Override
    public String execute() throws Exception {
        System.out.println("Demo3Action继承了ActionSupport类。。。");
        return NONE;
    }
}
