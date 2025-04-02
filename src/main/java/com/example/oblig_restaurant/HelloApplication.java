package com.example.oblig_restaurant;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.List;

public class HelloApplication extends javafx.application.Application {
    // Endret: Ordrekø med maks 5 bestillinger om gangen
    private final OrderQueue orderQueue = new OrderQueue(5);
    private int customerCount = 0;

    @Override
    public void start(Stage stage) {
        final double WIDTH = Screen.getPrimary().getBounds().getWidth() * 0.70;
        final double HEIGHT = Screen.getPrimary().getBounds().getHeight() * 0.70;
        Button button = new Button("Start Simulation");

        button.setOnAction(e -> {
            // Start simuleringslogikken i en egen tråd
            new Thread(this::startSimulation).start();

            // Åpne et nytt vindu for det forbedrede brukergrensesnittet
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
        // Opprett kokker med spesialisering og send med den globale køen
        Chef chefSushi = new Chef("Chef Sushi", Order.Meal.SUSHI, orderQueue.getQueue());
        Chef chefBurger = new Chef("Chef Burger", Order.Meal.BURGER, orderQueue.getQueue());
        Chef chefPizza = new Chef("Chef Pizza", Order.Meal.PIZZA, orderQueue.getQueue());

        Platform.runLater(() -> {
            SimulationData.chefData.add("Chef Sushi created");
            SimulationData.chefData.add("Chef Burger created");
            SimulationData.chefData.add("Chef Pizza created");
        });

        new Thread(chefSushi).start();
        new Thread(chefBurger).start();
        new Thread(chefPizza).start();

        // Opprett en liste med kokker
        List<Chef> chefList = List.of(chefSushi, chefBurger, chefPizza);

        // Start RestaurantManager for å tildele ordrer til kokkene
        RestaurantManager manager = new RestaurantManager(orderQueue.getQueue(), chefList);
        new Thread(manager).start();

        // Generering av kunder og ordre (kan ha variabel ankomsthastighet, se tidligere forslag)
        while (true) {
            customerCount++;
            Customer customer = new Customer("Customer " + customerCount);
            Platform.runLater(() -> SimulationData.customerData.add("Created " + customer.toString()));
            Order.Meal meal = Order.Meal.values()[customerCount % Order.Meal.values().length];
            Order order = new Order(meal, customer);
            try {
                orderQueue.addOrder(order);
                Platform.runLater(() -> SimulationData.orderData.add("Order added: " + order.toString()));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
            new Thread(customer).start();

            try {
                Thread.sleep((long)(Math.random() * 7000) + 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
