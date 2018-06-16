package cn.devinkin.category.dao.impl;

import cn.devinkin.category.dao.CategoryDao;
import cn.devinkin.category.domain.Category;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    private QueryRunner qra = new QueryRunner();

    @Override
    public List<Category> findAll() throws Exception {
        String sql = "select * from category";
        return qr.query(sql, new BeanListHandler<>(Category.class));
    }

    /**
     * 添加分类
     *
     * @param category 分类
     * @throws Exception
     */
    @Override
    public void addCategory(Category category) throws Exception {
        String sql = "insert into category values(?,?)";
        Object[] params = {category.getCid(), category.getCname()};
        qr.update(sql, params);
    }

    /**
     * 通过cid获取Category
     *
     * @param cid 分类id
     * @return 分类
     * @throws Exception
     */
    @Override
    public Category getCategoryByCid(String cid) throws Exception {
        String sql = "select * from category where cid=?";
        return qr.query(sql, new BeanHandler<>(Category.class), cid);
    }

    /**
     * 修改分类
     *
     * @param category 分类
     * @throws Exception
     */
    @Override
    public void update(Category category) throws Exception {
        String sql = "update category set cname = ? where cid=?";
        Object[] params = {category.getCname(), category.getCid()};
        qr.update(sql, params);
    }


    /**
     * 删除分类
     *
     * @param cid 分类id
     * @throws Exception
     */
    @Override
    public void delete(String cid) throws Exception {
        String sql = "delete from category where cid=?";
        qra.update(DataSourceUtils.getConnection(), sql, cid);
    }
}
