package com.ali.zn.data.dataprocess.duckdb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;
import java.sql.DriverManager;
import org.duckdb.DuckDBConnection;


public class DuckDBParquetParser {

    /**
     * 测试创建库  写入数据与落地
     *  数据库测试
     */

    @Test
    public void checkDuckDBInsertParquet() {
        try {
            // 1. 连接到 DuckDB（内存数据库）
//            Connection conn = DriverManager.getConnection("jdbc:duckdb:F:\\data\\duckDB/test.db");

            Class.forName("org.duckdb.DuckDBDriver");

            Properties connectionProperties = new Properties();
            connectionProperties.setProperty("temp_directory", "F:/duckdb/temp/");
            Connection conn = DriverManager.getConnection("jdbc:duckdb:F:\\data\\duckDB/testwal.db", connectionProperties);

//            String createSql = "create table zn_wind_stable(time datetime(3),tag_name varchar(128),tag_value FLOAT )";
            Statement stmt = conn.createStatement();

            Long startTime = System.currentTimeMillis();
            // 4. 执行查询
            ResultSet rs = stmt.executeQuery("select  tag_name ,count(*) as nums  from zn_wind_stable group by tag_name order by nums  desc limit 10");
            ResultSetMetaData rsm= rs.getMetaData();
            Integer  colSize= rsm.getColumnCount();
            // 5. 处理结果集
            while (rs.next()) {
                // 获取列数据（根据实际列名/类型修改）
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                System.out.println("ID: " + id + ", Name: " + name);
                JSONObject jo=new JSONObject();
                for (int i = 1; i <= colSize; i++) {
                    jo.put(rsm.getColumnName(i), rs.getObject(i));
                }

                System.out.println(jo.toJSONString());
            }
            Long endTime = System.currentTimeMillis();
            System.out.println(LocalDateTime.now()+"    cost:: "+(endTime-startTime)+"ms");

//            for(int i=0;i<1000;i++) {
//                LocalDateTime  now = LocalDateTime.now();
//                String insertSql = "insert into  zn_wind_stable (time,tag_name,tag_value) values('"+now+"','AAAAAA',1.234), " +
//                        " ('2025-01-02 11:11:11','AA',1.234) , ('2025-01-04 11:11:11','AA',1.234) , ('2025-01-06 11:11:11','AAAAAA',1.234)  " +
//                        ", ('2025-01-03 11:11:11','BB',1.234) , ('2025-01-05 11:11:11','BB',1.234) , ('2025-01-07 11:11:11','AAAAAA',1.234)  ";
//                // 2. 创建 Statement
//
////            stmt.executeUpdate(createSql);
//                stmt.execute(insertSql);
//            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 写入DuckDB内存
     *  数据库测试
     */

    @Test
    public void checkDuckDBWriteMenoryWriteParquet() {
        try {
            // 1. 连接到 DuckDB（内存数据库）
            Connection conn = DriverManager.getConnection("jdbc:duckdb:F:\\data\\duckDB/testwal.db");
//            String createSql = "create table zn_wind_stable(time datetime(3),tag_name varchar(128),tag_value FLOAT )";

            Statement stmt = conn.createStatement();
//                 stmt.execute(createSql);
            Random rand = new Random();
            for(int i=0;i<1000;i++) {

                StringBuilder  sqlSB=new StringBuilder("insert into  zn_wind_stable (time,tag_name,tag_value) values ");

              String  sqlFormat=new String("('%s','%s',%s),");
              LocalDateTime  datanow = LocalDateTime.now();
                for(int j=0;j<10000;j++) {

                    sqlSB.append(String.format(sqlFormat,datanow,"5151.SKKW."+j,rand.nextDouble()*10000/100));
                }
                Long  now = System.currentTimeMillis();
                // 2. 创建 Statement
                sqlSB.delete(sqlSB.lastIndexOf(","),sqlSB.length());
//            stmt.executeUpdate(createSql);
                stmt.execute(sqlSB.toString());
                System.out.println(LocalDateTime.now()+"  --- cost ::"+(System.currentTimeMillis()-now));
            }

            stmt.execute("COPY zn_wind_stable" +
                    "    TO 'F:\\data\\datapress/result-zstd-bk.parquet'\n" +
                    "    (FORMAT parquet, COMPRESSION zstd);");
            Long startTime = System.currentTimeMillis();
            // 4. 执行查询
            ResultSet rs = stmt.executeQuery("select  tag_name ,count(*) as nums  from zn_wind_stable group by tag_name order by nums  desc limit 10");
            ResultSetMetaData rsm= rs.getMetaData();
            Integer  colSize= rsm.getColumnCount();
            // 5. 处理结果集
            while (rs.next()) {
                // 获取列数据（根据实际列名/类型修改）
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                System.out.println("ID: " + id + ", Name: " + name);
                JSONObject jo=new JSONObject();
                for (int i = 1; i <= colSize; i++) {
                    jo.put(rsm.getColumnName(i), rs.getObject(i));
                }

                System.out.println(jo.toJSONString());
            }
            Long endTime = System.currentTimeMillis();
            System.out.println(LocalDateTime.now()+"    cost:: "+(endTime-startTime)+"ms");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkDuckDBDataInParquet() {
        try {
            // 1. 连接到 DuckDB（内存数据库）
            Connection conn = DriverManager.getConnection("jdbc:duckdb:F:\\data\\duckDB/test.db");
//            String createSql = "create table zn_wind_stable(time datetime(3),tag_name varchar(128),tag_value FLOAT )";
            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("select  * from zn_wind_stable order by time  desc  limit 100");

            stmt.execute("COPY zn_wind_stable" +
                    " to 'F:\\data\\duckDB/test.parquet'" +
                    "    (FORMAT parquet, COMPRESSION zstd);");

//            ResultSetMetaData rsm= rs.getMetaData();
//            Integer  colSize= rsm.getColumnCount();
//            // 5. 处理结果集
//            while (rs.next()) {
//                // 获取列数据（根据实际列名/类型修改）
////                int id = rs.getInt("id");
////                String name = rs.getString("name");
////                System.out.println("ID: " + id + ", Name: " + name);
//                JSONObject jo=new JSONObject();
//                for (int i = 1; i <= colSize; i++) {
//                    jo.put(rsm.getColumnName(i), rs.getString(i));
//                }
//                System.out.println(jo.toJSONString());
//            }



            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        try {
            // 1. 连接到 DuckDB（内存数据库）
            Connection conn = DriverManager.getConnection("jdbc:duckdb:F:\\data\\duckDB/testwal.db");

            // 2. 创建 Statement
            Statement stmt = conn.createStatement();

            // 3. 直接查询 Parquet 文件
            String parquetPath = "F:\\data\\datapress\\zjny_center_tsdb_td\\zn_wind_stable\\20250427/*/*.gz.parquet"; // 替换为实际路径
            String sql = "CREATE VIEW  zn_wind_stable   AS SELECT * FROM read_parquet('" + parquetPath + "') where tag_name like '%XQ22'  ";
            stmt.executeUpdate(sql);
            stmt.execute("insert into  zn_wind_stable (time,tag_name,tag_value,is_good) values ('2025-01-01 11:11:11','alishihaoren123',1.234,false)");
            stmt.execute("COPY zn_wind_stable" +
                    "    TO 'F:\\data\\datapress\\zjny_center_tsdb_td\\zn_wind_stable\\20250427/result-zstd-bk.parquet'\n" +
                    "    (FORMAT parquet, COMPRESSION zstd);");
           Long startTime = System.currentTimeMillis();
            // 4. 执行查询
            ResultSet rs = stmt.executeQuery("select * from zn_wind_stable where tag_name='alishihaoren123' limit 10");
            ResultSetMetaData rsm= rs.getMetaData();
            Integer  colSize= rsm.getColumnCount();
            // 5. 处理结果集
            while (rs.next()) {
                // 获取列数据（根据实际列名/类型修改）
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                System.out.println("ID: " + id + ", Name: " + name);
                JSONObject jo=new JSONObject();
                for (int i = 1; i <= colSize; i++) {
                    jo.put(rsm.getColumnName(i), rs.getObject(i));
                }

                System.out.println(jo.toJSONString());
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("cost:: "+(endTime-startTime)+"ms");

            // 6. 关闭资源
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///  F:\data\datapress
    @Test
    public  void QueryDuckDB() {
        try {
            Class.forName("org.duckdb.DuckDBDriver");
            // 1. 连接到 DuckDB（内存数据库）
            Connection conn = DriverManager.getConnection("jdbc:duckdb:F:\\data\\duckDB/test.db");
            System.setProperty("duckdb.temp_directory", "F:\\data\\duckDBtmp/");
            // 2. 创建 Statement
            Statement stmt = conn.createStatement();

            // 3. 直接查询 Parquet 文件
            String parquetPath = "F:\\data\\datapress\\zn_coalfired\\st2261/20250430/*/*.parquet"; // 替换为实际路径
            String sql = "CREATE VIEW  zn_wind_stable1   AS SELECT * FROM read_parquet('" + parquetPath + "') where tag_name  in ('2261.20AGC01XB11SEL_AO21','2261.10AGC01XB11SEL_AO21')  ";
            stmt.executeUpdate(sql);
//            stmt.execute("insert into  zn_wind_stable (time,tag_name,tag_value,is_good) values ('2025-01-01 11:11:11','alishihaoren123',1.234,false)");
//            stmt.execute("COPY zn_wind_stable" +
//                    "    TO 'F:\\data\\datapress\\zjny_center_tsdb_td\\zn_wind_stable\\20250427/result-zstd-bk.parquet'\n" +
//                    "    (FORMAT parquet, COMPRESSION zstd);");
            Long startTime = System.currentTimeMillis();
            // 4. 执行查询
            ResultSet rs = stmt.executeQuery("select tag_name,count(*) as nums from zn_wind_stable1 group by tag_name order by nums desc   limit 10");
            ResultSetMetaData rsm= rs.getMetaData();
            Integer  colSize= rsm.getColumnCount();
            // 5. 处理结果集
            while (rs.next()) {
                // 获取列数据（根据实际列名/类型修改）
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                System.out.println("ID: " + id + ", Name: " + name);
                JSONObject jo=new JSONObject();
                for (int i = 1; i <= colSize; i++) {
                    jo.put(rsm.getColumnName(i), rs.getObject(i));
                }

                System.out.println(jo.toJSONString());
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("cost:: "+(endTime-startTime)+"ms");

            // 6. 关闭资源
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}