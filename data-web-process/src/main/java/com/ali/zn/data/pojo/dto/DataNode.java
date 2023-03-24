package com.ali.zn.data.pojo.dto;

public class DataNode {

    private String dataId;

    private Double value;

    public DataNode(String dataId, Double value) {
        this.dataId = dataId;
        this.value = value;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
