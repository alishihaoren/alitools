package com.ali.zn.data.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Log4j2
public class WindPowerForecastdataprocess {


    public static void main(String[] args) {
        // 消费   201 数据
//        String kafkaUrl = "10.162.200.23:9092,10.162.200.24:9092,10.162.200.25:9092";
        // 拉取 时序数据库中的数据
        // 5151.WY0CRB04FE101	嘉海风电未来10天短期预测_上报出力
        // 5151.WY0CRB03FE101	嘉海风电未来4小时超短期预测_上报出力
//        String shortForcasttagName="5151.WY0CRB04FE101";
//        String superShortForcasttagName="5151.WY0CRB03FE101";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stable="st5151";
        StringBuilder sqlbuilder = new StringBuilder("insert into ");

        List<String>   tagList=Arrays.asList("5151.WY0CRB04FE101","5151.WY0CRB03FE101");

        for (String tag : tagList) {


        try {
            Class.forName("com.taosdata.jdbc.rs.RestfulDriver");
            String jdbcUrl = "jdbc:TAOS-RS://10.166.15.226:16041/zn_hfzx?user=root&password=taosdata";
// electric_quantity(electric_quantity),is_good(is_good),station_id(station_id),时间(time),机组编码(tag_name)

            Connection conn = DriverManager.getConnection(jdbcUrl);
            Statement statement = conn.createStatement();
            String sqlData = "select tag_name, time,tag_value,is_good from st5151  where  tag_name='"+tag+"' and time>now-10m order by time ";
            ResultSet rs = statement.executeQuery(sqlData);

            ResultSetMetaData rsm = rs.getMetaData();
            Integer colSize = rsm.getColumnCount();
            List list = new ArrayList<JSONObject>();
            Long startTime =0L;
            Long offset = 0L;

            Integer count = 10;

            // 5. 处理结果集
            while (rs.next()) {

                // 获取列数据（根据实际列名/类型修改）
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                System.out.println("ID: " + id + ", Name: " + name);
                JSONObject jo = new JSONObject();
                Long    time=   rs.getLong("time");
                if(startTime==0L){
                    startTime = time;
                }else{
                    offset=(time-startTime)/900000;
                }
                String  tagValue = rs.getString("tag_value");
                String  timeStr = sdf.format(time);
                jo.put("timeStr",timeStr);
                jo.put("time",time);
                jo.put("is_good",rs.getBoolean("is_good"));
               boolean  is_good=rs.getBoolean("is_good");
                jo.put("tag_value",tagValue);
                jo .put("offset",offset);
                String  tag_name=tag+"_"+offset;

                String tbname=stable+"_"+tag_name.replace(".","_").replace("-","_");
                jo .put("tbname",tbname);
                jo .put("tagName",tag_name);
                list.add(jo);

                sqlbuilder.append(tbname).append("(time,is_good,tag_value) using ").append( stable).append(" (tag_name,standard_code) TAGS( '")
                        .append(tag_name).append("','").append(tag_name).append("') values(").append(time+","+is_good+","+tagValue+") ");
//                count--
            }
            statement.executeUpdate(sqlbuilder.toString());
            rs.close();
            statement.close();
            conn.close();


        } catch (Exception e) {

            e.printStackTrace();
        }
        }


    }

}
