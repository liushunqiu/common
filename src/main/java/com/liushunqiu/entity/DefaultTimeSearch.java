package com.liushunqiu.entity;

import com.liushunqiu.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class DefaultTimeSearch extends AbstractTimeSearch implements Serializable {

    public DefaultTimeSearch(){
        wrapTime(this);
    }

    protected transient static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 默认传递过来的startTime 格式为 yyyy-MM-dd
     * @param startTime
     * @return
     */
     @Override
     protected String generationStartTime(String startTime) {
        if (StringUtils.isEmpty(startTime)) {
            return LocalDateTime.of(1990,01,01,00,00,00).toString();
        }
        ParsePosition pos = new ParsePosition(0);
        return DateUtils.getStartOfDay(format.parse(startTime,pos)).toString();
    }
    /**
     * 默认传递过来的startTime 格式为 yyyy-MM-dd
     * @param endTime
     * @return
     */
    @Override
    protected String generationEndTime(String endTime) {
        if (StringUtils.isEmpty(endTime)){
            return LocalDateTime.now().toString();
        }
        ParsePosition pos = new ParsePosition(0);
        return DateUtils.getEndOfDay(format.parse(endTime,pos)).toString();
    }


    @Override
    public void setStartTime(String startTime) {
        super.timeSearchObjectSupport.selfSetStartTime(generationStartTime(startTime));
    }

    @Override
    public void setEndTime(String endTime) {
        super.timeSearchObjectSupport.selfSetEndTime(generationEndTime(endTime));
    }
}
