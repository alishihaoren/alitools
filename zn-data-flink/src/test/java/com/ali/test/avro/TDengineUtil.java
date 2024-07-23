package com.ali.test.avro;

import com.alibaba.fastjson.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TDengineUtil {


    public static  List<JSONObject> getResultSet2JSON(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }
        List<JSONObject> list = new ArrayList<>();
        try {
            Integer columnNum = rs.getMetaData().getColumnCount();
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                for (int i = 0; i < columnNum; i++) {
                    jo.put(rsmd.getColumnName(i + 1), rs.getString(i + 1));
                }
                list.add(jo);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            rs.close();
        }
        return list;
    }




}
