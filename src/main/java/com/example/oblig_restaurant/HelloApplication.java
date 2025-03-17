package com.example.oblig_restaurant;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.List;

public class HelloApplication extends Application {
    // Delt ordrekø med begrenset kapasitet
    private final OrderQueue orderQueue = new OrderQueue(10);

    @Override
    public void start(Stage stage) {
        final double WIDTH = Screen.getPrimary().getBounds().getWidth() * 0.70;
        final double HEIGHT = Screen.getPrimary().getBounds().getHeight() * 0.70;
        Button button = new Button("Start Simulation");

        button.setOnAction(e -> {
            // Start simuleringslogikken i en egen tråd
            new Thread(this::startSimulation).start();

            // Åpne et nytt vindu (Stage) for SceneHandler fra den kjørende JavaFX-applikasjonen
            Platform.runLater(() -> {
                SceneHandler sceneHandler = new SceneHandler();
                try {
                    sceneHandler.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            stage.close();
        });

        BorderPane root = new BorderPane();
        root.setTop(button);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Restaurant Simulation");
        stage.setScene(scene);
        stage.show();
    }

    private void startSimulation() {
        // Opprett kokker med spesialisering (bruker nå den nye Chef-versjonen)
        Chef chefSushi = new Chef("Chef Sushi", Order.Meal.SUSHI, null);
        Chef chefBurger = new Chef("Chef Burger", Order.Meal.BURGER, null);
        Chef chefPizza = new Chef("Chef Pizza", Order.Meal.PIZZA, null);

        // Legg til meldinger i SimulationData for kokker (oppdater via Platform.runLater)
        Platform.runLater(() -> {
            SimulationData.chefData.add("Chef Sushi created");
            SimulationData.chefData.add("Chef Burger created");
            SimulationData.chefData.add("Chef Pizza created");
        });

        // Start kokketrådene
        new Thread(chefSushi).start();
        new Thread(chefBurger).start();
        new Thread(chefPizza).start();

        // Opprett en liste med kokker
        List<Chef> chefList = List.of(chefSushi, chefBurger, chefPizza);

        // Opprett og start RestaurantManager for å tildele ordrer til kokkene
        RestaurantManager manager = new RestaurantManager(orderQueue.getQueue(), chefList);
        new Thread(manager).start();

        // Opprett og start kundetråder (minst 5 samtidig)
        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer("Customer " + (i + 1));
            Platform.runLater(() -> SimulationData.customerData.add("Created " + customer.toString()));
            // For demonstrasjon: bytt mellom ulike måltider
            Order.Meal meal = Order.Meal.values()[i % Order.Meal.values().length];
            Order order = new Order(meal, customer);
            try {
                orderQueue.addOrder(order);
                Platform.runLater(() -> SimulationData.orderData.add("Order added: " + order.toString()));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            new Thread(customer).start();

            // Simuler tilfeldig ankomst med forsinkelse
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
