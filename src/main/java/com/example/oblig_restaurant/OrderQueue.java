package com.example.oblig_restaurant;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class OrderQueue {
    private final BlockingQueue<Order> orders;

    public OrderQueue(int maxSize) {
        // ArrayBlockingQueue enforces a maximum number of orders in the queue.
        this.orders = new ArrayBlockingQueue<>(maxSize);
    }

    public void addOrder(Order order) throws InterruptedException {
        orders.put(order); // Blocks if the queue is full
        System.out.println("Order added: " + order);
    }

    public BlockingQueue<Order> getQueue() {
        return orders;
    }
}
