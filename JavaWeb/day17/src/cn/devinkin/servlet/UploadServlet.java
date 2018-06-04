package cn.devinkin.servlet;


import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "UploadServlet", urlPatterns = {"/c3/upload1"})
@MultipartConfig
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取username
         * 2. 获取img
         */
        String username = request.getParameter("username");
        System.out.println(username);
//        String img = request.getParameter("img");
//        System.out.println(img);
        Part part = request.getPart("img");
//        System.out.println(part);
        System.out.println(part.getName());
        InputStream is = part.getInputStream();
        OutputStream outputStream = new FileOutputStream("/home/king/makise.jpg");
        IOUtils.copy(is,outputStream);
        is.close();
        outputStream.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
