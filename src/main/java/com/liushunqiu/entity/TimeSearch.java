package com.liushunqiu.entity;

import java.io.Serializable;

public interface TimeSearch extends Serializable {
    /**
     * 包装参数时间
     * @param timeSearch
     */
    void wrapTime(Object timeSearch);
}
