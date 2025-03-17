package com.example.oblig_restaurant;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RestaurantManager implements Runnable {
    private final BlockingQueue<Order> orderQueue;
    private final List<Chef> chefs;

    public RestaurantManager(BlockingQueue<Order> orderQueue, List<Chef> chefs) {
        if (orderQueue == null || chefs == null || chefs.isEmpty()) {
            throw new IllegalArgumentException("OrderQueue og kokkelisten kan ikke være null eller tom.");
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
                // Hent den eldste ordren (blokkerende)
                Order order = orderQueue.take();
                Chef availableChef = null;
                // Vent til en ledig kokk med riktig spesialisering er funnet.
                while ((availableChef = findAvailableChef(order.getMeal())) == null) {
                    Thread.sleep(500);
                }
                // Tildel ordren til den ledige kokken.
                availableChef.assignOrder(order);
                System.out.println("Assigned order " + order + " to " + availableChef.getName());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
