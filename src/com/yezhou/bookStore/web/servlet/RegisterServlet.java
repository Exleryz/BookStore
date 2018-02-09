package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.domain.User;
import com.yezhou.bookStore.exception.UserException;
import com.yezhou.bookStore.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理验证码 checkcode_session
        String ckcode = (String) request.getParameter("ckcode");    // 获取用户输入的验证码
        System.out.println(ckcode);
        String code = (String) request.getSession().getAttribute("checkcode_session");    // 验证码控件保存在session中的正确答案
        System.out.println(code);
        if (!ckcode.equals(code)) {
            request.setAttribute("ckcode_msg", "验证码错误");// 打印错误信息
            System.out.println("asdgasdg");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        // 获取表单数据
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
            user.setActiveCode(UUID.randomUUID().toString());    // 手动设置激活码
            // 调用业务逻辑
            UserService us = new UserService();
            us.regist(user);
            // 分发转向
//            要求用户激活后才能登录，所以不能把用户信息保存session中
//            request.setAttribute("user", user);    // 把用户信息封装到session中
            request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);
        } catch (UserException e) {
            request.setAttribute("user_msg", e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
