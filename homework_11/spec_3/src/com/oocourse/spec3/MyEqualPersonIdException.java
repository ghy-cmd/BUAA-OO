package com.oocourse.spec3;

import com.oocourse.spec3.exceptions.EqualPersonIdException;

import java.util.HashMap;

public class MyEqualPersonIdException extends EqualPersonIdException {
    private int id;
    private static HashMap<Integer, Integer> hashMap = new HashMap<>();
    private static int count = 0;

    public MyEqualPersonIdException(int id) {
        this.id = id;
        if (hashMap.containsKey(id)) {
            int time = hashMap.get(id);
            time++;
            hashMap.put(id, time);
        } else {
            hashMap.put(id, 1);
        }
        count++;
    }

    @Override
    public void print() {
        System.out.println("epi-" + count + ", " + id + "-" + hashMap.get(id));
    }
}
