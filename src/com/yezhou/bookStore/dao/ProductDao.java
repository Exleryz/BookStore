package com.yezhou.bookStore.dao;

import com.yezhou.bookStore.domain.Order;
import com.yezhou.bookStore.domain.Product;
import com.yezhou.bookStore.util.C3P0Util;
import com.yezhou.bookStore.util.ManagerThreadLocal;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    /**
     * 修改商品数量
     */
    public void updateProductNum(Order order) throws SQLException {
        QueryRunner qr = new QueryRunner();

        Object[][] params = new Object[order.getOrderItems().size()][];
        for (int i = 0; i < params.length; i++) {
            params[i] = new Object[]{order.getOrderItems().get(i).getBuynum(),
                    order.getOrderItems().get(i).getP().getId()};
        }
        qr.batch(ManagerThreadLocal.getConnection(), "update products set pnum=pnum-? where id=?", params);
    }

    /**
     * 查找所有图书
     *
     * @return
     * @throws SQLException
     */
    public List<Product> findAllBooks() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        return qr.query("select * from products", new BeanListHandler<Product>(Product.class));
    }

    /**
     * 添加图书信息
     *
     * @param product
     * @throws SQLException
     */
    public void addBook(Product product) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        qr.update("insert into products values(?,?,?,?,?,?,?)", product.getId(), product.getName(), product.getPrice(), product.getPnum(), product.getCategory(), product.getDescription(), product.getImg_url());
    }

    /**
     * 根据id查询图书
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public Product findBookById(String id) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        return qr.query("select * from products where id=?", new BeanHandler<Product>(Product.class), id);
    }

    /**
     * 修改图书信息
     *
     * @param product
     * @throws SQLException
     */
    public void updateBook(Product product) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        qr.update("update products set name=?,price=?,pnum=?,category=?," +
                        "description=? where id=?",
                product.getName(), product.getPrice(), product.getPnum()
                , product.getCategory(), product.getDescription(), product.getId());
    }

    /**
     * 根据id删除图书
     *
     * @param id
     * @throws SQLException
     */
    public void delBook(String id) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        qr.update("delete from products where id=?", id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @throws SQLException
     */
    public void deleAllBooks(String[] ids) throws SQLException {    // batch批量操作
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        Object[][] params = new Object[ids.length][];
        for (int i = 0; i < params.length; i++) {
            params[i] = new Object[]{ids[i]};    // 循环给每个一维数组赋值， 值是id
        }
        qr.batch("delete from products where id=?", params);
    }

    /**
     * 多条件查询图书
     *
     * @param id
     * @param category
     * @param name
     * @param minprice
     * @param maxprice
     * @return
     * @throws SQLException
     */
    public List<Product> searchBooks(String id, String category, String name, String minprice, String maxprice) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        return qr.query("", new BeanListHandler<Product>(Product.class));
    }

    /**
     * 得到总记录数
     *
     * @param category
     * @return
     * @throws SQLException
     */
    public int count(String category) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        String sql = "select count(*) from products";
        if (!category.equals("")) {
            sql += " where category='" + category + "'";
        }
        long l = (long) qr.query(sql, new ScalarHandler(1));
        return (int) l;
    }


    public List<Product> findBooks(int currentPage, int pageSize, String category) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());

        String sql = "select * from products where 1=1";
        List list = new ArrayList();
        if (!"".equals(category)) {
            sql += " and category=?";
            list.add(category);
        }
        sql += " limit ?,?";
        // select * from products where 1=1 and category=? limit ?,?;
        list.add((currentPage - 1) * pageSize);
        list.add(pageSize);

        return qr.query(sql, new BeanListHandler<Product>(Product.class), list.toArray());

    }

    /**
     * 根据书名查找图书 模糊查询
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public List<Object> searchBookByName(String name) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        return qr.query("select name from products where name like ?",
                new ColumnListHandler(), "%" + name + "%");    // ColumnListHandler将结果集中某一列的数据存放到List中
    }
}
