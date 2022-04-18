package com.ali.basic.process;



import com.ali.basic.beans.Student;
import com.ali.basic.source.RandomBeanSource;
import org.apache.flink.api.scala.typeutils.Types;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.types.DataType;

public class TableJoinTest {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment   tableEnv=StreamTableEnvironment.create(senv);
        DataStreamSource<Student> dataStream=senv.addSource(new RandomBeanSource());

        //  定义 table
        tableEnv.createTemporaryView("ali_tb",dataStream, Schema.newBuilder().
                columnByExpression("tmp1","TO_TIMESTAMP_LTZ(tmp, 3)")
                .watermark("tmp1","tmp1 - INTERVAL '1' SECOND")
                .build());
        tableEnv.executeSql("insert into ali_tb values(1,'FFFF',1234567890,LOCALTIME)");

        tableEnv.executeSql("select name,count(1) as nums from ali_tb group by name").print();







        senv.execute("table data process ");

    }


}
