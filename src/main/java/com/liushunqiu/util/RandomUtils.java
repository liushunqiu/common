package com.liushunqiu.util;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomUtils {

    public static String generateChar12(){
        String z = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder no = new StringBuilder();
        IntStream.range(0,12).forEach(i->{
            no.append(z.charAt(new Random().nextInt(52)));
        });
        return no.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateChar12());
    }
}
