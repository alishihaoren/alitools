package com.ali.zn.data.dataprocess;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class DataTEst {


    @Test
    public  void getDateFormat(){
        String  dateStr="{\"tag_name\":\"2261.kw_1_coal_mill_kwh\",\"device_idx\":\"1\",\"type\":\"HFCCP\",\"start_time\":\"2025-03-31 16:09:00.000\",\"end_time\":\"2025-03-31 17:14:00.000\",\"duration\":3900.0000000000005,\"max_value\":0.339985921130762,\"min_value\":0.339985921130762,\"time_weighted_avg\":0.339985921130762,\"baseline\":0.36,\"load_rate\":0.8,\"calc_time\":\"2025-04-25 15:42:33.257\"}";

        JSONObject jsonObject = JSONObject.parseObject(dateStr);
        System.out.println(jsonObject.getDate("start_time"));




    }
}
