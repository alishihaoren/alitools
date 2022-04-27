package com.ali.test.avictor;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorString;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagFuncation extends AbstractFunction {

    public final static Pattern pattern = Pattern.compile("\'[^}]+?\'");


    @Override
    public String getName() {
        return "getTag";
    }

    @Override
    public AviatorDouble call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {

        String text = FunctionUtils.getStringValue(arg1, env);
        String dateText = FunctionUtils.getStringValue(arg2, env);
        // 正则找到文案中包含的参数
        List<String> params = Lists.newArrayList();
        Matcher m = pattern.matcher(text);
        Matcher dateM = pattern.matcher(dateText);

        while (m.find()) {
            params.add(m.group(1));
        }
        while (dateM.find()){
            params.add(dateM.group(1));
        }
        // 替换参数
        for (String paramKey : params) {
            String param = "'" + paramKey + "'";
            if (text.contains(param) && env.containsKey(paramKey)) {
                text = text.replace(param, String.valueOf(env.get(paramKey)));
            }
        }

        if (pattern.matcher(text).find()) {
            return new AviatorDouble(text.length());
        } else {
            return new AviatorDouble(text.length());
        }


//        return super.call(env, arg1, arg2);
    }
}
