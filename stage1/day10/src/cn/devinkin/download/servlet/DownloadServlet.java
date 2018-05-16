package cn.devinkin.download.servlet;

import cn.devinkin.utils.DownLoadUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

@WebServlet(name = "DownloadServlet",urlPatterns = {"/download1"})
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 文件下载
         * 1. 获取文件名
         * 2. 获取文件MIME类型
         * 3. 设置响应头的MIME类型
         * 4. 设置下载头信息content-disposition
         * 5. 获取输入流和输出流，拷贝流
         */
        ServletContext context = this.getServletContext();
        String filename = request.getParameter("name");
        //处理中文乱码
        filename = new String(filename.getBytes("iso8859-1"),"utf-8");
        String MimeType = context.getMimeType("html/" + filename);
        response.setContentType(MimeType);
        //解决中文乱码问题
//        filename = new String(filename.getBytes("utf-8"), "iso8859-1");
        String userAgent = request.getHeader("user-agent");
        String encodName = DownLoadUtils.getFileName(userAgent, filename);

        response.setHeader("content-disposition", "attachment;filename=" + encodName);

//        response.setHeader("content-disposition", "attachment;filename=" + filename);

        InputStream is = context.getResourceAsStream("html/" + filename);
        ServletOutputStream os = response.getOutputStream();
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = is.read(b)) != -1) {
            os.write(b, 0, len);
        }
        os.close();
        is.close();
    }
}
