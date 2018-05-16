package cn.devinkin.request.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Dispatcher2Servlet",urlPatterns = {"/Dispatcher2Servlet"})
public class Dispatcher2Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("OK，我来帮你了: " + request.getParameter("money"));
        System.out.println(request.getAttribute("username"));
    }
}
