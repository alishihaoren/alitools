package com.zn.learn.basic.network;

import com.alibaba.fastjson.annotation.JSONField;

public class TagData {
    @JSONField(name = "tag_name")
    private String tagName;
    @JSONField(name = "tag_value")
    private Double tagValue;
    @JSONField(name = "time")
    private String  time;

    public TagData(String tagName, Double tagValue, String time) {
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.time = time;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Double getTagValue() {
        return tagValue;
    }

    public void setTagValue(Double tagValue) {
        this.tagValue = tagValue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
