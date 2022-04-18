package com.ali.basic.opeartions;


import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class MemoryFlinkTable {


    public static void main(String[] args) {
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(senv);
        tableEnv.executeSql(
                "CREATE TABLE GeneratedTable "
                        + "("
                        + "  name STRING,"
                        + "  score INT,"
                        + "  event_time TIMESTAMP_LTZ(3),"
                        + "  WATERMARK FOR event_time AS event_time - INTERVAL '10' SECOND"
                        + ")"
                        + "WITH ('connector'='datagen')");
        tableEnv.executeSql("insert into GeneratedTable values('ali',123,LOCALTIME) ");

        Table table = tableEnv.from("GeneratedTable");
        tableEnv.executeSql("select name,sum(score) from GeneratedTable group by  name").print();

        try {
            senv.execute(" data lines");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
