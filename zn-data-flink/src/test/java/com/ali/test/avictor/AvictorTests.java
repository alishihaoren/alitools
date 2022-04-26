package com.ali.test.avictor;


import com.googlecode.aviator.AviatorEvaluator;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AvictorTests {

    @Test
    public  void  getExp(){
        AviatorEvaluator.addFunction(new  TagFuncation());
        String string = AviatorEvaluator.execute("1.2+3+4").toString();// 8.2
       Object data= AviatorEvaluator.execute("getTag('alishihaoren','*')>15?(getTag('alishihaoren','*')*3):1");

        System.out.println(data.toString());
//        String name="小明";
//        Map<String, Object> paramMap=new HashMap<>();
//        paramMap.put("name", name);
//        string=AviatorEvaluator.execute("'你的名字是'+name", paramMap).toString();// 你的名字是小明
//
//        paramMap.put("a", 5);
//        paramMap.put("b", 4);
//        string = AviatorEvaluator.execute("a+b/3.0", paramMap).toString();// 6.333333333333333
//
//        //推荐使用的方式
//        string = AviatorEvaluator.compile("a/(b-1.0)").execute(paramMap).toString();// 1.6666666666666667
//
//        string=AviatorEvaluator.exec("hahah+'上1年级'", name).toString();// 小明上1年级
//        System.out.println(string);

    }

    @Test
    public  void getPattern(){
        Pattern  pattern=Pattern.compile("\\$\\{(.*?)}");
        String  expStr="${alishihaoren}+${你好 世界}";
      Matcher matcher= pattern.matcher(expStr);
        if (matcher.find() && matcher.groupCount() >= 1){
            System.out.println(matcher.group(1)+matcher.group(2));
        }


    }

    @Test
    public  void  getTagName(){
        Pattern  pattern=   Pattern.compile("\'.+[^']@");
        String  expStr="TagVal('alishihaoren@900')+${你好 世界}";
        Matcher matcher= pattern.matcher(expStr);
        if (matcher.find() && matcher.groupCount() >= 1){
            System.out.println(matcher.group(1));
        }

    }

}
