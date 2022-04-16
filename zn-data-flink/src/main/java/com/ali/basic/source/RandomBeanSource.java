package com.ali.basic.source;

import com.ali.basic.beans.Student;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class RandomBeanSource implements SourceFunction<Student> {
    private AtomicBoolean productFLag = new AtomicBoolean(true);

    private Random random=new Random();
    private String[]   stuArr={"AAA","BBB","CCC","DDD","EEE"};

    @Override
    public void run(SourceContext<Student> sourceContext) throws Exception {


        while (productFLag.get()) {
            Double  value=random.nextDouble()*100;
            Student student=new Student(random.nextInt(10),stuArr[random.nextInt(5)],new BigDecimal(value).setScale(4, RoundingMode.HALF_UP).doubleValue(),System.currentTimeMillis());
            sourceContext.collect(student);
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        productFLag = new AtomicBoolean(false);
    }
}
