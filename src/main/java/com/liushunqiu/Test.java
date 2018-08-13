package com.liushunqiu;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        List<Integer> subList = list.subList(0,2);
        System.out.println(subList);
        System.out.println(list);
        subList.clear();
        System.out.println(list);
    }
}
