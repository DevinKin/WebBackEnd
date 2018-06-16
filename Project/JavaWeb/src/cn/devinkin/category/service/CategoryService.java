package cn.devinkin.category.service;

import cn.devinkin.category.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll() throws Exception;

    void addCategory(Category category) throws Exception;
    void cacheRemove();

    Category getCategoryByCid(String cid) throws Exception;

    void updateCategory(Category category) throws Exception;

    void deleteCategory(String cid) throws Exception;
}
