package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.domain.Order;
import com.yezhou.bookStore.domain.OrderItem;
import com.yezhou.bookStore.domain.Product;
import com.yezhou.bookStore.domain.User;
import com.yezhou.bookStore.service.OrderService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "CreateOrderServlet", value = "/createOrderServlet")
public class CreateOrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 封装Order对象
        Order order = new Order();
        try {
            BeanUtils.populate(order, request.getParameterMap());
            order.setId(UUID.randomUUID().toString());
            order.setUser((User) request.getSession().getAttribute("user"));    // 订单所需的用户信息
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. 获得session对象中的购物车数据
        Map<Product, String> cart = (Map<Product, String>) request.getSession().getAttribute("cart");

        // 3.遍历购物车中的数据，添加到OrderItem对象中，同时把多个OrderItem添加到list集合中
        List<OrderItem> list = new ArrayList<OrderItem>();
        for (Product product : cart.keySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setP(product);
            orderItem.setBuynum(Integer.parseInt(cart.get(product)));
            list.add(orderItem);
        }

        // 4. 把集合放入到Order对象中
        order.setOrderItems(list);

        OrderService os = new OrderService();
        os.addOrder(order);

        request.setAttribute("order_id", order.getId());
        request.setAttribute("money", order.getMoney());

        request.getRequestDispatcher("/pay.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
