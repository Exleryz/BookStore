package com.yezhou.bookStore.web.filter;

import com.yezhou.bookStore.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RoleFilter implements Filter {    // servlet访问不会被过滤 只能过滤url级别
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 强转
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setContentType("text/html;charset=utf-8");

        // 处理业务
        // 从session中把用户对象得到
        User user = (User) request.getSession().getAttribute("user");
        // 判断当前用户权限
        if (user != null) {
            if (!"admin".equals(user.getRole())) {
                response.getWriter().print("无法访问");
                response.setHeader("refresh", "2;url=" + request.getContextPath() + "/index.jsp");
                return;
            }
            // 放行
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
