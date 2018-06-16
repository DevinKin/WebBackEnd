package cn.devinkin.product.web.servlet;

import cn.devinkin.category.domain.Category;
import cn.devinkin.product.domain.Product;
import cn.devinkin.product.service.ProductService;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.UUIDUtils;
import cn.devinkin.utils.UploadUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import sun.nio.ch.IOUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "AddProductServlet", urlPatterns = "/addProduct")
public class AddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 创建map，放入前台传入的数据
         * 2. 封装参数
         * 3. 调用service完成添加商品操作
         * 4. 页面重定向
         */
        HashMap<String, Object> map = new HashMap<>();


        /**
         * 1. 创建磁盘文件项
         * 2. 创建核心上传对象
         * 3. 解析request
         */
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> list = null;
        try {
            list = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        //遍历集合
        for (FileItem fi : list) {
            //判断是否是普通的上传组件
            if (fi.isFormField()) {
                map.put(fi.getFieldName(), fi.getString("utf-8"));
            } else {
                /**
                 * 文件上传组件
                 * 获取文件的真实名称
                 * 获取文件的存放路径
                 * 获取文件的流
                 * 保存图片
                 */
                String name = fi.getName();

                String realName = UploadUtils.getRealName(name);

                String uuidName = UploadUtils.getUUIDName(realName);

                String dir = UploadUtils.getDir(uuidName);

                String realPath = this.getServletContext().getRealPath("/store/products") + dir;

                File file = new File(realPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                System.out.println("文件存放的真是路径：" + realPath);

                InputStream is = fi.getInputStream();

                FileOutputStream os = new FileOutputStream(new File(realPath, uuidName));

                IOUtils.copy(is, os);
                os.close();
                is.close();
                //删除临时文件
                fi.delete();

                //在map中设置文件的路径
                map.put(fi.getFieldName(), "products" + dir + "/" + uuidName);
            }
        }

        Product p = new Product();
        try {
            BeanUtils.populate(p, map);

            /**
             * 封装参数
             * 1. 商品id
             * 2. 商品时间
             * 3. 封装Category
             */
            p.setPid(UUIDUtils.getId());
            p.setPdate(new Date());
            Category category = new Category();
            category.setCid((String) map.get("cid"));
            p.setCategory(category);


            ProductService productService = (ProductService) BeanFactory.getBean("ProductService");
            productService.addProduct(p);

            response.sendRedirect(request.getContextPath() + "/adminProduct?method=findByPage&currentPage=1");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "商品添加失败");
            request.getRequestDispatcher("/store/jsp/msg.jsp").forward(request, response);
            return;
        }


    }
}
