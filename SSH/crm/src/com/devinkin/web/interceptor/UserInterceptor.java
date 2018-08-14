package com.devinkin.web.interceptor;

import com.devinkin.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;

/**
 * 用户的拦截器，判断用户是否已经登录，如果登录，执行下一个拦截器
 * 如果没有登录，返回到登录页面(不能对所有请求都拦截，login和regist方法不能拦截)
 * 继承指定拦截器
 * @author king
 */
public class UserInterceptor extends MethodFilterInterceptor{

    /**
     * 拦截目标Action的方法
     * @param actionInvocation
     * @return
     * @throws Exception
     */
    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        // 获取Session
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
        if (user == null) {
            return "login";
        }
        // 执行下一个拦截器
        return actionInvocation.invoke();
    }
}
