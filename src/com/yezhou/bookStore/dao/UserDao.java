package com.yezhou.bookStore.dao;

import com.yezhou.bookStore.domain.User;
import com.yezhou.bookStore.util.C3P0Util;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;

public class UserDao {

    /**
     * 注册  添加用户
     *
     * @param user
     * @throws SQLException
     */
    public void addUser(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        String sql = "INSERT INTO user(username, PASSWORD, gender, email, telephone, introduce, activeCode, state, registTime)" +
                " VALUES (?,?,?,?,?,?,?,?,?)";
        qr.update(sql, user.getUsername(), user.getPassword(),
                user.getGender(), user.getEmail(), user.getTelephone(),
                user.getIntroduce(), user.getActiveCode(), user.getState(),
                user.getRegistTime());

    }

    /**
     * 激活 激活码查找用户
     *
     * @param activeCode
     * @return
     * @throws SQLException
     */
    public User findUserByActiveCode(String activeCode) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        return qr.query("select * from user where activecode=?", new BeanHandler<User>(User.class), activeCode);
    }

    /**
     * 修改用户状态
     *
     * @param activeCode
     * @throws SQLException
     */
    public void activeCode(String activeCode) throws SQLException {    // 使用用户编号是否会更好
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        qr.update("update user set state=1 where activecode=?", activeCode);
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public User findUserByUserNameAndPassword(String username, String password) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        return qr.query("select * from user where username=? and password=?", new BeanHandler<User>(User.class), username, password);
    }

    /**
     * 根据id查找用户
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public User findUserById(String id) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        return qr.query("select * from user where id=?", new BeanHandler<User>(User.class), id);
    }

    /**
     * 修改用户信息
     *
     * @param user
     */
    public void modifyUser(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
        qr.update("update user set password=?, gender=?, telephone=? where id =?",
                user.getPassword(), user.getGender(), user.getTelephone(), user.getId());
    }
}
