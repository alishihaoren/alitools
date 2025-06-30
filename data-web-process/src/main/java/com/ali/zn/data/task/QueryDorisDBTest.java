package com.ali.zn.data.task;

import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryDorisDBTest {
    public static void main(String[] args) {
        String driver = "com.mysql.jdbc.Driver";//驱动名称
        //String url = "jdbc:mysql://172.36.22.69:3306/rtdms?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";//数据库地址
//        String url = "jdbc:mysql://10.162.201.40:8000/rtdms?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";//数据库地址
        String url = "jdbc:mysql://10.162.201.135:9030/zn_coalfired?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&useCompression=true";//数据库地址
        String user = "root";//数据库帐号
        String password = "Syl@7758521";//数据库密码
        List<String> dataList = new ArrayList<String>(Arrays.asList(
                "2261.ERTU_089",
                "2261.DPU3046.SH0407.AALM028.PV",
                "2261.DPU3046.SH0401.AALM012.PV",
                "2261.DPU3046.SH0407.AALM027.PV",
                "2261.DPU3046.SH0406.AALM013.PV",
                "2261.DPU3046.SH0401.AALM013.PV",
                "2261.DPU3046.SH0402.AALM027.PV",
                "2261.DPU4046.SH0401.AALM005.PV",
                "2261.DPU4046.SH0406.AALM005.PV",
                "2261.DPU4046.SH0401.AALM013.PV",
                "2261.DPU4046.SH0407.AALM028.PV",
                "2261.DPU4046.SH0406.AALM013.PV",
                "2261.DPU4046.SH0402.AALM027.PV",
                "2261.DPU4046.SH0401.AALM012.PV",
                "2261.DPU4046.SH0407.AALM027.PV",
                "2261.DPU4046.SH0401.AALM011.PV",
                "2261.DPU4046.SH0406.AALM011.PV",
                "2261.DPU4046.SH0401.AALM010.PV",
                "2261.DPU4046.SH0406.AALM010.PV",
                "2261.DPU4046.SH0406.AALM012.PV",
                "2261.20AGC01XB11SEL_AO21",
                "2261.DPU3046.SH0401.AALM006.PV",
                "2261.DPU3046.SH0406.AALM007.PV",
                "2261.DPU3046.SH0401.AALM008.PV",
                "2261.DPU3046.SH0406.AALM008.PV",
                "2261.DPU3046.SH0406.AALM006.PV",
                "2261.DPU3046.SH0401.AALM007.PV",
                "5151.W11MDY60CS005XQ22",
                "5151.W11MDY61CS005XQ22",
                "2261.DPU3046.SH0406.AALM005.PV",
                "2261.DPU3046.SH0401.AALM005.PV",
                "2261.DPU3046.SH0406.AALM012.PV",
                "2261.DPU3046.SH0401.AALM011.PV",
                "2261.DPU3046.SH0401.AALM010.PV",
                "2261.DPU3046.SH0406.AALM010.PV",
                "2261.DPU3046.SH0406.AALM011.PV",
                "2261.10AGC01XB11SEL_AO21",
                "2261.20BAC01GS001_DO4",
                "2261.10BAC01GS001_DO4",
                "2261.U2_FD13064AO4",
                "2261.FD13064AO4",
                "2261.U2_FD13055AO5",
                "2261.FD13055AO5",
                "2261.U2_FD13055AO2",
                "2261.10HSK51CF101A",
                "2261.10HSK52CF101A",
                "2261.20HSK52CF101A",
                "2261.20HSK51CF101A",
                "2261.ERTU_093"
        ));

//        String user = "rtdms";//数据库帐号
//        String password = "rtdms@q23";//数据库密码
        Connection con;
        Statement stmt;
        try {
            Class.forName(driver);//加载驱动程序
            con = DriverManager.getConnection(url, user, password);
            stmt=con.createStatement();
            String sqlFormat="select * from  st2261 where tag_name ='%s'";
            for(String  tagName:dataList){
                Long startTime = System.currentTimeMillis();
              ResultSet rs=   stmt.executeQuery(String.format(sqlFormat,tagName));
              int columnSizes=rs.getMetaData().getColumnCount();
                ResultSetMetaData  resultSetMetaData=rs.getMetaData();
                List<String>  list=new ArrayList<>();
              while (rs.next()){
                  JSONObject jo=new JSONObject();
                 for(int i=1;i<=columnSizes;i++){
                     jo.put(resultSetMetaData.getColumnName(i),rs.getObject(i));
                 }
                  list.add(jo.toString());
              }

                System.out.println("tagName "  + tagName+"  size:"+list.size()+"  cost:"+(System.currentTimeMillis()-startTime));
            }
            stmt.execute("DROP TABLE IF EXISTS zn_coalfired");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }


}
