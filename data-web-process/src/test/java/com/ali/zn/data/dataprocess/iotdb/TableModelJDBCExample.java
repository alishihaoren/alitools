package com.ali.zn.data.dataprocess.iotdb;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.iotdb.jdbc.IoTDBSQLException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class TableModelJDBCExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableModelJDBCExample.class);


    @Test
    public void insertBatchData() throws IOException, IoTDBSQLException {


        // don't specify database in url
        try {
            Class.forName("org.apache.iotdb.jdbc.IoTDBDriver");
            Connection connection =
                    DriverManager.getConnection(
                            "jdbc:iotdb://10.162.201.136:6667/wind_db?sql_dialect=table", "root", "root");
            Statement statement = connection.createStatement();

//            statement.execute("CREATE DATABASE test1");
//            statement.execute("CREATE DATABASE test2");
//
//            statement.execute("use test2");

            // or use full qualified table name
            Integer num=20000;
            while (num>0) {
                StringBuilder sql = new StringBuilder("insert into  st5151(time,tag_name,tag_value,is_good) values");
                for (int i = 0; i < 10000; i++) {
                    sql.append(" (now(),'AAAAAA" + i + "',1.22,false),");
                }
                sql.delete(sql.lastIndexOf(","), sql.length());
                statement.execute(sql.toString());
//                Thread.sleep(1000L);
                num--;
            }

            statement.close();

            connection.close();

//            statement.execute(
//                    "create table table2(region_id STRING TAG, plant_id STRING TAG, color STRING ATTRIBUTE, temperature FLOAT FIELD, speed DOUBLE FIELD) with (TTL=6600000)");

            // show tables from current database
//            try (ResultSet resultSet = statement.executeQuery("select * from st5151 order by time desc")) {
//                ResultSetMetaData metaData = resultSet.getMetaData();
//                System.out.println(metaData.getColumnCount());
//                while (resultSet.next()) {
//                    System.out.println(resultSet.getString(1) + ", " + resultSet.getInt(2));
//                }
//            }

            // show tables by specifying another database
            // using SHOW tables FROM
//            try (ResultSet resultSet = statement.executeQuery("SHOW TABLES FROM test1")) {
//                ResultSetMetaData metaData = resultSet.getMetaData();
//                System.out.println(metaData.getColumnCount());
//                while (resultSet.next()) {
//                    System.out.println(resultSet.getString(1) + ", " + resultSet.getInt(2));
//                }
//            }

        } catch (IoTDBSQLException e) {
            LOGGER.error("IoTDB Jdbc example error", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.iotdb.jdbc.IoTDBDriver");

        // don't specify database in url
        try (Connection connection =
                     DriverManager.getConnection(
                             "jdbc:iotdb://10.162.201.136:6667/wind_db?sql_dialect=table", "root", "root");
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE DATABASE test1");
            statement.execute("CREATE DATABASE test2");

            statement.execute("use test2");

            // or use full qualified table name
            statement.execute(
                    "create table test1.table1(region_id STRING TAG, plant_id STRING TAG, device_id STRING TAG, model STRING ATTRIBUTE, temperature FLOAT FIELD, humidity DOUBLE FIELD) with (TTL=3600000)");

            statement.execute(
                    "create table table2(region_id STRING TAG, plant_id STRING TAG, color STRING ATTRIBUTE, temperature FLOAT FIELD, speed DOUBLE FIELD) with (TTL=6600000)");

            // show tables from current database
            try (ResultSet resultSet = statement.executeQuery("SHOW TABLES")) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                System.out.println(metaData.getColumnCount());
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + ", " + resultSet.getInt(2));
                }
            }

            // show tables by specifying another database
            // using SHOW tables FROM
            try (ResultSet resultSet = statement.executeQuery("SHOW TABLES FROM test1")) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                System.out.println(metaData.getColumnCount());
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + ", " + resultSet.getInt(2));
                }
            }

        } catch (IoTDBSQLException e) {
            LOGGER.error("IoTDB Jdbc example error", e);
        }

        // specify database in url
        try (Connection connection =
                     DriverManager.getConnection(
                             "jdbc:iotdb://127.0.0.1:6667/test1?sql_dialect=table", "root", "root");
             Statement statement = connection.createStatement()) {
            // show tables from current database test1
            try (ResultSet resultSet = statement.executeQuery("SHOW TABLES")) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                System.out.println(metaData.getColumnCount());
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + ", " + resultSet.getInt(2));
                }
            }

            // change database to test2
            statement.execute("use test2");

            try (ResultSet resultSet = statement.executeQuery("SHOW TABLES")) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                System.out.println(metaData.getColumnCount());
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + ", " + resultSet.getInt(2));
                }
            }
        }
    }
}