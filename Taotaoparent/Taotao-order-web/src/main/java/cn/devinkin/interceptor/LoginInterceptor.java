package cn.devinkin.interceptor;

import cn.devinkin.common.pojo.TaotaoResult;
import cn.devinkin.common.utils.CookieUtils;
import cn.devinkin.pojo.TbUser;
import cn.devinkin.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author devinkin
 * <p>Title: LoginInterceptor</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 22:53 2018/10/5
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @Value("${SSO_URL}")
    private String SSO_URL;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // 执行handler之前先执行此方法
        // 返回值为true:放行,返回false,拦截
        // 1. 从cookie中取token信息
        String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
        // 2. 如果取不到token,跳转到sso的登录页面,需要把当前请求的url当做参数传递给sso,sso登录成功之后跳转回请求的页面.
        if (StringUtils.isBlank(token)) {
            // 取当前请求的url
            StringBuffer requestURL = request.getRequestURL();
            // 跳转到登录页面
            response.sendRedirect(SSO_URL + "/user/page/login?url=" + requestURL);
            // 拦截
            return false;
        }
        // 3. 取到token,调用sso系统的服务判断用户是否登录
        TaotaoResult taotaoResult = userService.getUserByToken(token);
        // 4. 如果用户未登录状态,即没取到用户信息,跳转到sso的登录页面
        if (taotaoResult.getStatus() != 200) {
            // 取当前请求的url
            StringBuffer requestURL = request.getRequestURL();
            // 跳转到登录页面
            response.sendRedirect(SSO_URL + "/user/page/login?url=" + requestURL);
            // 拦截
            return false;
        }
        // 5. 如果取到用户信息,放行
        // 把用户信息放到request中
        TbUser user = (TbUser) taotaoResult.getData();
        request.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // handler执行之后,modelAndView返回之前
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // 在ModelAndView返回之后,异常处理
    }
}
