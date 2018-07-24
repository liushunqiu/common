package com.liushunqiu.entity;

import java.io.Serializable;

public class ValidateCode implements Serializable {
    private String encryptStr;

    private String code;

    public static ValidateCode builder(){
        return new ValidateCode();
    }

    public String getEncryptStr() {
        return encryptStr;
    }

    public ValidateCode setEncryptStr(String encryptStr) {
        this.encryptStr = encryptStr;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ValidateCode setCode(String code) {
        this.code = code;
        return this;
    }
}
