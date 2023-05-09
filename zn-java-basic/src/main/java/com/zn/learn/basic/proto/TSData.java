package com.zn.learn.basic.proto;

import com.alibaba.fastjson.annotation.JSONField;

import java.time.LocalDateTime;

public class TSData {
    @JSONField(name = "piTS")
    private LocalDateTime time;

    private Double tagValue;

    private String tagName;

    private String isGood = "true";

    private String companyCode;

    private String sendTS;

    private LocalDateTime prevTime;

    private Double prevTagValue;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LocalDateTime getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(LocalDateTime prevTime) {
        this.prevTime = prevTime;
    }

    public Double getPrevTagValue() {
        return prevTagValue;
    }

    public void setPrevTagValue(Double prevTagValue) {
        this.prevTagValue = prevTagValue;
    }
    //    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }

    public Double getTagValue() {
        return tagValue;
    }

    public void setTagValue(Double tagValue) {
        this.tagValue = tagValue;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getIsGood() {
        return isGood;
    }

    public void setIsGood(String isGood) {
        this.isGood = isGood;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSendTS() {
        return sendTS;
    }

    public void setSendTS(String sendTS) {
        this.sendTS = sendTS;
    }
}
