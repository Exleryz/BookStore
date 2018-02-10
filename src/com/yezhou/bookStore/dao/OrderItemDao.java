package com.yezhou.bookStore.dao;

import com.yezhou.bookStore.domain.Order;
import com.yezhou.bookStore.util.C3P0Util;
import com.yezhou.bookStore.util.ManagerThreadLocal;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class OrderItemDao {

    /**
     * 添加订单项
     *
     * @param order
     * @throws SQLException
     */
    public void addOrderItem(Order order) throws SQLException {
        QueryRunner qr = new QueryRunner();

        Object[][] params = new Object[order.getOrderItems().size()][];
        for (int i = 0; i < params.length; i++) {
            // 订单id        商品id                                       商品购买数量
            params[i] = new Object[]{order.getId(), order.getOrderItems().get(i).getP().getId(), order.getOrderItems().get(i).getBuynum()};    //
        }
        qr.batch(ManagerThreadLocal.getConnection(), "insert into orderitem values(?,?,?)", params);
    }
}
