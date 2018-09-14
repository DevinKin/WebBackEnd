package cn.devinkin.exception.resolver;

import cn.devinkin.exception.CustomException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        e.printStackTrace();

        CustomException customException = null;

        // 如果抛出的是系统自定义异常则直接转换
        if (e instanceof CustomException) {
            customException = (CustomException) e;
        } else {
            // 如果跑出的不是系统自定义异常则重新构造一个系统错误异常
            customException = new CustomException("系统错误,请与管理员联系!");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", customException.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
