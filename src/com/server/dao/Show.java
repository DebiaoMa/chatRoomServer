package com.server.dao;

import com.server.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Show {

    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = JDBCUtils.getConnection();

            String sql = "update chat set cname = 'gyyy' where id = 1";  //定义sql
            stmt = conn.createStatement();//获取执行sql的对象

            int count = stmt.executeUpdate(sql);  //执行sql
            System.out.println(count);     //打印处理结果

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(stmt, conn);
        }
    }
}
