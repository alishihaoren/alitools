package com.ali.zn.data.dataprocess;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryDorisDBTest {

@Test
    public void testQueryDorisDB() {
        String driver = "com.mysql.jdbc.Driver";//驱动名称
        //String url = "jdbc:mysql://172.36.22.69:3306/rtdms?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";//数据库地址
//        String url = "jdbc:mysql://10.162.201.40:8000/rtdms?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";//数据库地址
        String url = "jdbc:mysql://10.162.200.33:9030/zn_coalfired?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";//数据库地址
        String user = "root";//数据库帐号
        String password = "znsk@123";//数据库密码


//        String user = "rtdms";//数据库帐号
//        String password = "rtdms@q23";//数据库密码
        Connection con;
        Statement stmt;
        try {
            Class.forName(driver);//加载驱动程序
            con = DriverManager.getConnection(url, user, password);
            stmt=con.createStatement();
            String sqlFormat="select * from  st2261 where tag_name ='%s'";

                Long startTime = System.currentTimeMillis();
              ResultSet rs=   stmt.executeQuery("SELECT *  FROM s3(     'uri' = 's3://bigdata/datapress/zn_coalfired/st2261/*/*.parquet',     'format' = 'parquet',     's3.endpoint' = 'http://10.162.200.47:9000',     's3.region' = 'us-east-1',     's3.access_key' = 'sunyunli',     's3.secret_key'='123456!aA' )  limit 10 ");
              int columnSizes=rs.getMetaData().getColumnCount();
                ResultSetMetaData  resultSetMetaData=rs.getMetaData();
                List<String>  list=new ArrayList<>();
              while (rs.next()){
                  JSONObject jo=new JSONObject();
                 for(int i=1;i<=columnSizes;i++){
                     jo.put(resultSetMetaData.getColumnName(i),rs.getObject(i));
                 }
                  System.out.println(jo.toJSONString());
                  list.add(jo.toString());
              }

                System.out.println("tagName " +"  size:"+list.size()+"  cost:"+(System.currentTimeMillis()-startTime));

            stmt.execute("DROP TABLE IF EXISTS zn_coalfired");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }


}
