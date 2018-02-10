package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.domain.User;
import com.yezhou.bookStore.exception.UserException;
import com.yezhou.bookStore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FindUserById", value = "/findUserById")
public class FindUserById extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        UserService us = new UserService();

        try {
            User user = us.findUserById(id);
            request.setAttribute("u", user);
            request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request, response);
        } catch (UserException e) {
//            response.getWriter().write(e.getMessage());
            response.sendRedirect(request.getContextPath()+ "/login.jsp");
        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
