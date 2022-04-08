package com.ali.basic.source;

import com.ali.basic.beans.Student;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class RandomBeanSource implements SourceFunction<Student> {
    private AtomicBoolean productFLag = new AtomicBoolean(true);

    private Random random=new Random();
    private String[]   stuArr={"AAA","BBB","CCC","DDD","EEE"};

    @Override
    public void run(SourceContext<Student> sourceContext) throws Exception {

        while (productFLag.get()) {
            Student student=new Student(random.nextInt(10),stuArr[random.nextInt(5)],new BigDecimal(random.nextDouble()).setScale(4).doubleValue(),System.currentTimeMillis());
            sourceContext.collect(student);
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        productFLag = new AtomicBoolean(false);
    }
}
