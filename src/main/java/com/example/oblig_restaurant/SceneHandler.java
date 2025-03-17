package com.example.oblig_restaurant;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

        customerListView = new ListView<>(SimulationData.customerData);
        orderListView = new ListView<>(SimulationData.orderData);
        chefListView = new ListView<>(SimulationData.chefData);

        HBox hbox = new HBox(10, customerListView, orderListView, chefListView);
        root.setCenter(hbox);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Restaurant Simulation");
        stage.setScene(scene);
        stage.show();
    }
}
