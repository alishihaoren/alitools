package com.ali.basic.source;

import com.ali.basic.beans.BandStudentRule;
import com.ali.basic.beans.Student;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class RuleParallSource extends RichParallelSourceFunction<BandStudentRule> {
    //    private  boolean  productFLag=true;
    private AtomicBoolean productFLag = new AtomicBoolean(true);

    private Random random = new Random();
    private String[] stuArr = {"AAA", "BBB", "CCC", "DDD", "EEE"};


    @Override
    public void run(SourceContext<BandStudentRule> sourceContext) throws Exception {
        RuntimeContext runtimeContext = getRuntimeContext();
        Integer totalPa = runtimeContext.getNumberOfParallelSubtasks();

        while (productFLag.get()) {
//            System.out.println(runtimeContext.getIndexOfThisSubtask()+"   "+totalPa);
            BandStudentRule bandStudentRule = new BandStudentRule(random.nextInt(4), stuArr[random.nextInt(4)], 60.0, System.currentTimeMillis());
            sourceContext.collect(bandStudentRule);
//sourceContext.emitWatermark(new Watermark(0L));

            Thread.sleep(2000);
        }


    }

//    @Override
//    public void run(SourceContext<BandStudentRule> sourceContext) throws Exception {
//
//    }

    @Override
    public void cancel() {
        productFLag=new AtomicBoolean( false);
    }

    @Override
    public void open(Configuration parameters) throws Exception {


        super.open(parameters);
    }
}
