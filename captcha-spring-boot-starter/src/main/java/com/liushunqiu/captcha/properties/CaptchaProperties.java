package com.liushunqiu.captcha.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("common.captcha")
public class CaptchaProperties {
    private int num;

    private int width;

    private int height;

    private boolean hasLineSize;

    public boolean isHasLineSize() {
        return hasLineSize;
    }

    public void setHasLineSize(boolean hasLineSize) {
        this.hasLineSize = hasLineSize;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
