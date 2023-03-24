package com.ali.zn.data.controller;

import com.ali.zn.data.pojo.dto.DataNode;
import com.ali.zn.data.service.TagService;
import com.ali.zn.data.utils.SourceConf;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/test")
public class DataProcessController {

    @Autowired
    private TagService tagService;

    @Autowired
    private SourceConf sourceConf;

    private boolean  state=true;

    @RequestMapping(value = "/ali")
    public String getMsg(HttpServletRequest request) {
        System.out.println(request.getHeader("name"));
        return JSON.toJSONString(tagService.getTagInfoById(Arrays.asList("110", "111", "112")));
//
//        System.out.println(JSON.toJSONString(request.getSession().getAttributeNames()));
//        return "alishihaoren";
    }

    @RequestMapping(value = "/getTagValue")
    public List<Map<String, Object>> getMsg(String tagName) {
//          Map<String,Double> resdata=new HashMap<>();
//          resdata.put("dataId")
        // {
        //  "id": "6e402f9c",
        //  "showChild": 1
        //}
        Random random = new Random();

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("id", "1234");
        if(state) {
            data1.put("showChild", 0);
        }else{
            data1.put("showChild", 1);
        }
        state=!state;

        Map<String, Object> data2 = new HashMap<>();
        data2.put("dataId", "1234");
        data2.put("value", 123);
        list.add(data1);
        list.add(data2);
//        Map<String, Object> data3 = new HashMap<>();
//        data3.put("id", "1234");
//        data3.put("showChild", 2);
//        list.add(data3);
        return list;

//        return  "[{\"dataId\":\"device-001\",\"value\":31}ue\":88},{:58},{\"dataId\":\"d-2-d-002\",\"value\":1},{\"dataId\":\"d-2-d-003\",\"value\":82},{\"dataId\":\"device-002-e\",\"value\":15},{\"dataId\":\"d-2-e-001\",\"value\":-9},{\"dataId\":\"d-2-e-002\",\"value\":33},{\"dataId\":\"d-2-e-003\",\"value\":81},{\"dataId\":\"device-003\",\"value\":19},{\"dataId\":\"device-003-a\",\"value\":57},{\"dataId\":\"d-3-a-001\",\"value\":43},{\"dataId\":\"d-3-a-002\",\"value\":56},{\"dataId\":\"d-3-a-003\",\"value\":83},{\"dataId\":\"device-003-b\",\"value\":42},{\"dataId\":\"d-3-b-001\",\"value\":1.1685176},{\"dataId\":\"d-3-b-002\",\"value\":true},{\"dataId\":\"d-3-b-003\",\"value\":0},{\"dataId\":\"d-3-b-004\",\"value\":0.62714756}]";
//        System.out.println(JSON.toJSONString(request.getSession().getAttributeNames()));
//        return "alishihaoren";
    }

    ;

    @RequestMapping(value = "/bean")
    public String getSource() {

        System.out.println(sourceConf.getAge());
        return "1222";
    }


}