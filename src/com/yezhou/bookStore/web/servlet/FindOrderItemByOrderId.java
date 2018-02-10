package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.dao.OrderDao;
import com.yezhou.bookStore.dao.OrderItemDao;
import com.yezhou.bookStore.domain.Order;
import com.yezhou.bookStore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FindOrderItemByOrderId", value = "/findOrderItemByOrderId")
public class FindOrderItemByOrderId extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderid = request.getParameter("orderid");

        OrderService os = new OrderService();
        Order order = os.findOrdersByOrderId(orderid);

        request.setAttribute("order", order);
        request.getRequestDispatcher("/orderInfo.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
