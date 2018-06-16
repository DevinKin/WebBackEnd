package cn.devinkin.product.web.servlet;

import cn.devinkin.category.domain.Category;
import cn.devinkin.product.domain.PageBean;
import cn.devinkin.product.domain.Product;
import cn.devinkin.product.service.ProductService;
import cn.devinkin.utils.BaseServlet;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.UploadUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 后台商品管理
 */
@WebServlet(name = "AdminProductServlet", urlPatterns = "/adminProduct")
@MultipartConfig
public class AdminProductServlet extends BaseServlet {
    private ProductService productService = (ProductService) BeanFactory.getBean("ProductService");

    /**
     * 根据分类分页查找商品
     *
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取currentPage参数
         * 2. 设置每页显示数量
         * 3. 获取分类id
         * 4. 调用service的findByPage方法，获取PageBean
         * 5. 将PageBean放入request域中
         * 6. 请求转发到/admin/list.jsp
         */
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        String cid = request.getParameter("cid");
        final int pageSize = 8;
        PageBean<Product> pb = null;
        try {
            if (cid == null) {
                pb = productService.findAllByPage(currentPage, pageSize);
            } else {
                pb = productService.findByPage(currentPage, pageSize, cid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("pb", pb);
        return "f:/store/admin/product/list.jsp";
    }


    /**
     * 添加商品UI
     *
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "f:/store/admin/product/add.jsp";
    }


    /**
     * 编辑商品页面
     *
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String editProductUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取pid
         * 2. 调用ProductService查找Product
         * 3. 将product放入request域中
         * 4. 请求转发到/store/admin/product/edit.jsp
         */
        String pid = request.getParameter("pid");
        Product product = null;
        try {
            product = productService.getProductByPid(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("p", product);
        return "f:/store/admin/product/edit.jsp";
    }


    /**
     * 编辑商品
     *
     * @param request
     * @param response
     * @return 重定向路径
     * @throws ServletException
     * @throws IOException
     */
    public String editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取参数，并封装Product
         * 2. 获取文件上传组件
         * 3. 获取文件名
         * 4. 获取文件存放名称
         * 5. 获取文件流
         * 6. 拷贝流
         * 7. 关闭输入流，输出流，删除临时文件
         */
        Map<String, String[]> map = request.getParameterMap();
        Product p = null;
        try {
            p = productService.getProductByPid(map.get("pid")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Part part = request.getPart("pimage");
        String sss = part.getHeader("Content-Disposition");
        //获取文件真实路径
        String realName = sss.substring(sss.indexOf("filename=") + 10, sss.length() - 1);
        //若有上传文件，则保存图片
        if (!realName.trim().isEmpty()) {

            //获取文件存放路径
            String uuidName = UploadUtils.getUUIDName(realName);
            //获取文件存放目录
            String dir = UploadUtils.getDir(uuidName);

            //获取文件真实存放目录
            String realPath = this.getServletContext().getRealPath("/store/products") + dir;

            //创建目录
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            //获取文件流
            InputStream is = part.getInputStream();
            FileOutputStream os = new FileOutputStream(new File(realPath, uuidName));
            IOUtils.copy(is, os);
            //封装pimage参数
            p.setPimage("products" + dir + "/" + uuidName);
            //关闭流，删除临时文件
            os.close();
            is.close();
            part.delete();
        }


        try {
            BeanUtils.populate(p, map);
            Category category = new Category();
            BeanUtils.populate(category, map);
            p.setCategory(category);
            productService.updateProduct(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "r:/adminProduct?method=findByPage&currentPage=1";
    }


    /**
     * 下架商品
     *
     * @param request
     * @param response
     * @return 重定向路径
     * @throws ServletException
     * @throws IOException
     */
    public String unShelveProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取pid参数
         * 2. 调用service完成删除操作
         * 3. 重定向到所有商品页面
         */
        String pid = request.getParameter("pid");
        try {
            productService.unShelveProduct(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "r:/adminProduct?method=findByPage&currentPage=1";
    }


    /**
     * 上架商品
     * @param request
     * @param response
     * @return 重定向路径
     * @throws ServletException
     * @throws IOException
     */
    public String shelveProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        try {
            productService.shelveProduct(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "r:/adminProduct?method=findByPage&currentPage=1";
    }
}
