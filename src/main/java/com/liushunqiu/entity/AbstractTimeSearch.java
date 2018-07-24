package com.liushunqiu.entity;

import java.io.Serializable;

public abstract class AbstractTimeSearch extends TimeSearchObjectSupport implements TimeSearch,Serializable {

    TimeSearchObjectSupport  timeSearchObjectSupport;

    @Override
    public final void wrapTime(Object timeSearch){
        this.timeSearchObjectSupport = (TimeSearchObjectSupport)timeSearch;
    }

    protected abstract String generationStartTime(String startTime);

    protected abstract String generationEndTime(String endTime);
}
