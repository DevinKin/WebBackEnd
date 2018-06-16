package cn.devinkin.product.dao.impl;

import cn.devinkin.category.domain.Category;
import cn.devinkin.product.dao.ProductDao;
import cn.devinkin.product.domain.Product;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class ProductDaoImpl implements ProductDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    private QueryRunner qra = new QueryRunner();

    /**
     * 查询最新商品
     *
     * @return 最新商品列表
     * @throws Exception
     */
    @Override
    public List<Product> findNewest() throws Exception {
        String sql = "select * from product order by pdate limit 9";
        return qr.query(sql, new BeanListHandler<>(Product.class));
    }

    /**
     * 查询热门商品
     *
     * @return 热门商品列表
     * @throws Exception
     */
    @Override
    public List<Product> findHotest() throws Exception {
        String sql = "select * from product where is_hot = 1 limit 9";
        return qr.query(sql, new BeanListHandler<>(Product.class));
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
        String sql = "select * from product p, category c where p.cid = c.cid and pid = ? ";
        Map<String, Object> map = qr.query(sql, new MapHandler(), pid);
        Product product = new Product();
        Category category = new Category();
        loadProduct(product, map);
        loadCategory(category, map);
        product.setCategory(category);
        return product;
    }

    /**
     * 加载分类
     *
     * @param category 分类对象
     * @param map      参数map
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void loadCategory(Category category, Map<String, Object> map) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.populate(category, map);
    }


    /**
     * 加载商品
     *
     * @param product 商品对象
     * @param map     参数map
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void loadProduct(Product product, Map<String, Object> map) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.populate(product, map);
    }

    /**
     * 查找当前页商品
     *
     * @param currentPage 当前页
     * @param pageSize    每页的记录数
     * @param cid 分类id
     * @return 商品列表
     * @throws Exception
     */
    @Override
    public List<Product> findByCurrentPage(int currentPage, int pageSize, String cid) throws Exception {
        String sql = "select * from product where cid = ? limit ?,?";
        Object[] params = {cid, (currentPage - 1) * pageSize, pageSize};
        return qr.query(sql, new BeanListHandler<>(Product.class), params);
    }


    /**
     * 查找当前页商品
     *
     * @param currentPage 当前页
     * @param pageSize    每页的记录数
     * @param cid 分类id
     * @return 商品列表，带有pflag查询
     * @throws Exception
     */
    @Override
    public List<Product> findByCurrentPageWithFlag(int currentPage, int pageSize, String cid) throws Exception {
        String sql = "select * from product where cid = ? and pflag=1 limit ?,? ";
        Object[] params = {cid, (currentPage - 1) * pageSize, pageSize};
        return qr.query(sql, new BeanListHandler<>(Product.class), params);
    }


    /**
     * 查找商品总记录条数
     *
     * @param cid
     * @return 查找商品总记录条数
     * @throws Exception
     */
    @Override
    public int getTotalRecord(String cid) throws Exception {
        String sql = "select count(*) from product where cid=?";
        return ((Long) qr.query(sql, new ScalarHandler(), cid)).intValue();
    }


    /**
     * 查找商品总记录条数，带有pflag查询
     *
     * @param cid
     * @return 查找商品总记录条数
     * @throws Exception
     */
    @Override
    public int getTotalRecordWithFlag(String cid) throws Exception {
        String sql = "select count(*) from product where cid=? and pflag=1";
        return ((Long) qr.query(sql, new ScalarHandler(), cid)).intValue();
    }


    /**
     * 删除商品的分类
     *
     * @param cid 分类id
     * @throws Exception
     */
    @Override
    public void deleteCategory(String cid) throws Exception {
        String sql = "update product set cid = NULL where cid=?";
        qra.update(DataSourceUtils.getConnection(), sql, cid);
    }


    /**
     * 通过当前页获取所有商品
     *
     * @param currentPage
     * @param pageSize
     * @return 商品列表
     * @throws Exception
     */
    @Override
    public List<Product> findAllByPage(int currentPage, int pageSize) throws Exception {
        String sql = "select * from product order by pdate desc limit ?,?";
        Object[] params = {(currentPage - 1) * pageSize, pageSize};
        return qr.query(sql, new BeanListHandler<>(Product.class), params);
    }

    /**
     * 查询商品记录条数
     *
     * @return
     * @throws Exception
     */
    @Override
    public int getAllProductRecord() throws Exception {
        String sql = "select count(*) from product";
        return ((Long) qr.query(sql, new ScalarHandler())).intValue();
    }

    /**
     * 添加商品
     *
     * @param p
     * @throws Exception
     */
    @Override
    public void addProduct(Product p) throws Exception {
        String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {p.getPid(), p.getPname(), p.getMarket_price(),
                p.getShop_price(), p.getPimage(), new Date(p.getPdate().getTime()),
                p.getIs_hot(), p.getPdesc(), p.getPflag(), p.getCategory().getCid()};

        qr.update(sql, params);
    }


    /**
     * 更新商品信息
     *
     * @param p 商品
     * @throws Exception
     */
    @Override
    public void updateProduct(Product p) throws Exception {
        String sql = "update product set pname=?, market_price=?, shop_price=?, pimage=?, is_hot=?, pdesc=?, cid=? where pid=?";
        Object[] params = {p.getPname(), p.getMarket_price(), p.getShop_price(),
                p.getPimage(), p.getIs_hot(), p.getPdesc(), p.getCategory().getCid(),
                p.getPid()};
        qr.update(sql, params);

    }


    /**
     * 下架商品
     *
     * @param pid 商品id
     * @throws Exception
     */
    @Override
    public void unShelveProduct(String pid) throws Exception {
        String sql = "update product set pflag = 0 where pid=?";
        qr.update(sql, pid);
    }

    /**
     * 上架商品
     *
     * @param pid 商品id
     * @throws Exception
     */
    @Override
    public void shelveProduct(String pid) throws Exception {
        String sql = "update product set pflag = 1 where pid=?";
        qr.update(sql, pid);
    }
}
