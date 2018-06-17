package cn.devinkin.product.service;

import cn.devinkin.product.domain.PageBean;
import cn.devinkin.product.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findNewest() throws Exception;

    List<Product> findHotest() throws Exception;

    Product getProductByPid(String pid) throws Exception;

    PageBean<Product> findByPage(int currentPage, int pageSize, String cid, int pflag) throws Exception;

    PageBean<Product> findAllByPage(int currentPage, int pageSize,int pflag) throws Exception;

    void addProduct(Product p) throws Exception;

    void updateProduct(Product p) throws Exception;

    void unShelveProduct(String pid) throws Exception;

    void shelveProduct(String pid) throws Exception;
}
