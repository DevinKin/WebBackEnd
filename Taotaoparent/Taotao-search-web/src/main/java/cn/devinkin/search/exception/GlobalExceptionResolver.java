package cn.devinkin.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author devinkin
 * <p>Title: GlobalExceptionResolver</p>
 * <p>Description: 全局异常处理器</p>
 * @version 1.0
 * @see
 * @since 7:49 2018/9/26
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception e) {
        logger.info("进入全局异常处理器");
        logger.debug("测试handler的类型: ",handler.getClass());
        // 控制台打印异常
        e.printStackTrace();
        // 向日志文件中写入异常
        logger.error("系统发生异常",e);
        // 发邮件
        // jmail
        // 发短信
        // 有第三方
        // 展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "系统发生异常,请稍后重试");
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
