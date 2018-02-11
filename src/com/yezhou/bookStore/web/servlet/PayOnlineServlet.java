package com.yezhou.bookStore.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PayOnlineServlet", value = "/payOnlineServlet")
public class PayOnlineServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户提交的数据
        String money = request.getParameter("money");
        String orderid = request.getParameter("orderid");    // 金额订单编号

        // 封装第三方支付控件所需数据
        String yh = request.getParameter("yh");    // 用户支付银行
        // ...


        request.setAttribute("p2_Order", orderid);
        request.setAttribute("p3_Amt", money);
        request.getRequestDispatcher("/confirm.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
