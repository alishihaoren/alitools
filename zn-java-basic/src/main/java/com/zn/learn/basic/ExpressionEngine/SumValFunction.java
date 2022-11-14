package com.zn.learn.basic.ExpressionEngine;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

public class SumValFunction extends AbstractFunction {
    @Override
    public String getName() {
        return "SumVal";
    }

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        String tagName = FunctionUtils.getStringValue(arg1, env);
        String startTime = FunctionUtils.getStringValue(arg2, env);
        String endTime = FunctionUtils.getStringValue(arg3, env);
        System.out.println("    -----  exec ::"+ AviatorUtils.getTime(startTime,getName(),null));
        System.out.println("    -----  exec ::"+ AviatorUtils.getTime(endTime,getName(),null));

        System.out.println(tagName+ startTime+endTime);



        return new AviatorDouble(0.123);
    }
}
