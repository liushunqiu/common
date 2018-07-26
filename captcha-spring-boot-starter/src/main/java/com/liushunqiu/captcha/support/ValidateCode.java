package com.liushunqiu.captcha.support;

import java.io.Serializable;

public class ValidateCode implements Serializable {
    private String encryptKey;

    private String code;

    public static ValidateCode builder(){
        return new ValidateCode();
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public ValidateCode setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ValidateCode setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return "ValidateCode{" +
                "encryptKey='" + encryptKey + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
