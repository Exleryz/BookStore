package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.domain.Product;
import com.yezhou.bookStore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddCartServlet", value = "/addCartServlet")
public class AddCartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        ProductService ps = new ProductService();
        Product b = ps.findBookById(id);

        HttpSession session = request.getSession();
        Map<Product, String> cart = (Map<Product, String>) session.getAttribute("cart");
        int num = 1;
        //如果是第一次访问，没有购物车对象，就创建 一个购物车对象
        if (cart == null) {
            cart = new HashMap<Product, String>();
        }
        //查看当前集合中是否存在b这本书,如果有就把数据取出来加1;
        if (cart.containsKey(b)) {
            num = Integer.parseInt(cart.get(b)) + 1;
        }
        cart.put(b, num + "");
        session.setAttribute("cart", cart);
        response.getWriter().print("<a href='"+request.getContextPath()+"/pageServlet'>继续购物</a>，<a href='"+request.getContextPath()+"/cart.jsp'>查看购物车</a>");




    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
