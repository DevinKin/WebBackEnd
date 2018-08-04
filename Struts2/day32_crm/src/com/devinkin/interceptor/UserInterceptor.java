package com.devinkin.interceptor;

import com.devinkin.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.ServletActionContext;

/**
 * 自定义拦截器，判断当前系统是否已经登录，如果登录了，继续执行。没有登录，跳转到登录页面
 */
public class UserInterceptor extends MethodFilterInterceptor{

    /**
     * 进行拦截的方法
     * @param actionInvocation
     * @return
     * @throws Exception
     */
    @Override
    protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
        // 获得session对象
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
        if (user == null) {
            // 说明没有登录，后面就不会执行了
            return "login";
        }
        return actionInvocation.invoke();
    }
}
