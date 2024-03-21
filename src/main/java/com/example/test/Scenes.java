package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;

public class Scenes extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Group root = new Group();
        Scene scene = new Scene(root, 600, 600, Color.LIGHTSKYBLUE);
        Stage stage = new Stage();

        Text text = new Text();
        text.setText("Penis!");
        text.setFont(Font.font("Verdana", 50));
        text.setX(50);
        text.setY(50);
        text.setFill(Color.LIMEGREEN);

        Line line = new Line();
        line.setStartX(500);
        line.setStartY(600);
        line.setEndX(500);
        line.setEndY(200);
        line.setStrokeWidth(5);
        line.setStroke(Color.BLACK);


        Line line2 = new Line();
        line2.setStartX(200);
        line2.setStartY(800);
        line2.setEndX(500);
        line2.setEndY(800);
        line2.setStrokeWidth(5);
        line2.setStroke(Color.BLACK);



        Rectangle rectangle = new Rectangle();
        rectangle.setX(100);
        rectangle.setY(100);
        rectangle.setWidth(100);
        rectangle.setHeight(100);
        rectangle.setFill(Color.BLUE);
        rectangle.setStrokeWidth(5);
        rectangle.setStroke(Color.WHITE);


        root.getChildren().add(text);
        root.getChildren().add(line);
        root.getChildren().add(line2);
        root.getChildren().add(rectangle);
        stage.setScene(scene);
        stage.show();
    }
}