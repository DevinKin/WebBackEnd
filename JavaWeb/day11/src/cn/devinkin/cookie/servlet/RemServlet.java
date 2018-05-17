package cn.devinkin.cookie.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "RemServlet",urlPatterns = {"/RemServlet"})
public class RemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置响应编码
         * 2. 获取cookies，遍历之，查看是否有lastAccessTime的cookie
         * 3. 判断cookie是否为空
         *  1. 为空，提示：“你第一次访问”
         *  2. 不为空，获取cookie的value，提示：“你上次访问的时间是...”
         * 4. 将此次访问时间记录下来，回写浏览器
         */
        response.setContentType("text/html;charset=utf-8");
        Cookie cookie = getCookieByName("lastAccessTime", request.getCookies());
        if (cookie == null)
            response.getWriter().print("你第一次访问...");
        else {
            String time = cookie.getValue();
            response.getWriter().print("你上次访问的时间是： " + time);
        }
        cookie = new Cookie("lastAccessTime", new Date().toString());

        cookie.setMaxAge(3600);
        cookie.setPath(request.getContextPath() + "/");
        response.addCookie(cookie);
    }

    /**
     * 根据cookie名获取Cookie对象
     * @param cookieName cookie名
     * @param cookies cookie数组
     * @return
     */
    public Cookie getCookieByName(String cookieName, Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName()))
                    return c;
            }
        }
        return null;
    }
}
