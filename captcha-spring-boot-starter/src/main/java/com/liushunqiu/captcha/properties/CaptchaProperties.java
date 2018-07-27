package com.liushunqiu.captcha.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("common.captcha")
public class CaptchaProperties {
    private int num = 4;

    private int width = 80;

    private int height = 26;

    private int lineSize = 40;

    private boolean hasLineSize = true;

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

    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

    @Override
    public String toString() {
        return "CaptchaProperties{" +
                "num=" + num +
                ", width=" + width +
                ", height=" + height +
                ", lineSize=" + lineSize +
                ", hasLineSize=" + hasLineSize +
                '}';
    }
}
