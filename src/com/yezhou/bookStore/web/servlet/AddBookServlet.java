package com.yezhou.bookStore.web.servlet;

import AnLi.Service.BookServiceImpl;
import AnLi.domain.Book;
import AnLi.util.UUIDUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AddBookServlet", value = "/addBookServlet")
public class AddBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        List<FileItem> fileItems = new ArrayList<FileItem>(0);
        sfu.setHeaderEncoding("UTF-8");    // 上传文件的乱码
        Map<String, String[]> map = new HashMap<String, String[]>();
        try {
            fileItems = sfu.parseRequest(request);
            // 迭代
            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {
                    // 普通表单项
                    String name = fileItem.getFieldName();    // 得到字段的名
                    String value = fileItem.getString("UTF-8");    // 得到字段的值
                    map.put(name, new String[]{value});    // 向map中赋值



                } else {
                    // 文件表单项
                    InputStream inputStream = fileItem.getInputStream();
                    String filename = fileItem.getName();    // 得到上传的名
                    String extension = FilenameUtils.getExtension(filename);
                    if (!extension.equals("jsp")) {
                        // 创建目录
                        File storeDirectory = new File(this.getServletContext().getRealPath("/upload"));
                        if (!storeDirectory.exists()) {
                            storeDirectory.mkdirs();
                        }

                        // 处理文件名
                        if (fileItem != null) {
                            filename = FilenameUtils.getName(filename);
//                            System.out.println(filename);
                        }
                        // 目录打撒
                        String childDirectory = makeChildDirectory(storeDirectory, filename);
                        fileItem.write(new File(storeDirectory, childDirectory + File.separator + filename));
                        fileItem.delete();    // 删除缓存文件
                    }
                    map.put(fileItem.getFieldName(), new String[] {filename});    // 将图片表单项的name和value保存到map中

                }
            }
            Book book = new Book();
            BeanUtils.populate(book, map);
            book.setId(UUIDUtil.getUUID());    // 图书编号

            BookServiceImpl bs = new BookServiceImpl();
            bs.addBook(book);

            request.getRequestDispatcher("bookListServlet").forward(request, response);
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



        /*request.setCharacterEncoding("UTF-8");
        // 获取表单数据
        Book book = new Book();
        try {
//            System.out.println(request.getParameter("name"));
//            System.out.println(request.getParameter("price"));
//            System.out.println(request.getParameter("pnum"));
//            System.out.println(request.getParameter("category"));
//            System.out.println(request.getParameter("description"));
            BeanUtils.populate(book, request.getParameterMap());
            book.setId(UUIDUtil.getUUID());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 调用业务逻辑
        BookServiceImpl bs = new BookServiceImpl();
        bs.addBook(book);

        // 分发转向
        request.getRequestDispatcher("bookListServlet").forward(request, response);
        */
    }

    private String makeChildDirectory(File storeDirectory, String filename) {
        int hashcode = filename.hashCode();    // 返回字符串转换的32位hashcode码
        System.out.println(hashcode);
        String code = Integer.toHexString(hashcode);    // 把hashcode转换为16进制的字符
        System.out.println(code);
        String childDirectory = code.charAt(0) + File.separator + code.charAt(1);

        // 创建指定目录
        File file = new File(storeDirectory, childDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
        return childDirectory;
    }
}
