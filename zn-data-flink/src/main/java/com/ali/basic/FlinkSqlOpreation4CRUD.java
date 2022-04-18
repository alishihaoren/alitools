package com.ali.basic;


import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FlinkSqlOpreation4CRUD {

    public static void main(String[] args) {
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment sTableEnv = StreamTableEnvironment.create(senv);
        sTableEnv.executeSql("CREATE TABLE file_tb (\n" +
                "  tagName String,\n" +
                "  tagValue Double,\n" +
                "   piTS String\n" +
                ") WITH (\n" +
                "  'connector' = 'filesystem',\n" +
                "  'path' = 'file:///D:\\data/orgSource',\n" +
                "  'format' = 'json'\n" +
                ")");


        sTableEnv.executeSql("select * from  file_tb limit 10").print();
//        sTableEnv.executeSql("update  ");
        Table tb_v1 = sTableEnv.sqlQuery("select * from  file_tb  ");
//        sTableEnv.createTemporaryView("s1_tb",tb_v1);
        sTableEnv.toDataStream(tb_v1).map(new MapFunction<Row, Object>() {
            @Override
            public Object map(Row row) throws Exception {
                Set<String> columns = row.getFieldNames(true);
                Map<String,Object> onceMap=new HashMap<>();
                   for(String key:columns){
                       onceMap.put(key,row.getField(key));
                   }
                return JSON.toJSONString(onceMap);
            }
        }).print();

        try {
            senv.execute(" sql data process");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
