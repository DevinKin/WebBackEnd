package cn.devinkin.test.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SContextServlet", urlPatterns = {"/SContextServlet"})
public class SContextServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = this.getServletContext();
        System.out.println(context.getInitParameter("encoding"));
        System.out.println(context.getRealPath("/"));
        System.out.println(context.getResourceAsStream("login.htm"));
        System.out.println(context.getMimeType("login.htm"));
    }
}
