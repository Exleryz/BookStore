package com.yezhou.bookStore.web.servlet;

import com.yezhou.bookStore.domain.PageBean;
import com.yezhou.bookStore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PageServlet", value = "/pageServlet")
public class PageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String category = request.getParameter("category");//从上一页或下一页得到的数据

//        System.out.println(category);
        if (category == null) {
            category = "";
        }

        int pagesize = 4;    // 初始化每页显示记录数
        int currentPage = 1;    // 当前页
        String currPage = request.getParameter("currentPage");
        if (currPage != null && !"".equals(currPage)) {    // 第一次访问资源时，currPage可能是null
            currentPage = Integer.parseInt(currPage);
        }

        ProductService ps = new ProductService();
        //分页查询，并返回PageBean对象
        PageBean pb = ps.findBooksPage(currentPage, pagesize, category);
        request.setAttribute("pb", pb);
        request.getRequestDispatcher("/product_list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
