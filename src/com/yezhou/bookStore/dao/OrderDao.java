package com.yezhou.bookStore.dao;

import com.yezhou.bookStore.domain.Order;
import com.yezhou.bookStore.util.C3P0Util;
import com.yezhou.bookStore.util.ManagerThreadLocal;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

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
}
