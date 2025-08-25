package com.ali.zn.data.dataprocess;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class DataTEst {


    @Test
    public  void getDateFormat(){
        String  dateStr="{\"companyCode\": \"5601\", \"tagName\": \"2261.DPU3013.SH0037.AALM1.PV_weekly\", \"tagValue\": 565.3155495723855, \"isGood\": true, \"piTS\": \"2025-05-04T00:00:00+08:00\", \"sendTS\": \"2025-07-28T16:35:46.374824+08:00\"}";

        JSONObject jsonObject = JSONObject.parseObject(dateStr);
        System.out.println(jsonObject.getString("tagName"));




    }
}
