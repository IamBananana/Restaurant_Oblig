package com.example.oblig_restaurant;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneHandler extends Application {
    final double WIDTH = Screen.getPrimary().getBounds().getWidth() * 0.70;
    final double HEIGHT = Screen.getPrimary().getBounds().getHeight() * 0.70;

    private ListView<String> customerListView;
    private ListView<String> orderListView;
    private ListView<String> chefListView;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Toppseksjon med tittel
        Label title = new Label("Restaurant Simulation");
        title.setStyle("-fx-font-size: 24px; -fx-padding: 10px;");
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        // Kunder-seksjon
        VBox customerBox = new VBox(5);
        Label customerLabel = new Label("Kunder");
        customerLabel.setStyle("-fx-font-size: 18px; -fx-underline: true;");
        customerListView = new ListView<>(SimulationData.customerData);
        customerListView.setPrefHeight(500);
        customerBox.getChildren().addAll(customerLabel, customerListView);

        // Bestillinger-seksjon
        VBox orderBox = new VBox(5);
        Label orderLabel = new Label("Bestillinger");
        orderLabel.setStyle("-fx-font-size: 18px; -fx-underline: true;");
        orderListView = new ListView<>(SimulationData.orderData);
        orderListView.setPrefHeight(500);
        orderBox.getChildren().addAll(orderLabel, orderListView);

        // Kokker-seksjon
        VBox chefBox = new VBox(5);
        Label chefLabel = new Label("Kokker");
        chefLabel.setStyle("-fx-font-size: 18px; -fx-underline: true;");
        chefListView = new ListView<>(SimulationData.chefData);
        chefListView.setPrefHeight(500);
        chefBox.getChildren().addAll(chefLabel, chefListView);

        // Plasser seksjonene side om sin
        root.setLeft(customerBox);
        root.setCenter(orderBox);
        root.setRight(chefBox);

        // Bunnseksjon med stoppknapp
        Button stopButton = new Button("Stop Simulation");
        stopButton.setStyle("-fx-font-size: 16px;");
        stopButton.setOnAction(e -> Platform.exit());
        BorderPane.setMargin(stopButton, new Insets(10));
        root.setBottom(stopButton);
        BorderPane.setAlignment(stopButton, Pos.CENTER);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Restaurant Simulation");
        stage.setScene(scene);
        stage.show();
    }
}
