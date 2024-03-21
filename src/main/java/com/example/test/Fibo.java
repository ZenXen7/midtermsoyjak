package com.example.test;

import java.util.Scanner;

public class Fibo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of Fibonacci elements to print: ");
        int num = scanner.nextInt();

        FibRunnable[] runnables = new FibRunnable[num];
        Thread[] threads = new Thread[num];
        Integer[] fibResults = new Integer[num];

        FibRunnable.setFibResults(fibResults);

        for (int i = 0; i < num; i++) {
            runnables[i] = new FibRunnable(i + 1);
            threads[i] = new Thread(runnables[i]);
            threads[i].start();
        }

        try {
            threads[num - 1].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Fibonacci sequence:");
        for (int i = 0; i < num; i++) {
            System.out.print(fibResults[i] + " ");
        }
    }
}
