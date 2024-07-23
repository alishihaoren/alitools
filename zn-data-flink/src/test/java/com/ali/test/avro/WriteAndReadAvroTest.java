package com.ali.test.avro;

import com.alibaba.fastjson.JSONObject;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipInputStream;

public class WriteAndReadAvroTest {

    private ThreadPoolExecutor executor;

    @Before
    public void getThreadPoolTaskExecutor() {
        executor = new ThreadPoolExecutor(
                5, // 核心线程数为 10
                10, // 最大线程数为 20
                0L, TimeUnit.MILLISECONDS, // keep-alive time 为 0 毫秒，即非核心线程空闲立即回收
                new LinkedBlockingQueue<>(1000)); // 阻塞队列大小为 50
    }


    @Test
    public void testWriteInfo() throws IOException {
        TsData tsData1 = new TsData( true, System.currentTimeMillis(), 1.234f, "stable_ali_test");
        TsData tsData2 = new TsData( true, System.currentTimeMillis(), 1.234f, "stable_ali_test");
        TsData tsData3 = new TsData( true, System.currentTimeMillis(), 1.234f, "stable_ali_test");
        TsData tsData4 = new TsData( true, System.currentTimeMillis(), 1.234f, "stable_ali_test");
        String path = "D:\\tmp\\tsdata20230724.avro"; // avro文件存放目录
        DatumWriter<TsData> userDatumWriter = new SpecificDatumWriter<TsData>(TsData.class);
        DataFileWriter<TsData> dataFileWriter = new DataFileWriter<TsData>(userDatumWriter);
        dataFileWriter.create(tsData2.getSchema(), new File(path));
        Random random = new Random();
        // 把生成的user对象写入到avro文件
        for (int i = 0; i < 1000; i++) {
            TsData tsData = new TsData(true, System.currentTimeMillis(), random.nextFloat() * 100, "stable_ali_test" + i);
            dataFileWriter.append(tsData);
        }
//        dataFileWriter.append(tsData2);
//        dataFileWriter.append(tsData3);
//        dataFileWriter.append(tsData4);
        dataFileWriter.flush();
        dataFileWriter.close();

    }

    @Test
    public void testReadInfo() throws IOException {

        DatumReader<TsData> reader = new SpecificDatumReader<TsData>(TsData.class);
        DataFileReader<TsData> dataFileReader = new DataFileReader<TsData>(new File("D:\\data\\backup\\112\\20230805\\zn_wind_stable2023080500.avro"), reader);
        TsData user = null;
        while (dataFileReader.hasNext()) {
            user = dataFileReader.next();
            System.out.println(user);
        }
        System.out.println(user);
        
    }

    @Test
    public void downLoadSpeed() {

        try {
            Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
//            Class.forName("com.taosdata.jdbc.TSDBDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String jdbcUrl = "jdbc:TAOS-RS://10.162.200.58:6041/zjny_center_tsdb_td?user=root&password=taosdata&timezone=UTC-8&charset=UTF-8&locale=en_US.UTF-8";
//        String jdbcUrl = "jdbc:TAOS://10.162.200.55:6030/zjny_center_tsdb_td?user=root&password=taosdata&timezone=UTC-8&charset=UTF-8&locale=en_US.UTF-8";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        LocalDate localDateTime = LocalDateTime.parse("2023-07-26T15:00:00").toLocalDate();
        LocalDateTime startTime = LocalDateTime.parse("2023-07-26T15:00:00");
        LocalDateTime endTime =  LocalDateTime.parse("2023-07-26T15:02:00");
        Integer step = 120;
        String sqlDemo = "select * from  zn_wind_stable where time>%s and time<%s";
        LocalDateTime cursorTime = LocalDateTime.of(startTime.toLocalDate(), startTime.toLocalTime());
        Integer taskNum=0;
//        CountDownLatch countDownLatch=new CountDownLatch(100);

        try {
            while (cursorTime.isBefore(endTime)) {
                Long taskStartTime = System.currentTimeMillis();
                Long startT = cursorTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                Long endT = startT + step * 1000;
                Statement statement = conn.createStatement();
                executor.execute(new DownLoadTask(taskNum.toString(),statement,startT,endT));
                taskNum++;
//                String execSql = String.format(sqlDemo, startT, endT);
//                Statement statement = conn.createStatement();
//                ResultSet rs = statement.executeQuery(execSql);
//                System.out.println(" exec demo " + execSql);
//
//                List<JSONObject> list = new ArrayList<>();
//                try {
//                    Integer columnNum = rs.getMetaData().getColumnCount();
//                    ResultSetMetaData rsmd = rs.getMetaData();
//                    while (rs.next()) {
//                        JSONObject jo = new JSONObject();
//                        for (int i = 0; i < columnNum; i++) {
//                            jo.put(rsmd.getColumnName(i + 1), rs.getString(i + 1));
//                        }
//                        list.add(jo);
//                    }
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                } finally {
//                    rs.close();
//                    statement.close();
//                }

                cursorTime = cursorTime.plusSeconds(step);
//                System.out.println("minutes -- data size " + list.size() + "    耗时：： " + (System.currentTimeMillis() - taskStartTime));

            }
            Thread.sleep(1000000);


        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        System.out.println(startTime + "  " + endTime);
        String sqlline = "select * from  zn_wind_stable where  time>now-5h ";

    }

    @Test
    public void testDateUtil() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        LocalDateTime plusTime = localDateTime.plusDays(60);
        System.out.println(plusTime);

    }


    class DownLoadTask implements Runnable {
        private String taskNum;
        private String sqlFormat = "select * from  zn_wind_stable where time>%s and time<%s";
        private Statement statement;
        private Long startTime;
        private Long endTime;

        public DownLoadTask(String taskNum,  Statement statement, Long startTime, Long endTime) {
            this.taskNum = taskNum;
            this.statement = statement;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public void run() {
            List<JSONObject> list = new ArrayList<>();
            Long startTaskTime=System.currentTimeMillis();
            String execSql = String.format(sqlFormat, startTime, endTime);
//            Statement statement = conn.createStatement();
            ResultSet rs = null;
            try {
                rs = statement.executeQuery(execSql);
                System.out.println(" exec demo " + execSql);

//                List<JSONObject> list = new ArrayList<>();
                try {
                    Integer columnNum = rs.getMetaData().getColumnCount();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    while (rs.next()) {
                        JSONObject jo = new JSONObject();
                        for (int i = 0; i < columnNum; i++) {
                            if(rsmd.getColumnName(i+1).equals("time")) {
                                jo.put(rsmd.getColumnName(i + 1), rs.getLong(i + 1));
                            }else{
                                jo.put(rsmd.getColumnName(i + 1), rs.getString(i + 1));
                            }
                        }
                        list.add(jo);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    rs.close();
                    statement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("minutes -- data size " + list.size() + "    耗时：： " + (System.currentTimeMillis() - startTaskTime));
        }
    }



    @Test
    public void  checkDateFormat(){
//        File file=new File("D:\\data\\backup\\back1\\20230727\\");

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 在这里编写任务的具体逻辑
                System.out.println("定时任务执行了！");
            }
        };

        Timer timer = new Timer();
        // 在1秒后开始执行任务，并每隔5秒重复执行
        timer.schedule(task, 1000, 5000);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
