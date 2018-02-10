package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.domain.Product;
import com.yezhou.bookStore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FindBookInfoServlet", value = "/findBookInfoServlet")
public class FindBookInfoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        ProductService ps = new ProductService();
        Product product = ps.findBookById(id);

        request.setAttribute("b", product);
        request.getRequestDispatcher("/product_info.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
