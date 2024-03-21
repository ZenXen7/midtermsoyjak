package com.example.test;

public class FibRunnable implements Runnable{
    private int n;
    private static Integer[] fibResults;

    public FibRunnable(int n) {
        this.n = n;
    }

    public static void setFibResults(Integer[] results) {
        fibResults = results;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            if (n == 1) {
                fibResults[0] = 0;
            } else if (n == 2) {
                fibResults[1] = 1;
            } else {
                synchronized (fibResults) {
                    while (fibResults[n - 2] == null || fibResults[n - 3] == null) {
                        fibResults.wait();
                    }
                    fibResults[n - 1] = fibResults[n - 2] + fibResults[n - 3];
                }
            }
            synchronized (fibResults) {
                fibResults.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
