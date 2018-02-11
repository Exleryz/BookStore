package com.yezhou.bookStore.web.servlet;

import AnLi.Service.BookServiceImpl;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchBookAJAXServlet", value = "/searchBookAJAXServlet")
public class SearchBookAJAXServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");    // 只能解决post方式的乱码
        String name = request.getParameter("name");

        BookServiceImpl bs = new BookServiceImpl();
        List<Object> list = bs.serachBookBName(name);

        // 把集合中的数据转换为字符串返回到网页
//        String str = "[";
//        for (int i = 0; i < list.size(); i++) {
//            if (i > 0) {
//                str += ",";
//            }
//            str += "\""+ list.get(i) +"\"";    // 转义
//        }
//        str += "]";
        String str = JSONArray.fromObject(list).toString();
        // 把集合中的数据转换为字符串返回到网页
        response.getWriter().print(str);    // str ["1","2","3"]
        System.out.println(str);
//        request.setAttribute("str", str);
//        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
