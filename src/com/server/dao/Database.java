package com.server.dao;

import com.server.utils.JDBCUtils;

import java.sql.*;

public class Database {

//    public static void main(String[] args) throws Exception {
//
//        if (new Database().login("1", "123456")) {
//            System.out.println("io");
//        } else {
//            System.out.println("no");
//        }
//    }


    public boolean login(String cname, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (cname != null && password != null) {
            try {
                conn = JDBCUtils.getConnection();
                String sql = "select * from chat where cname = ? and password = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, cname);
                pstmt.setString(2, password);
                rs = pstmt.executeQuery();

                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCUtils.close(rs, pstmt, conn);
            }

            return false;
        } else {
            return false;
        }
    }

}
