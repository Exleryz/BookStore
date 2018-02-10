package com.yezhou.bookStore.dao;

import com.yezhou.bookStore.domain.Order;
import com.yezhou.bookStore.domain.OrderItem;
import com.yezhou.bookStore.domain.Product;
import com.yezhou.bookStore.util.C3P0Util;
import com.yezhou.bookStore.util.ManagerThreadLocal;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    /**
     * 添加订单
     */
    public void addOrder(Order order) throws SQLException {
        QueryRunner qr = new QueryRunner();
        qr.update(ManagerThreadLocal.getConnection(), "insert into orders values(?,?,?,?,?,?,?,?)",
                order.getId(), order.getMoney(), order.getReceiverAddress(), order.getReceiverName(),
                order.getReceiverPhone(), order.getPaystate(),
                order.getOrdertime(), order.getUser().getId());
    }

    /**
     * 根据用户id查询订单
     *
     * @param id
     * @throws SQLException
     */
    public List<Order> findOrders(int id) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        return qr.query("select * from orders where user_id=?",
                new BeanListHandler<Order>(Order.class), id);
    }

    /**
     * 查询指定用户的订单信息
     *
     * @param orderid
     * @return
     */
    public Order findOrdersById(String orderid) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());

        // 得到订单
        Order order = qr.query("select * from orders where id=?", new BeanHandler<Order>(Order.class), orderid);
        /**
         * // 得到当前订单中的所有子单
         List<OrderItem> orderItems = qr.query("select * from orderItem where order_id=? ",
         new BeanListHandler<OrderItem>(OrderItem.class), orderid);

         // 子单中的商品信息
         List<Product> products = qr.query("select * from product where id in (select product_id from ordersItem where order_id=?)",
         new BeanListHandler<Product>(Product.class), orderid);

         */
        List<OrderItem> orderItems = qr.query("SELECT * FROM orderitem o,products p WHERE p.id=o.product_id AND order_id=?", new ResultSetHandler<List<OrderItem>>() {
            @Override
            public List<OrderItem> handle(ResultSet resultSet) throws SQLException {
                List<OrderItem> orderItems = new ArrayList<OrderItem>();
                while (resultSet.next()) {
                    OrderItem oi = new OrderItem();
                    oi.setBuynum(resultSet.getInt("buynum"));

                    Product p = new Product();
                    p.setName(resultSet.getString("name"));
                    p.setPrice(resultSet.getDouble("price"));

                    oi.setP(p);

                    orderItems.add(oi);
                }

                return orderItems;
            }
        }, orderid);

        order.setOrderItems(orderItems);
        return order;
    }
}
