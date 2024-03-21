package com.example.test;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class PerfectNumberController extends Application {

    @FXML
    private TextArea perfectNumbersTextArea;
    @FXML
    private Label lessThanPerfectLabel;
    @FXML
    private Label moreThanPerfectLabel;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Perfect Number Finder");

        Label numLabel = new Label("Enter a number:");
        TextField numTextField = new TextField();
        Label cntLabel = new Label("Enter number of threads:");
        TextField cntTextField = new TextField();
        Button findButton = new Button("Find Perfect Numbers");

        perfectNumbersTextArea = new TextArea();
        perfectNumbersTextArea.setEditable(false);
        lessThanPerfectLabel = new Label();
        moreThanPerfectLabel = new Label();

        VBox progressBarsContainer = new VBox(5);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                numLabel, numTextField,
                cntLabel, cntTextField,
                findButton,
                new Label("Perfect Numbers:"), perfectNumbersTextArea,
                lessThanPerfectLabel,
                moreThanPerfectLabel,
                progressBarsContainer
        );
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400   , 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        findButton.setOnAction(event -> {
            perfectNumbersTextArea.clear();
            lessThanPerfectLabel.setText("");
            moreThanPerfectLabel.setText("");
            progressBarsContainer.getChildren().clear();

            int num;
            int cnt;
            try {
                num = Integer.parseInt(numTextField.getText());
                cnt = Integer.parseInt(cntTextField.getText());
            } catch (NumberFormatException e) {
                errorJak("Please enter valid numbers.");
                return;
            }

            if (num <= 0 || cnt <= 0) {
                errorJak("Please enter positive numbers.");
                return;
            }

            findPerfectNumbers(num, cnt, progressBarsContainer);
        });
    }

    private boolean isPerfect(int n) {

        if(n == 1){
            return false;
        }
        int sum = 1;
        for(int i = 2; i < n; i++){
            if(n % i == 0){
                sum+=i;
            }
        }

        return sum == n;

    }

    private void calculateJak(int num) {

        int lessThanPerfectCount = 0;

        int moreThanPerfectCount = 0;

        for (int i = 1; i <= num; i++) {
            int sum = 0;
            for (int j = 1; j < i; j++) {
                if (i % j == 0) {
                    sum += j;
                }
            }
            if (sum < i) {
                lessThanPerfectCount++;
            } else if (sum > i) {
                moreThanPerfectCount++;
            }
        }
        lessThanPerfectLabel.setText("Less than perfect: " + lessThanPerfectCount);
        moreThanPerfectLabel.setText("More than perfect: " + moreThanPerfectCount);
    }

    private void errorJak(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void findPerfectNumbers(int num, int cnt, VBox progressBarsContainer) {
        List<Thread> threads = new ArrayList<>();
        List<Task<Void>> tasks = new ArrayList<>();
        int numbersPerThread = num / cnt;

        for (int i = 0; i < cnt; i++) {
            final int start = i * numbersPerThread + 1;
            final int end;

            if (i == cnt - 1) {
                end = num;
            } else {
                end = (i + 1) * numbersPerThread;
            }

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    for (int n = start; n <= end; n++) {
                        if (isPerfect(n)) {
                            perfectNumbersTextArea.appendText(n + " ");
                            System.out.println(n);
                        }

                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        updateProgress((double) (n - start) / (end - start), 1);
                    }
                    return null;
                }
            };
            task.messageProperty().addListener((observable, oldValue, newValue) -> {
                perfectNumbersTextArea.appendText(newValue);
            });

            ProgressIndicator progressIndicator = new ProgressIndicator();
            VBox vbox = new VBox(progressIndicator);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(5));
            progressBarsContainer.getChildren().add(vbox);

            progressIndicator.progressProperty().bind(task.progressProperty());

            tasks.add(task);
            threads.add(new Thread(task));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Task<Void> task : tasks) {
            task.setOnSucceeded(event -> {
                boolean allThreadsDone = true;
                for (Task<Void> t : tasks) {
                    if (!t.isDone()) {
                        allThreadsDone = false;
                        break;
                    }
                }
                if (allThreadsDone) {
                    calculateJak(num);
                }
            });
        }
    }




    public static void main(String[] args) {
        launch(args);
    }


    public void findPerfectNumbers(ActionEvent actionEvent) {
    }
}
