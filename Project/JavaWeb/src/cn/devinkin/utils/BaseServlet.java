package cn.devinkin.utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取子类
         * 2. 获取请求的方法
         * 3. 获取方法对象
         * 4. 让方法执行
         * 5. 判断方法返回字符串是否为空
         *  1. 不为空
         *      1. 判断s用:分割后第二个字段是否为空
         *          1. 若不为空，判断第一个字段是r还是f
         *              1. 若为r，重定向到path
         *              2. 若为f，请求转发到path
         */
        Class clazz = this.getClass();

        String m = request.getParameter("method");
        if (m == null) {
            m = "f:index";
        }

        Method method = null;

        try {
            method = clazz.getMethod(m, HttpServletRequest.class, HttpServletResponse.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("你要调用的方法: " + m + "(HttpServletRequest req,HttpServletResponse resp)，它不存在");
        }

        try {
            String s = (String) method.invoke(this, request, response);
            if (s == null || s.trim().isEmpty())
                return;
            if (s.contains(":")) {
                String prefix = s.split(":")[0];
                String path = s.split(":")[1];
                if (path != null) {
                    if ("r".equals(prefix))
                        response.sendRedirect(request.getContextPath()+path);
                    else if ("f".equals(prefix))
                        request.getRequestDispatcher(path).forward(request, response);
                } else {
                    throw new RuntimeException("你指定的操作： " + prefix + ", 当前版本还不支持");
                }
            } else {
                //默认是请求转发
                request.getRequestDispatcher(s).forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 展示用户首页
     * @param request
     * @param response
     * @return 请求首页的路径
     * @throws ServletException
     * @throws IOException
     */
    public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //去数据库中查询最新商品和热门商品，将结果放入request域中，请求转发到/jsp/index.jsp
        return "f:/store/jsp/index.jsp";
    }
}
