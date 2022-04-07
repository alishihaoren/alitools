package com.ali.basic.source;

import com.ali.basic.beans.Student;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class RandomBeanSource  implements SourceFunction<Student> {
    private  boolean  productFLag=true;
    @Override
    public void run(SourceContext<Student> sourceContext) throws Exception {

        while(productFLag){



            Thread.sleep(1000);
        }


    }

    @Override
    public void cancel() {

    }
}
