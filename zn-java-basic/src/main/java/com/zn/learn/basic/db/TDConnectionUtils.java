package com.zn.learn.basic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TDConnectionUtils {
    private final String url = "";

    static {

        try {
            Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
//            Class.forName("com.taosdata.jdbc.TSDBDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String jdbcUrl = "jdbc:TAOS-RS://10.162.201.62:6041/zjny_center_tsdb_td?user=root&password=taosdata&timezone=UTC-8&charset=UTF-8&locale=en_US.UTF-8";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


}
