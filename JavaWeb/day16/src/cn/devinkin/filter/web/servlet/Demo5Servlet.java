package cn.devinkin.filter.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Demo5Servlet",urlPatterns = {"/e/Demo5Servlet"})
public class Demo5Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Demo5Servlet执行了，转发到Demo6Servlet执行");
        request.getRequestDispatcher("/e/Demo6Servlet").forward(request,response);
    }
}
