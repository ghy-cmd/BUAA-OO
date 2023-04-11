package com.oocourse.spec3;

import com.oocourse.spec3.exceptions.EqualRelationException;

import java.util.HashMap;

public class MyEqualRelationException extends EqualRelationException {
    private int id1;
    private int id2;
    private static HashMap<Integer, Integer> hashMap = new HashMap<>();
    private static int count = 0;

    public MyEqualRelationException(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
        if (hashMap.containsKey(id1)) {
            int time = hashMap.get(id1);
            time++;
            hashMap.put(id1, time);
        } else {
            hashMap.put(id1, 1);
        }
        if (id1 != id2) {
            if (hashMap.containsKey(id2)) {
                int time = hashMap.get(id2);
                time++;
                hashMap.put(id2, time);
            } else {
                hashMap.put(id2, 1);
            }
        }
        count++;
    }

    @Override
    public void print() {
        if (id1 < id2) {
            System.out
                .println(
                    "er-" + count + ", " + id1 + "-" + hashMap.get(id1) + ", " + id2 + "-" +
                        hashMap.get(id2));
        } else {
            System.out
                .println(
                    "er-" + count + ", " + id2 + "-" + hashMap.get(id2) + ", " + id1 + "-" +
                        hashMap.get(id1));
        }
    }
}
