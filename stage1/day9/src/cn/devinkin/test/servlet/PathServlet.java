package cn.devinkin.test.servlet;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PathServlet", urlPatterns = {"/PathServlet"})
public class PathServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Test
    public void func1() {
        String pt = PathServlet.class.getClassLoader().getResource("login.htm").getPath();
        System.out.println(pt);
    }
}
