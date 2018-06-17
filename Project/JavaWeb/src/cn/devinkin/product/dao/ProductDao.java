package cn.devinkin.product.dao;

import cn.devinkin.product.domain.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findNewest() throws Exception;

    List<Product> findHotest() throws Exception;

    Product getProductByPid(String pid) throws Exception;

    List<Product> findByCurrentPage(int currentPage, int pageSize, String cid, int pflag) throws Exception;

    int getTotalRecord(String cid, int pflag) throws Exception;

    void deleteCategory(String cid) throws Exception;

    List<Product> findAllByPage(int currentPage, int pageSize, int pflag) throws Exception;

    int getAllProductRecord(int pflag) throws Exception;

    void addProduct(Product p) throws Exception;

    void updateProduct(Product p) throws Exception;

    void unShelveProduct(String pid) throws Exception;

    void shelveProduct(String pid) throws Exception;
}
