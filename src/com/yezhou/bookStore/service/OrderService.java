package com.yezhou.bookStore.service;

import com.yezhou.bookStore.dao.OrderDao;
import com.yezhou.bookStore.dao.OrderItemDao;
import com.yezhou.bookStore.dao.ProductDao;
import com.yezhou.bookStore.domain.Order;
import com.yezhou.bookStore.exception.OrderException;
import com.yezhou.bookStore.util.ManagerThreadLocal;

import java.sql.SQLException;
import java.util.List;


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

    public List<Order> findOrdersByUserId(int id) {
        try {
            return orderDao.findOrders(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order findOrdersByOrderId(String orderid) {
        try {
            return orderDao.findOrdersById(orderid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void midifyOrderState(String orderid) throws OrderException {
        try {
            orderDao.modifyOrderState(orderid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new OrderException("修改失败");
        }
    }

}
