package com.example.oblig_restaurant;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;

public class Order {
    private Meal meal;
    private int preparationTime; // in seconds
    private LocalTime orderTime;
    private LocalTime startTime;
    private LocalTime completionTime;
    private StatusOrder status;

    public enum Meal {
        SUSHI(600), BURGER(660), PIZZA(720);

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

    public Order(Meal meal) {
        if (meal == null) throw new IllegalArgumentException("meal is null");
        this.meal = meal;
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
        if (!isComplete()) throw new IllegalStateException("Order is in progress");
        this.status = StatusOrder.COMPLETED;
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
                "meal = " + meal +
                ", preparationTime=" + preparationTime +
                ", status=" + status;
    }
}
