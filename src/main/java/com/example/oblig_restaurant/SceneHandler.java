package com.example.oblig_restaurant;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneHandler extends Application {
    final double WIDTH = Screen.getPrimary().getBounds().getWidth() * 0.70;
    final double HEIGHT = Screen.getPrimary().getBounds().getHeight() * 0.70;

    private VBox customerVBox;
    private VBox orderVBox;
    private VBox chefVBox;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();

        customerVBox = new VBox(5);
        orderVBox = new VBox(5);
        chefVBox = new VBox(5);

        customerVBox.setStyle("-fx-border-color: black;");
        orderVBox.setStyle("-fx-border-color: black;");
        chefVBox.setStyle("-fx-border-color: black;");

        grid.add(customerVBox, 0, 0);
        grid.add(orderVBox, 1, 0);
        grid.add(chefVBox, 2, 0);
        grid.setPrefSize(WIDTH, HEIGHT);

        root.setCenter(grid);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Restaurant Simulation");
        stage.setScene(scene);
        stage.show();

        // Start en periodisk UI-oppdatering
        startUIUpdater();
    }

    private void startUIUpdater() {
        Thread uiUpdater = new Thread(() -> {
            try {
                while (true) {
                    Platform.runLater(() -> updateUI());
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        uiUpdater.setDaemon(true);
        uiUpdater.start();
    }

    // Oppdaterer visuelle elementer basert på simuleringstilstanden
    private void updateUI() {
        // For demonstrasjon legges det til en test-label.
        customerVBox.getChildren().clear();
        customerVBox.getChildren().add(new Label("Update UI with customers..."));
        orderVBox.getChildren().clear();
        orderVBox.getChildren().add(new Label("Update UI with orders..."));
        chefVBox.getChildren().clear();
        chefVBox.getChildren().add(new Label("Update UI with chefs..."));
    }
}
