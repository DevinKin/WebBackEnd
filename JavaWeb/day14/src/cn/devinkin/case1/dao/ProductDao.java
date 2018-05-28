package cn.devinkin.case1.dao;

import cn.devinkin.case1.domain.Product;
import cn.devinkin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    /**
     * 查找所有商品
     * @return List<Product>
     * @throws SQLException
     */
    public List<Product> findAll() throws SQLException {
        String sql = "select * from product";
        return qr.query(sql, new BeanListHandler<Product>(Product.class));
    }

    /**
     * 添加商品
     * @param product 所添加的商品
     * @throws SQLException
     */
    public void addProduct(Product product) throws SQLException {
        String sql = "insert into product(pid,pname,market_price,shop_price,pdate,pdec) values(?,?,?,?,?,?)";
        Object[] params = {product.getPid(),  product.getPname(), product.getMarket_price(), product.getShop_price(), product.getPdate(), product.getPdec()};
        qr.update(sql, params);
    }

    /**
     * 通过商品id获取商品
     * @param pid 商品id
     * @return product
     */
    public Product getProductById(String pid) throws SQLException {
        String sql = "select * from product where pid=?";
        return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
    }

    /**
     * 修改商品
     * @param product 商品信息
     */
    public void updateProduct(Product product) throws SQLException {
        String sql = "update product set pname = ?, market_price = ?,shop_price = ?,pdec = ? where pid=?";
        Object[] params = {product.getPname(), product.getMarket_price(), product.getShop_price(), product.getPdec(),product.getPid()};
        qr.update(sql, params);
    }

    /**
     * 通过id删除商品信息
     * @param pid 商品id
     */
    public void deleteProductById(String pid) throws SQLException {
        String sql = "delete from product where pid=?";
        qr.update(sql, pid);
    }

    /**
     * 删除多个商品
     * @param pids 多个商品id
     */
    public void deleteProducts(String[] pids) throws SQLException {
        String sql = "delete from product where pid = ?";
        Object[][] params = new Object[pids.length][];
        for (int i = 0; i < pids.length; i++) {
            params[i] = new Object[] {pids[i]};
        }
        qr.batch(sql, params);
    }

    /**
     * 根据条件查询商品
     * @param pname 商品名称
     * @param keyword 商品关键字
     * @return 返回值
     */
    public List<Product> findProductByConditions(String pname, String keyword) throws SQLException {
        String baseSql = "select * from product where 1=1 ";
        StringBuilder sql = new StringBuilder(baseSql);
        ArrayList<String> params = new ArrayList<>();
        if (pname != null || pname.trim().length() > 0) {
            sql.append("and pname like ? ");
            pname = "%" + pname + "%";
            params.add(pname);
        }
        if (keyword != null || keyword.trim().length() > 0) {
            sql.append("and pdec like ? ");
            keyword = "%" + keyword + "%";
            params.add(keyword);
        }
        return qr.query(sql.toString(), new BeanListHandler<Product>(Product.class), params.toArray());
    }

    /**
     * 查询当前页码的数据
     * @param currentPage 当前页码
     * @param pagesize 每页记录数
     * @return 总记录数
     */
    public List<Product> findProductByPage(int currentPage, int pagesize) throws SQLException {
        String sql = "select * from product limit ?,?";
        Object[] params = {(currentPage - 1)*pagesize, pagesize};
        return qr.query(sql, new BeanListHandler<Product>(Product.class), params);
    }

    /**
     * 查询总记录数
     * @return 总记录数
     */
    public int getTotalRecord() throws SQLException {
        String sql = "select count(*) from product";
        return ((Long)qr.query(sql, new ScalarHandler())).intValue();
    }
}
