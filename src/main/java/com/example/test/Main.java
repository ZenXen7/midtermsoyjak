package com.example.test;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static String PASSWORD;
    static boolean isFound = false;

    public static class PasswordRunnable implements Runnable {

        int index;

        char vowel;
        int id;

        public PasswordRunnable(int id, int index, char vowel) {
            this.id = id;
            this.index = index;
            this.vowel = vowel;
        }

        public void soyjak(StringBuilder sb, int curr) {
            if (isFound) return;
            if (curr == PASSWORD.length()) {
                System.out.println("Thread " + String.format("%02d", id) + ": " + sb.toString());
                if (PASSWORD.equals(sb.toString())) {
                    isFound = true;
                    System.out.println("Thread " + id + " found the password, The password is: " + sb.toString());
                }
                return;
            }

            if (index == curr) {
                sb.append(vowel);
                soyjak(sb, curr + 1);
                sb.deleteCharAt(sb.length() - 1);
            } else {
                for (char i = 'a'; i <= 'z'; i++) {
                    sb.append(i);
                    soyjak(sb, curr + 1);
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }

        @Override
        public void run() {
            soyjak(new StringBuilder(), 0);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter password: ");
        PASSWORD = sc.nextLine();
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        Thread[] threads = new Thread[PASSWORD.length() * 5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < PASSWORD.length(); j++) {
                threads[i * PASSWORD.length() + j] = new Thread(new PasswordRunnable(i * PASSWORD.length() + j, j, vowels[i]));
            }
        }
        Arrays.stream(threads).forEach(Thread::run);
        
    }
}
