package com.yezhou.bookStore.service;

import com.yezhou.bookStore.dao.UserDao;
import com.yezhou.bookStore.domain.User;
import com.yezhou.bookStore.exception.UserException;
import com.yezhou.bookStore.util.SendJMail;


import java.sql.SQLException;

public class UserService {
    UserDao ud = new UserDao();

    public void regist(User user) throws UserException {
        try {
            ud.addUser(user);

            String emailMsg = "注册成功，请<a href='http://localhost:8080/activeServlet?activeCode=" + user.getActiveCode() + "'>激活</a>登录";
            SendJMail.sendMail(user.getEmail(), emailMsg);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("注册失败");
        }
    }

    public void activeUser(String activeCode) throws UserException {

        // 根据激活码查找用户
        try {
            User user = ud.findUserByActiveCode(activeCode);
            if (user != null) {
                // 激活用户
                ud.activeCode(activeCode);
                return;
            }
            throw new UserException("激活失败");    // 用户不存在
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("激活失败");
        }
    }

    public User login(String username, String password) throws UserException {
        User user = null;
        try {
            user = ud.findUserByUserNameAndPassword(username, password);
            if (user == null) {
                throw new UserException("用户名或密码错误");
            }
            if (user.getState() == 0) {
                throw new UserException("用户未激活");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("用户名或密码错误");
        }
        return user;
    }

    public User findUserById(String id) throws UserException {
        try {
            return ud.findUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("用户查找失败");
        }

    }

    public void modifyUser(User user) throws UserException {
        try {
            ud.modifyUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("修改失败");
        }
    }
}
