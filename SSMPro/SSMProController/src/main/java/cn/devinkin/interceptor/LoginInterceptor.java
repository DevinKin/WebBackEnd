package cn.devinkin.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return 布尔值,true为方形,false则拦截住
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 判断当前访问路径是否为登录路径,如果是,则放行,如果不是,拦截
        if (request.getRequestURI().indexOf("/login") > 0) {
            return true;
        }

        // 判断session中是否有登录信息,如果没有,则跳转到登录页面,如果有,则放行
        if (request.getSession().getAttribute("username") != null) {
            return true;
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, httpServletResponse);
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("========Interceptor1======postHandle=====");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("========Interceptor1======afterCompletion=====");
    }
}
