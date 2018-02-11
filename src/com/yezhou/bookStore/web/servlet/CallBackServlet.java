package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.exception.OrderException;
import com.yezhou.bookStore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CallBackServlet", value = "/callBackServlet")
public class CallBackServlet extends HttpServlet {   // 第三方支付来访问此Servlet 返回的数据是给第三方看

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderid = request.getParameter("orderid");
        String money = request.getParameter("money");

        // 第三放支付控件回调 数据接收
        // ....
        boolean isSuccess = true;    // 支付成功

        if (!isSuccess) {
            response.getWriter().print("支付数据有误，联系客服");
        } else {
            // 判断第三方返回的支付状态
            if (true) {
            // 修改订单状态
                response.getWriter().print("success");

            }
            OrderService os = new OrderService();
            try {
                os.midifyOrderState(orderid);
            } catch (OrderException e) {
                System.out.println(e.getMessage());
            }
        }
        response.sendRedirect(request.getContextPath() + "/paysuccess.jsp");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
