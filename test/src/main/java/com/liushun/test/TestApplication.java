package com.liushun.test;

import com.liushunqiu.captcha.CaptchaUtils;
import com.liushunqiu.captcha.support.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestApplication {
    @Autowired(required = false)
    private CaptchaUtils captchaUtils;

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class,args);
    }

    @RequestMapping(value ="/test1")
    public void test1(){
        try {
            ValidateCode validateCode = captchaUtils.getRandcode();
            System.out.println(validateCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value ="/test2/{encryptKey}/{code}")
    public void test2(@PathVariable("encryptKey")String encryptKey,@PathVariable("code")String code){
        System.out.println(captchaUtils.checkValidateCode(encryptKey,code,false));
    }

}