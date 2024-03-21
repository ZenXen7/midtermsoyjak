package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class HelloApplication extends Application {
    public static class PerfectRunnable implements Runnable {

        int start, end, curr;
        ProgressIndicator pb;

        public PerfectRunnable(int start, int end, ProgressIndicator pb) {
            this.start = start;
            this.end = end;
            this.pb = pb;
        }


        private int isPerfect(final int n) {
            int s = IntStream.rangeClosed(1, n - 1).filter(value -> n % value == 0).sum();
            return Integer.compare(s, n);
        }

        @Override
        public void run() {
            for (curr = start; curr <= end; curr++) {
                int r = isPerfect(curr);
                double d = (curr - start) / (double) (end - start);
                pb.setProgress(d);
                synchronized (results) {
                    results[curr] = r;
                }
                if (r == 0) System.out.println(curr + " is a perfect number");
            }
        }
    }


    static int[] results;
    static Thread[] threads;
    static ProgressIndicator[] pbs;
    static Integer pending;

    @Override
    public void start(Stage stage) throws IOException {
        VBox paneRoot = new VBox();

        GridPane paneInputs = new GridPane();

        Label lblN = new Label("Enter n: ");
        TextField fieldN = new TextField("100000");

        Label lblCnt = new Label("Enter cnt: ");
        TextField fieldCnt = new TextField("3");

        Button btnGo = new Button("Go");

        paneInputs.add(lblN, 0, 0);
        paneInputs.add(fieldN, 1, 0);
        paneInputs.add(lblCnt, 0, 1);
        paneInputs.add(fieldCnt, 1, 1);
        paneInputs.add(btnGo, 0, 2, 2, 1);

        HBox paneProgress = new HBox();

        paneRoot.getChildren().addAll(paneInputs, paneProgress);

        btnGo.setOnAction(event -> {
            if (threads != null) Arrays.stream(threads).forEach(Thread::interrupt);

            int n = Integer.parseInt(fieldN.getText());
            int cnt = Integer.parseInt(fieldCnt.getText());
            pending = cnt;

            paneProgress.getChildren().clear();
            threads = new Thread[cnt];
            pbs = new ProgressIndicator[cnt];
            results = new int[n + 1];

            int start = 1;
            for (int i = 0; i < cnt - 1; i++) {
                pbs[i] = new ProgressIndicator(0);
                int count = (int) (Math.random() * (n - start));
                threads[i] = new Thread(new PerfectRunnable(start, start + count, pbs[i]));
                start += count;
            }
            pbs[cnt - 1] = new ProgressIndicator(0);
            threads[cnt - 1] = new Thread(new PerfectRunnable(start, n, pbs[cnt - 1]));

            paneProgress.getChildren().addAll(pbs);
            Arrays.stream(threads).forEach(Thread::start);

        });

        Scene scene = new Scene(paneRoot, 320, 240);
        stage.setTitle("Perfect number");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}