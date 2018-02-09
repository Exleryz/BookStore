package com.yezhou.bookStore.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class C3P0Util {

    // 得到一个数据源
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();

    public static ComboPooledDataSource getDataSource() {
        return cpds;
    }

    // 从数据源中得到一个连接对象
    public static Connection getConnection() {
        try {
            return cpds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("系统错误");
        }
    }

    public static void release(Connection connection, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stmt = null;
        }
        if (connection != null) {
            try {
                connection.close();    // 关闭
            } catch (Exception e) {
                e.printStackTrace();
            }
            connection = null;
        }
    }
}
