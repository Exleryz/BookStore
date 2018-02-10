package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ChangeNumServlet", value = "/changeNumServlet")
public class ChangeNumServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String num = request.getParameter("num");

        Product b = new Product();
        b.setId(id);

        HttpSession session = request.getSession();
        Map<Product, String> cart = (Map<Product, String>) session.getAttribute("cart");
        if ("0".equals(num)) {
            cart.remove(b);
        }
        if (cart.containsKey(b)) {
            cart.put(b, num);
        }
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
