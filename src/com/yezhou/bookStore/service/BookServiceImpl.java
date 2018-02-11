package com.yezhou.bookStore.service;



import com.yezhou.bookStore.dao.BookDaoImpl;
import com.yezhou.bookStore.domain.Book;
import com.yezhou.bookStore.domain.PageBean;

import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl {

    // 创建一个dao对象
    BookDaoImpl bookDao = new BookDaoImpl();

    public List<Book> findAllBook() {
        try {
            return bookDao.findAllBooks();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 添加图书
    public void addBook(Book book) {
        try {
            bookDao.addBook(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Book findBookById(String id) {
        try {
            return bookDao.findBookById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateBook(Book book) {
        try {
            bookDao.updateBook(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(String id) {
        try {
            bookDao.delBook(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delAllBooks(String[] ids) {
        try {
            bookDao.delAllBooks(ids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> searchBooks(String id, String category, String name, String minprice, String maxprice) {
        try {
            return bookDao.searchBooks(id, category, name, minprice, maxprice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param name
     * @return
     */
    public List<Object> serachBookBName(String name) {
        try {
            return bookDao.seachBookByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
