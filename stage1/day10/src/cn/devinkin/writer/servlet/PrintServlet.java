package cn.devinkin.writer.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@WebServlet(name = "PrintServlet", urlPatterns = {"/PrintServlet"})
public class PrintServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 打印表格
         * 2. 获取字符流
         */
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        ServletOutputStream os = response.getOutputStream();
        PrintWriter w = response.getWriter();
        w.print("<table border='1'><tr>");
        w.print("<td>用户名</td>");
        w.print("<td>tom</td>");
        w.print("</tr>");
        w.print("<tr>");
        w.print("<td>密码</td>");
        w.print("<td>1234</td>");
        w.print("</tr></table>");
    }
}
