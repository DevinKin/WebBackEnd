package cn.devinkin.utils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "VerifyCodeServlet", urlPatterns = {"/VerifyCodeServlet"})
public class VerifyCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //使用java图形界面技术绘制一张图片
        int charNum = 4;
        int width = 30 * 4;
        int height = 30;

        //1. 创建一张内存图片，画纸
        BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //2. 获得画笔
        Graphics graphics = bufferedImage.getGraphics();

        //3. 绘制背景颜色
        graphics.setColor(Color.orange);
        graphics.fillRect(0,0,width, height);

        //4. 绘制边框
        graphics.setColor(Color.blue);
        graphics.drawRect(0,0,width -1,height -1);
        graphics.setFont(new Font("宋体", Font.BOLD, 20));

        //5. 随机输出4个字符
        Graphics2D graphics2D = (Graphics2D) graphics;
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int slen = s.length();
        Random random = new Random();
        int x = 5;
        String code="";
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(slen);
            String character = String.valueOf(s.charAt(index));
            code += character;
            double theta = random.nextInt(45)*Math.PI/180;
            //让字体扭曲
            graphics2D.rotate(theta, x, 18);
            graphics2D.drawString(character, x, 18);
            graphics2D.rotate(-theta, x, 18);
            x+=30;
        }
        request.getSession().setAttribute("sessionCode", code);

        //6. 绘制干扰线
        graphics.setColor(Color.GRAY);
        for (int i = 0; i < 5; i++) {
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);
            graphics.drawLine(x1,y1,x2,y2);
        }

        //7. 释放资源
        graphics.dispose();

        //8. 图片输出ImageIO
        ImageIO.write(bufferedImage,"jpg",response.getOutputStream());
    }
}
