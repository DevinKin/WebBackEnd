package cn.devinkin.case1.service;

import cn.devinkin.case1.dao.ProductDao;
import cn.devinkin.case1.domain.PageBean;
import cn.devinkin.case1.domain.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDao productDao = new ProductDao();

    /**
     * 查询所有商品
     * @return list
     */
    public List<Product> findAll() throws SQLException {
        return productDao.findAll();
    }

    /**
     * 添加商品
     * @param product
     */
    public void addProduct(Product product) throws SQLException {
        productDao.addProduct(product);
    }

    /**
     * 通过id查找商品
     * @param pid 商品id
     * @return product
     */
    public Product getProductById(String pid) throws SQLException {
        return productDao.getProductById(pid);
    }

    /**
     * 更新商品信息
     * @param product 商品信息
     */
    public void updateProduct(Product product) throws SQLException {
        productDao.updateProduct(product);
    }

    /**
     * 通过id删除商品信息
     * @param pid 商品id
     */
    public void deleteProductById(String pid) throws SQLException {
        productDao.deleteProductById(pid);
    }

    /**
     * 删除多个商品
     * @param pids 多个商品的id
     */
    public void deleteProducts(String[] pids) throws SQLException {
        productDao.deleteProducts(pids);
    }

    /**
     * 根据条件查询商品
     * @param pname 商品名称
     * @param keyword 商品关键字
     * @return 商品列表
     */
    public List<Product> findProductByConditions(String pname, String keyword) throws SQLException {
        return productDao.findProductByConditions(pname,keyword);
    }

    /**
     * 分页查询
     * @param currentPage 当前页码
     * @param pagesize 每页显示的条数
     * @return
     */
    public PageBean<Product> showProductsByPage(int currentPage, int pagesize) throws SQLException {
        //查询当前页数据，limit (当前页-1)*每页显示条数 每页显示的条数
        List<Product> list = productDao.findProductByPage(currentPage, pagesize);

        //查询总条数
        int totalCount = productDao.getTotalRecord();
        return new PageBean<Product>(list, currentPage, pagesize, totalCount);

    }
}
