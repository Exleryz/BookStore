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

@WebServlet(name = "LoginServlet", value = "/loginServlet")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserService us = new UserService();
        try {
            String path = "/index.jsp";
            User user = us.login(username, password);
            if (user.getRole().equals("admin")) {    // 权限控制
                path = "/admin/login/home.jsp";
            }
            request.getSession().setAttribute("user", user);
            request.getRequestDispatcher(path).forward(request, response);
        } catch (UserException e) {
            e.printStackTrace();
            request.setAttribute("user_msg", e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
