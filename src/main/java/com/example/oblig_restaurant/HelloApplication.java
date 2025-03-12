package com.example.oblig_restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        final double WIDTH = Screen.getPrimary().getBounds().getWidth()*0.70;
        final double HEIGHT = Screen.getPrimary().getBounds().getHeight()*0.70;
        Button button = new Button("Start Simulation");

        button.setOnAction(e -> {
            try {
                new SceneHandler().start(new Stage());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            stage.close();
        });

        BorderPane root = new BorderPane();
        root.setTop(button);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Restaurant Simulation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}