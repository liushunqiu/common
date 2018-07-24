package com.liushunqiu.util;

import org.apache.commons.codec.digest.DigestUtils;

public final class PwdUtils {
    /**
     * 密码md5加盐加密
     * @param pwd
     * @return
     */
    public static final String md5Encrypt(String pwd,String salt){
        StringBuilder builder = new StringBuilder();
        String encrypt = builder.append(DigestUtils.md5Hex(pwd))
                .append(salt)
                .toString();
        return DigestUtils.md5Hex(encrypt);
    }
}
