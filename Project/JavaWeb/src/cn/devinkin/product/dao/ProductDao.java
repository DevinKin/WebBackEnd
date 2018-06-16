package cn.devinkin.product.dao;

import cn.devinkin.product.domain.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findNewest() throws Exception;

    List<Product> findHotest() throws Exception;

    Product getProductByPid(String pid) throws Exception;

    List<Product> findByCurrentPage(int currentPage, int pageSize, String cid) throws Exception;

    List<Product> findByCurrentPageWithFlag(int currentPage, int pageSize, String cid) throws Exception;

    int getTotalRecord(String cid) throws Exception;

    int getTotalRecordWithFlag(String cid) throws Exception;

    void deleteCategory(String cid) throws Exception;

    List<Product> findAllByPage(int currentPage, int pageSize) throws Exception;

    int getAllProductRecord() throws Exception;

    void addProduct(Product p) throws Exception;

    void updateProduct(Product p) throws Exception;

    void unShelveProduct(String pid) throws Exception;

    void shelveProduct(String pid) throws Exception;
}
