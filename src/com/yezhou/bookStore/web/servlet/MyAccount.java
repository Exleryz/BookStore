package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MyAccount", value = "/myAccount")
public class MyAccount extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从session中取出user对象
        User user = (User) request.getSession().getAttribute("user");
        // 判断user是否为null
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            String path = "/myAccount.jsp";
            if ("admin".equals(user.getRole())) {
                // 管理员
                path = "/admin/login/home.jsp";
            }
            request.getRequestDispatcher(path).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
