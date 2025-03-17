package com.example.oblig_restaurant;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationDemo extends Application {

    @Override
    public void start(Stage stage) {
        ListView<String> logListView = new ListView<>();
        VBox root = new VBox(logListView);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Restaurant Simulation Animation");
        stage.setScene(scene);
        stage.show();

        // Timeline for å legge til en ny loggmelding hvert sekund (10 meldinger totalt)
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            String newLog = "Ny loggmelding: " + System.currentTimeMillis();
            logListView.getItems().add(newLog);
            // Animerer hele ListView-et med en kort fade-effekt
            FadeTransition ft = new FadeTransition(Duration.millis(500), logListView);
            ft.setFromValue(0.5);
            ft.setToValue(1.0);
            ft.play();
        }));
        timeline.setCycleCount(10);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
