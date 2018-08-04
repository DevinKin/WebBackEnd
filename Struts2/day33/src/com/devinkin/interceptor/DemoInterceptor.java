package com.devinkin.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class DemoInterceptor extends AbstractInterceptor{
    /**
     * Intercept用来拦截的
     * @param actionInvocation
     * @return
     * @throws Exception
     */
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        System.out.println("Action方法执行之前。。。");
        String result = actionInvocation.invoke();
        System.out.println("Action方法执行之后");
        return result;
    }
}
