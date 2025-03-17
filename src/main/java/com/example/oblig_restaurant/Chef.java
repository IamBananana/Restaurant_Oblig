package com.example.oblig_restaurant;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Chef implements Runnable {
    private final String name;
    private final Order.Meal specializedMeal;
    // Personlig kø for å motta én ordre om gangen
    private final BlockingQueue<Order> personalQueue = new ArrayBlockingQueue<>(1);
    // Forsinkelse mellom ordrer (i sekunder)
    private final int delayBetweenOrders = 5;

    public Chef(String name, Order.Meal specializedMeal, BlockingQueue<Order> globalQueue) {
        if (name == null || specializedMeal == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        this.name = name;
        this.specializedMeal = specializedMeal;
    }

    public String getName() {
        return name;
    }

    public Order.Meal getSpecializedMeal() {
        return specializedMeal;
    }

    // Indikerer at kokken er ledig dersom den personlige køen er tom.
    public boolean isAvailable() {
        return personalQueue.isEmpty();
    }

    // Blokkerende kall for å motta en tildelt ordre.
    public void assignOrder(Order order) throws InterruptedException {
        personalQueue.put(order);
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Vent på at en ordre skal tildeles
                Order order = personalQueue.take();
                prepareOrder(order);
                Thread.sleep(delayBetweenOrders * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Chef " + name + " ble avbrutt.");
                break;
            }
        }
    }

    private void prepareOrder(Order order) {
        String message = "Chef " + name + " (spesialisert på " + specializedMeal + ") er i gang med "
                + order.getMeal() + " for " + order.getCustomer().getName();
        System.out.println(message);
        order.startMakingOrder();
        try {
            Thread.sleep(order.getPreparationTime() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Tilberedningen av " + order.getMeal() + " av " + name + " ble avbrutt.");
        }
        order.completeOrder();
        String completeMessage = "Order completed: " + order.getMeal() + " for " + order.getCustomer().getName();
        System.out.println(completeMessage);
    }
}
