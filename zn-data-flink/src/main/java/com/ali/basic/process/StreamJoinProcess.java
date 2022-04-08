package com.ali.basic.process;

import com.ali.basic.source.RandomBeanSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class StreamJoinProcess {

    public static void main(String[] args) {
    StreamExecutionEnvironment senv=    StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment stEnv=StreamTableEnvironment.create(senv);
        senv.setParallelism(1).addSource(new RandomBeanSource());







    }

}
