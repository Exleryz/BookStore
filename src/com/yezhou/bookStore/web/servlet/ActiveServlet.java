package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.exception.UserException;
import com.yezhou.bookStore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ActiveServlet", value = "/activeServlet")
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得激活码
        String activeCode = request.getParameter("activeCode");

        UserService us = new UserService();
        try {
            us.activeUser(activeCode);    // 若用户已经激活。。。 没有激活。。。。
            response.getWriter().print("激活成功");
        } catch (UserException e) {
            e.printStackTrace();
            // 向用户提示失败信息
            response.getWriter().write(e.getMessage());
        }

    }
}
