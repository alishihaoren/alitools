package com.ali.basic.cep;

import com.ali.basic.beans.Student;
import com.ali.basic.source.RandomBeanSource;
import com.alibaba.fastjson.JSON;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.runtime.operators.coordination.OperatorCoordinator;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.scala.DataStream;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.apache.flink.walkthrough.common.entity.Alert;

import java.util.List;
import java.util.Map;


public class StreamCepDataProcess {

    public static void main(String[] args) {
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Student> dataStream = senv.addSource(new RandomBeanSource());
//        dataStream.map(line->JSON.toJSONString(line)).print();
      Pattern<Student,?> pattern=  Pattern.<Student>begin("start").where(new SimpleCondition<Student>() {
            @Override
            public boolean filter(Student student) throws Exception {
                if (student.getScore() > 0)
                    return true;
                else return true;
            }
        }).within(Time.minutes(1));

        PatternStream<Student> patternStream = CEP.pattern(dataStream, pattern);

        SingleOutputStreamOperator<String> result = patternStream.process(
                new PatternProcessFunction<Student, String>() {
                    @Override
                    public void processMatch(Map<String, List<Student>> map, Context context, Collector<String> collector) throws Exception {
                        collector.collect(JSON.toJSONString(map));
                    }
                });


        result.print();

        try {
            senv.execute("  flink  cep test ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
