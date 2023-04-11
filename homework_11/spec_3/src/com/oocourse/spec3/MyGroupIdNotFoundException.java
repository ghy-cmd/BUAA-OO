package com.oocourse.spec3;

import com.oocourse.spec3.exceptions.GroupIdNotFoundException;

import java.util.HashMap;

public class MyGroupIdNotFoundException extends GroupIdNotFoundException {
    private int id;
    private static HashMap<Integer, Integer> hashMap = new HashMap<>();
    private static int count = 0;

    public MyGroupIdNotFoundException(int id) {
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
        System.out.println("ginf-" + count + ", " + id + "-" + hashMap.get(id));
    }

}
