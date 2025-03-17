package com.example.oblig_restaurant;

import javafx.application.Platform;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RestaurantManager implements Runnable {
    private final BlockingQueue<Order> orderQueue;
    private final List<Chef> chefs;

    public RestaurantManager(BlockingQueue<Order> orderQueue, List<Chef> chefs) {
        if (orderQueue == null || chefs == null || chefs.isEmpty()) {
            throw new IllegalArgumentException("OrderQueue and chef list cannot be null or empty.");
        }
        this.orderQueue = orderQueue;
        this.chefs = chefs;
    }

    // Sjekker om det finnes en ledig kokk med riktig spesialisering for den aktuelle ordren.
    private Chef findAvailableChef(Order.Meal meal) {
        for (Chef chef : chefs) {
            if (chef.getSpecializedMeal() == meal && chef.isAvailable()) {
                return chef;
            }
        }
        return null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Order order = orderQueue.take();
                Chef availableChef = null;
                while ((availableChef = findAvailableChef(order.getMeal())) == null) {
                    Thread.sleep(500);
                }
                availableChef.assignOrder(order);
                String assignmentMsg = "Assigned order " + order + " to " + availableChef.getName();
                System.out.println(assignmentMsg);
                Platform.runLater(() -> SimulationData.orderData.add(assignmentMsg));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
