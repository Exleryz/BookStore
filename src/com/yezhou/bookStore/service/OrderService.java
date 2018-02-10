package com.yezhou.bookStore.service;

import com.yezhou.bookStore.dao.OrderDao;
import com.yezhou.bookStore.dao.OrderItemDao;
import com.yezhou.bookStore.dao.ProductDao;
import com.yezhou.bookStore.domain.Order;
import com.yezhou.bookStore.util.ManagerThreadLocal;

import java.sql.SQLException;


public class OrderService {

    OrderDao orderDao = new OrderDao();
    OrderItemDao orderItemDao = new OrderItemDao();
    ProductDao productDao = new ProductDao();

    public void addOrder(Order order) {
        try {
            ManagerThreadLocal.startTransacation();
            orderDao.addOrder(order);
            orderItemDao.addOrderItem(order);
            productDao.updateProductNum(order);
            ManagerThreadLocal.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            ManagerThreadLocal.rollback();

        }
    }

}
