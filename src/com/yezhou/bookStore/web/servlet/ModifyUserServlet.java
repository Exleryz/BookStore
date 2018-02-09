package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.domain.User;
import com.yezhou.bookStore.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet(name = "ModifyUserServlet", value = "/modifyUserServlet")
public class ModifyUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 封装表单数据
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());

            UserService us = new UserService();
            us.modifyUser(user);

            request.getSession().invalidate();    // 相当于注销    密码修改成功
            response.sendRedirect(request.getContextPath() + "/modifyUserInfoSuccess.jsp");
        } catch (Exception e) {
            response.getWriter().write(e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
