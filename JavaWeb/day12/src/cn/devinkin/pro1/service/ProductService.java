package cn.devinkin.pro1.service;

import cn.devinkin.pro1.dao.ProductDao;
import cn.devinkin.pro1.domain.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDao productDao = new ProductDao();
    public List<Product> findAll() throws SQLException {
        return productDao.findAll();
    }
}
