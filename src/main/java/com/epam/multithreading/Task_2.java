package com.epam.multithreading;

import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.System.out;

public class Task_2 {
    public static final Object mutex1 = new Object();
    public static final Object mutex2 = new Object();
    public static final Object mutex3 = new Object();
    public static CopyOnWriteArrayList<Double> values;

    public static void main(String[] args) throws InterruptedException {

        synchronized (mutex1) {
            values = new CopyOnWriteArrayList<>();
        }
        boolean isInfinity = true;

        Runnable threadOne = () -> {
            out.println(Thread.currentThread().getName() + "threadOne");
            while (isInfinity) {
                values.add(Math.random() * 100);
            }
        };

        Thread t1 = new Thread(threadOne);
        ThreadTwo t2 = new ThreadTwo();
        ThreadThree t3 = new ThreadThree();

        t1.start();
        t2.start();
        t3.start();
    }

    private static class ThreadTwo extends Thread{
        @Override
        public void run() {
            synchronized (mutex2) {
                double sum = values.stream().mapToDouble(Double::doubleValue).sum();
                out.println(sum);
                out.println(Thread.currentThread().getName() + "threadTwo");
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    out.println("Thread Two: Acquired lock on Two and Three");
                }
                synchronized (mutex3) {
                    out.println("Block three");
                }
            }
        }
    }

    private static class ThreadThree extends Thread{
        @Override
        public void run() {
            synchronized (mutex2) {
                double sum = values.stream().mapToDouble(value -> Math.pow(value, 2)).sum();
                out.println(Math.sqrt(sum));
                out.println(Thread.currentThread().getName() + "threadThree");
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    out.println("Thread three: Acquired lock on two and three");
                }
                synchronized (mutex3) {
                    out.println("Block two");
                }
            }
        }
    }
}
