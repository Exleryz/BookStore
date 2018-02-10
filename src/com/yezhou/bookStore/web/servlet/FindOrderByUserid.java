package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.domain.Order;
import com.yezhou.bookStore.domain.User;
import com.yezhou.bookStore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FindOrderByUserid", value = "/findOrderByUserid")
public class FindOrderByUserid extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        OrderService os = new OrderService();
        List<Order> orders = os.findOrdersByUserId(user.getId());

        request.setAttribute("orders", orders);
        request.setAttribute("orderCount", orders.size());
        request.getRequestDispatcher("/orderlist.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
