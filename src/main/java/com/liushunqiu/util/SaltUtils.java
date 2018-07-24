package com.liushunqiu.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 生成6位随机加盐
 */
public final class SaltUtils {
    /**
     * 构建特殊字符串
     */
    static final List<String> specials = Arrays.asList("~","!","@","#","$","%","^","&","*","(",")","_","+","{","}",":","<",">");
    /**
     * 小写字母
     */
    static final List<String> letters = Arrays.asList("q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m","Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M");
    /**
     * 数字
     */
    static final List<String> numbers = Arrays.asList("1","2","3","4","5","6","7","8","9","0");


    public static String salt(){
        StringBuilder builder = new StringBuilder();
        IntStream.range(0,2).forEach(i->{
            int specialsRandom = (int) (Math.random() * specials.size());
            builder.append(specials.get(specialsRandom));
            int lettersRandom = (int) (Math.random() * letters.size());
            builder.append(letters.get(lettersRandom));
            int numbersRandom = (int) (Math.random() * numbers.size());
            builder.append(numbers.get(numbersRandom));
        });
        return builder.toString();
    }

    public static void main(String[] args) {
        IntStream.range(0,1000).forEach(i->{
            System.out.println(salt());
        });
    }
}
