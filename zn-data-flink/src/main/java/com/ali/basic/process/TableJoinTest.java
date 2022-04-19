package com.ali.basic.process;


import com.ali.basic.beans.BandStudentRule;
import com.ali.basic.beans.Student;
import com.ali.basic.source.RandomBeanSource;
import com.ali.basic.source.RuleParallSource;
import com.alibaba.fastjson.JSON;
import org.apache.flink.api.scala.typeutils.Types;
import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.streaming.api.functions.co.CoProcessFunction;
import org.apache.flink.streaming.api.functions.co.KeyedCoProcessFunction;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.types.DataType;
import org.apache.flink.util.Collector;

public class TableJoinTest {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        senv.setParallelism(2);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(senv);
        DataStreamSource<Student> dataStream = senv.addSource(new RandomBeanSource());
        DataStreamSource<BandStudentRule> ruleStream = senv.addSource(new RuleParallSource()).setParallelism(1);
      ConnectedStreams<Student,BandStudentRule>  connStream= dataStream.connect(ruleStream).keyBy(Student::getName, BandStudentRule::getName
        );



//          定义 table
        tableEnv.createTemporaryView("ali_tb",dataStream, Schema.newBuilder().
                columnByExpression("tmp1","TO_TIMESTAMP_LTZ(tmp, 3)")
                .watermark("tmp1","tmp1 - INTERVAL '1' SECOND")
                .build());

        tableEnv.createTemporaryView("rule_tb",ruleStream,Schema.newBuilder().
                columnByExpression("tmp1","TO_TIMESTAMP_LTZ(tmp, 3)")
                .watermark("tmp1","tmp1 - INTERVAL '1' SECOND")
                .build());
//        tableEnv.executeSql("insert into ali_tb values(1,'FFFF',1234567890,LOCALTIME)");


        // window 操作
//        tableEnv.executeSql("select  * from table (  tumble( table(rule_tb,DESCRIPTOR(tmp1),INTERVAL '10' SECONDS) ))").print();
// window  join operations
// tableEnv.executeSql("SELECT name,window_start, window_end, count(1)\n" +
//        "  FROM TABLE(\n" +
//        "    HOP(TABLE ali_tb, DESCRIPTOR(tmp1), INTERVAL '2' SECONDS,INTERVAL '10' SECONDS))\n" +
//        "  GROUP BY name, window_start, window_end").print();


        // 集成操作  简单join

        tableEnv.executeSql("  select aa.*,bb.* from  ali_tb aa left join(  select  name,LAST_VALUE(level) as nums  from  rule_tb group by name ) bb on aa.name=bb.name  ").print();





        senv.execute("table data process ");

    }


}
