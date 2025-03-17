package com.example.oblig_restaurant;

import com.example.oblig_restaurant.Order;

import java.util.concurrent.PriorityBlockingQueue;

public class OrderQueue {
    private PriorityBlockingQueue<Order> orders;

    public OrderQueue(int maxSize) {
        this.orders = new PriorityBlockingQueue<>(maxSize);
    }

    public void addOrder(Order order) {
        orders.put(order); // Thread-safe add operation
        System.out.println("Order added: " + order);
    }

    public Order getNextOrder(Chef chef) {
        try {
            Order order = orders.take(); // Thread-safe take operation (blocks if queue is empty)
            System.out.println(chef.getName() + " is processing: " + order);
            return order;
        } catch (InterruptedException e) {
            System.out.println(chef.getName() + " was interrupted while waiting for an order.");
            return null;
        }
    }
}