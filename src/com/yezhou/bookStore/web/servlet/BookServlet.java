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

@WebServlet(name = "BookServlet", value = "/bookListServlet")
public class BookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //调用业务逻辑
        BookServiceImpl bsi = new BookServiceImpl();
        List<Book> books = bsi.findAllBook();
        if (books != null) {    // 跳转页面
            request.setAttribute("books", books);    // 把List放入到request对象中
            request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
        }
    }
}
