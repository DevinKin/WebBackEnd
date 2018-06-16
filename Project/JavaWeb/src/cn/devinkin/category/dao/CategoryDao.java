package cn.devinkin.category.dao;

import cn.devinkin.category.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll() throws Exception;

    void addCategory(Category category) throws Exception;

    Category getCategoryByCid(String cid) throws Exception;

    void update(Category category) throws Exception;

    void delete(String cid) throws Exception;
}
