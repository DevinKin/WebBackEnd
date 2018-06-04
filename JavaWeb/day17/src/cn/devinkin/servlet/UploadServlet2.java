package cn.devinkin.servlet;

import cn.devinkin.utils.UploadUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@WebServlet(name = "UploadServlet2",urlPatterns = "/c3/upload2")
@MultipartConfig
public class UploadServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取普通的上传组件 username
         * 2. 获取文件上传组件
         * 3. 获取文件名称
         * 4. 获取随机名称
         * 5. 获取文件存放的目录
         * 6. 获取文件，拷贝流
         */
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        System.out.println(username);
        Part part = request.getPart("img");
        String sss = part.getHeader("Content-Disposition");
        String realName = sss.substring(sss.indexOf("filename=") + 10, sss.length()-1);
        System.out.println("realName: " + realName);

        String uuidName =  UploadUtils.getUUIDName(realName);
        System.out.println("uuidName: " + uuidName);

        String dir = UploadUtils.getDir(uuidName);
        String realPath =  this.getServletContext().getRealPath("/upload"+ dir);
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        System.out.println("文件存放目录: " + realPath);

        InputStream is = part.getInputStream();
        OutputStream os = new FileOutputStream(new File(realPath,uuidName));
        IOUtils.copy(is,os);
        os.close();
        is.close();
        part.delete();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter("test"));
    }
}
