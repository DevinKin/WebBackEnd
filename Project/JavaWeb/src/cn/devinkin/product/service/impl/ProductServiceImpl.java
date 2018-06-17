package cn.devinkin.product.service.impl;

import cn.devinkin.product.dao.ProductDao;
import cn.devinkin.product.domain.PageBean;
import cn.devinkin.product.domain.Product;
import cn.devinkin.product.service.ProductService;
import cn.devinkin.utils.BeanFactory;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = (ProductDao) BeanFactory.getBean("ProductDao");

    /**
     * 查询最新商品
     *
     * @return 最新商品列表
     * @throws Exception
     */
    @Override
    public List<Product> findNewest() throws Exception {
        return productDao.findNewest();
    }

    /**
     * 查询热门商品
     *
     * @return 热门商品列表
     * @throws Exception
     */
    @Override
    public List<Product> findHotest() throws Exception {
        return productDao.findHotest();
    }

    /**
     * 通过pid获取商品
     *
     * @param pid 商品id
     * @return 商品
     * @throws Exception
     */
    @Override
    public Product getProductByPid(String pid) throws Exception {
        return productDao.getProductByPid(pid);
    }

    /**
     * 分页查询商品
     *
     * @param currentPage 当前页码
     * @param pageSize    总页数
     * @param cid         分类id
     * @param pflag
     * @return 商品列表
     * @throws Exception
     */
    @Override
    public PageBean<Product> findByPage(int currentPage, int pageSize, String cid, int pflag) throws Exception {
        //当前页数据
        List<Product> productList = productDao.findByCurrentPage(currentPage, pageSize, cid, pflag);

        //该分类商品总记录数
        int totalRecord = productDao.getTotalRecord(cid,pflag);

        //封装PageBean对象
        return new PageBean<>(productList, currentPage, pageSize, totalRecord);
    }


    /**
     * 分页查询所有商品
     *
     *
     * @param currentPage 当前页
     * @param currentPage
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public PageBean<Product> findAllByPage(int currentPage, int pageSize, int pflag) throws Exception {
        //当前页数据
        List<Product> productList = productDao.findAllByPage(currentPage, pageSize, pflag);

        //所有商品的记录数
        int totalRecord = productDao.getAllProductRecord(pflag);

        return new PageBean<>(productList, currentPage, pageSize, totalRecord);
    }



    /**
     * 添加商品
     *
     * @param p
     * @throws Exception
     */
    @Override
    public void addProduct(Product p) throws Exception {
        productDao.addProduct(p);
    }


    /**
     * 更新商品信息
     *
     * @param p 商品
     * @throws Exception
     */
    @Override
    public void updateProduct(Product p) throws Exception {
        productDao.updateProduct(p);
    }


    /**
     * 下架商品
     *
     * @param pid 商品id
     * @throws Exception
     */
    @Override
    public void unShelveProduct(String pid) throws Exception {
        productDao.unShelveProduct(pid);
    }


    /**
     * 上架商品
     *
     * @param pid 商品id
     * @throws Exception
     */
    @Override
    public void shelveProduct(String pid) throws Exception {
        productDao.shelveProduct(pid);
    }
}
