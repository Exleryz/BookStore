package com.yezhou.bookStore.web.servlet;



import com.yezhou.bookStore.domain.Book;
import com.yezhou.bookStore.service.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FindBookByIdServlet", value = "/findBookByIdServlet")
public class FindBookByIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        BookServiceImpl bs = new BookServiceImpl();
        Book book = bs.findBookById(id);

        request.setAttribute("book", book);
        request.getRequestDispatcher("/admin/products/edit.jsp").forward(request, response);
    }
}
