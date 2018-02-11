package com.yezhou.bookStore.web.servlet;

import AnLi.Service.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DelAllBoolsServlet", value = "/delAllBoolsServlet")
public class DelAllBoolsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 得到所有的id
        String[] ids = request.getParameterValues("ids");
        // 调用删除业务
        BookServiceImpl bs = new BookServiceImpl();
        bs.delAllBooks(ids);

        request.getRequestDispatcher("bookListServlet").forward(request, response);
    }
}
