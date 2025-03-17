package com.example.oblig_restaurant;

import java.util.concurrent.BlockingQueue;

public class Chef implements Runnable {
    private final Order.Meal specializedMeal;
    private final BlockingQueue<Order> orderQueue;

    public Chef(Order.Meal specializedMeal, BlockingQueue<Order> orderQueue)  {
        if (specializedMeal == null || orderQueue == null) {
            throw new IllegalArgumentException("Specialized meal and order queue cannot be null");
        }
        this.specializedMeal = specializedMeal;
        this.orderQueue = orderQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Take an order from the priority queue
                Order order = orderQueue.take();

                // Check if the order matches the chef's specialization
                if (order.getMeal() == this.specializedMeal) {
                    prepareOrder(order);
                } else {
                    // Re-add the order to the queue if it doesn't match
                    orderQueue.put(order);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Chef interrupted while waiting for orders.");
                break;
            }
        }
    }

    private void prepareOrder(Order order) {
        System.out.println("Chef specializing in " + specializedMeal + " is preparing the order: " + order.getMeal() + " for " + order.getCustomer().getName());
        try {
            // Simulate preparation time using the meal's baseTime
            Thread.sleep(order.getPreparationTime() * 1000L); // Convert seconds to milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Preparation was interrupted.");
        }
        order.completeOrder();
        System.out.println("Order completed: " + order.getMeal() + " for " + order.getCustomer().getName());
    }
}