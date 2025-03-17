package com.example.oblig_restaurant;

import java.time.LocalTime;
import java.time.Duration;

public class Order {
    private Meal meal;
    private int preparationTime; // in seconds
    private LocalTime orderTime;
    private LocalTime startTime;
    private LocalTime completionTime;
    private StatusOrder status;
    private Customer customer;

    public enum Meal {
        SUSHI(6), BURGER(10), PIZZA(12);

        private int baseTime;
        Meal(int baseTime) {
            this.baseTime = baseTime;
        }
        public int getBaseTime() {
            return baseTime;
        }
    }
    public enum StatusOrder {
        QUEUED, IN_PROGRESS, COMPLETED
    }

    public Order(Meal meal, Customer customer) {
        if (meal == null || customer == null) {
            throw new IllegalArgumentException("Meal and customer cannot be null");
        }
        this.meal = meal;
        this.customer = customer;
        this.orderTime = LocalTime.now();
        this.status = StatusOrder.QUEUED;
        this.preparationTime = meal.getBaseTime();
    }

    public void startMakingOrder() {
        this.startTime = LocalTime.now();
        this.completionTime = startTime.plusSeconds(preparationTime);
        this.status = StatusOrder.IN_PROGRESS;
    }

    public boolean isComplete() {
        return completionTime != null && LocalTime.now().isAfter(completionTime);
    }

    public void completeOrder() {
        if (!isComplete()) {
            throw new IllegalStateException("Order is still in progress");
        }
        this.status = StatusOrder.COMPLETED;
        System.out.println("Order completed: " + meal + " for " + customer.getName());
    }

    public Customer getCustomer() {
        return customer;
    }

    public Meal getMeal() {
        return meal;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getCompletionTime() {
        return completionTime;
    }

    public StatusOrder getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order: " +
                "Meal=" + meal +
                ", prepTime=" + preparationTime +
                " sec, status=" + status +
                ", Customer=" + customer.getName();
    }
}
