package com.example.oblig_restaurant;

import java.util.concurrent.BlockingQueue;

public class Chef implements Runnable {
    private final String name;
    private final Order.Meal specializedMeal;
    private final BlockingQueue<Order> orderQueue;
    // Delay between orders (in seconds)
    private final int delayBetweenOrders = 5;

    public Chef(String name, Order.Meal specializedMeal, BlockingQueue<Order> orderQueue) {
        if (name == null || specializedMeal == null || orderQueue == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        this.name = name;
        this.specializedMeal = specializedMeal;
        this.orderQueue = orderQueue;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Take an order from the queue (blocking)
                Order order = orderQueue.take();

                // If order doesn't match this chef’s specialization, put it back and try later
                if (order.getMeal() != this.specializedMeal) {
                    orderQueue.put(order);
                    Thread.sleep(1000); // short wait before trying again
                    continue;
                }
                prepareOrder(order);
                Thread.sleep(delayBetweenOrders * 1000L); // Delay between orders for this chef
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Chef " + name + " interrupted.");
                break;
            }
        }
    }

    private void prepareOrder(Order order) {
        System.out.println("Chef " + name + " (specializing in " + specializedMeal + ") is preparing "
                + order.getMeal() + " for " + order.getCustomer().getName());
        order.startMakingOrder();
        try {
            // Simulate preparation time (baseTime in seconds)
            Thread.sleep(order.getPreparationTime() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Preparation by Chef " + name + " was interrupted.");
        }
        order.completeOrder();
    }
}
