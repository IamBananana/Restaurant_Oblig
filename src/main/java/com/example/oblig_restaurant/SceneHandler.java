package com.example.oblig_restaurant;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneHandler extends Application {
    final double WIDTH = Screen.getPrimary().getBounds().getWidth()*0.70;
    final double HEIGHT = Screen.getPrimary().getBounds().getHeight()*0.70;
    private int customerCount = 0;
    private int cookCount = 0;
    private int orderCount = 0;
    BorderPane root;
    VBox leftVBucks;
    VBox middleVBucks;
    VBox rightVBucks;

    @Override
    public void start(Stage stage) throws Exception {
        initializeGrid();
        Button button = new Button("tester");
        button.setOnAction(e -> testo());
        leftVBucks.getChildren().add(button);
        System.out.println("This ran");
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Restaurant Simulation Started");
        stage.setScene(scene);
        stage.show();
    }
    private void initializeGrid() {
        root = new BorderPane();
        GridPane grid = new GridPane();
        leftVBucks = new VBox();
        middleVBucks = new VBox();
        rightVBucks = new VBox();
        leftVBucks.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        middleVBucks.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        rightVBucks.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        leftVBucks.setPrefSize(WIDTH / 3, HEIGHT);
        middleVBucks.setPrefSize(WIDTH / 3, HEIGHT);
        rightVBucks.setPrefSize(WIDTH / 3, HEIGHT);

        VBox.setVgrow(leftVBucks, Priority.ALWAYS);
        VBox.setVgrow(middleVBucks, Priority.ALWAYS);
        VBox.setVgrow(rightVBucks, Priority.ALWAYS);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(33.3);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(33.3);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(33.3);

        grid.getColumnConstraints().addAll(col1, col2, col3);

        grid.add(leftVBucks, 0, 0);
        grid.add(middleVBucks, 1, 0);
        grid.add(rightVBucks, 2, 0);

        grid.setPrefSize(WIDTH, HEIGHT);

        root.setCenter(grid);
    }
    private void testo() {
        Label label = new Label("This is a test");
        leftVBucks.getChildren().add(label);
    }
    private void updateGrids(){
        updateCustomers();
        updateOrders();
        updateCooks();
    }

    private void updateCooks() {
    }

    private void updateOrders() {
    }

    private void updateCustomers() {
        if (customerCount == leftVBucks.getChildren().size()) return;

    }
}
