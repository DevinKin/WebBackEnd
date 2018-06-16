package cn.devinkin.category.service.impl;

import cn.devinkin.category.dao.CategoryDao;
import cn.devinkin.category.dao.impl.CategoryDaoImpl;
import cn.devinkin.category.domain.Category;
import cn.devinkin.category.service.CategoryService;
import cn.devinkin.product.dao.ProductDao;
import cn.devinkin.product.domain.Product;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.DataSourceUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.InputStream;
import java.util.List;

public class CategoryServiceImpl implements CategoryService{
    private CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("CategoryDao");
    /**
     * 查询所有分类
     * @return 分类的集合
     * @throws Exception
     */
    @Override
    public List<Category> findAll() throws Exception {
        /**
         * 1. 创建缓存管理器
         * 2. 获取指定的缓存
         * 3. 通过指定的缓存获取数据
         * 4. 判断数据是否为空
         */
        //1
        CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
        //2
        Cache cache = cm.getCache("categoryCache");
        //将Cache看成map集合
        Element element = cache.get("clist");
        List<Category> clist = null;
        //4
        if (element == null) {
            //从数据库中获取
            clist = categoryDao.findAll();

            //将list放入缓存
            cache.put(new Element("clist", clist));
            System.out.println("缓存中没有数据，你去数据库中获取");
        } else {
            //直接返回
            clist = (List<Category>) element.getObjectValue();
            System.out.println("缓存中有数据");
        }
        return clist;
    }

    /**
     * 添加分类
     * @param category 分类
     * @throws Exception
     */
    @Override
    public void addCategory(Category category) throws Exception {
        categoryDao.addCategory(category);
        //添加成功以后，更新缓存
        cacheRemove();
    }

    @Override
    public void cacheRemove() {
        /**
         * 1. 获取缓存管理器
         * 2. 获取指定缓存
         * 3. 清空缓存
         */
        CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
        Cache cache = cm.getCache("categoryCache");
        cache.remove("clist");
    }


    /**
     * 通过cid获取Category
     * @param cid 分类id
     * @return 分类
     * @throws Exception
     */
    @Override
    public Category getCategoryByCid(String cid) throws Exception {
        return categoryDao.getCategoryByCid(cid);
    }


    /**
     * 修改分类操作
     * @param category 分类
     * @throws Exception
     */
    @Override
    public void updateCategory(Category category) throws Exception {
        /**
         * 1. 调用dao修改分类
         * 2. 清空缓存clist
         */
        categoryDao.update(category);
        cacheRemove();
    }

    /**
     * 删除分类
     * @param cid 分类id
     * @throws Exception
     */
    @Override
    public void deleteCategory(String cid) throws Exception {
        /**
         * 1. 开启事务
         * 2. 调用productDao将对应分类置为null
         * 3. 调用categoryDao删除分类
         * 4. 事务控制
         * 5. 清空缓存
         */
        DataSourceUtils.beginTransaction();
        ProductDao productDao = (ProductDao) BeanFactory.getBean("ProductDao");
        try {
            productDao.deleteCategory(cid);
            categoryDao.delete(cid);
        } catch (Exception e) {
            e.printStackTrace();
            DataSourceUtils.rollbackTransaction();
        }
        DataSourceUtils.commitTransaction();
        cacheRemove();
    }


//    public static void main(String[] args) {
//        InputStream is = CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
//        System.out.println(is);
//    }
}
