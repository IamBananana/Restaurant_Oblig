package com.example.oblig_restaurant;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class SimHandler implements Runnable {
    boolean gameOn = true;

    //all the objects
    public SimHandler() {
        OrderQueue orderQueue = new OrderQueue(10); // Max 5 orders in the queue

        // Create chefs
        Chef chef1 = new Chef(Order.Meal.BURGER., orderQueue);
        Chef chef2 = new Chef(Order.Meal.SUSHI, "MealType2", orderQueue);

        // Create customers (some VIP, some non-VIP)
        Customer customer1 = new Customer("Alice", 10, orderQueue, false);
        Customer customer2 = new Customer("Bob", 15, orderQueue, true); // VIP
        Customer customer3 = new Customer("Charlie", 20, orderQueue, false);

        // Start threads

    }
    //Methods here to manage queue

    //Process orders

    //Process cooking
}
