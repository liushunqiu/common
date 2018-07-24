package com.liushunqiu.entity;

import java.io.Serializable;

public abstract class TimeSearchObjectSupport implements Serializable {
    private String startTime;

    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public abstract void setStartTime(String startTime);

    protected void selfSetStartTime(String startTime){
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public abstract void setEndTime(String endTime);

    protected void selfSetEndTime(String endTime){
        this.endTime = endTime;
    }
}
