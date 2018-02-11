package com.yezhou.bookStore.web.servlet;

import AnLi.Service.BookServiceImpl;
import AnLi.domain.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchBooksServlet", value = "/searchBooksServlet")
public class SearchBooksServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // 获取表单数据
        String id = request.getParameter("id");
        String category = request.getParameter("category");
        String name = request.getParameter("name");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");

        BookServiceImpl bs = new BookServiceImpl();
        List<Book> books = bs.searchBooks(id, category, name, minprice, maxprice);
        request.setAttribute("books", books);
        request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
    }
}
