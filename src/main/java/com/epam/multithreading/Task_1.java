package com.epam.multithreading;

import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.out;

public class Task_1 {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<Integer, Integer> values = new ConcurrentHashMap<>();
        Runnable threadOne = () -> {
            values.put(1,2);
            values.put(3,4);
            values.put(5,6);
            values.put(7,8);
            out.println(Thread.currentThread().getName() + "Thread one");
        };

        Runnable threadTwo = () -> {
            var sum = values.values().stream().mapToInt(Integer::intValue).sum();
            out.println(sum);
            out.println(Thread.currentThread().getName() + "Thread two");
        };

        Thread t2 = new Thread(threadTwo);
        out.println(Thread.currentThread().getName());
        Thread t1 = new Thread(threadOne);
        out.println(Thread.currentThread().getName());

        t2.start();
        t1.start();
    }
}
