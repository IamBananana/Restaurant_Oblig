package com.example.oblig_restaurant;

import java.time.LocalTime;
import java.util.Timer;

public class Customer {
    private int id;
    private String name;
    private Order order;
    private LocalTime arrivalTime;
    private LocalTime angryTime;

    //In precentages
    private final int HAPPY_THRESH = 80;
    //private final int NORMAL_THRESH = ;
    private final int ANGRY_THRESH = 30;

    Customer(int id, String name, Order order, LocalTime arrivalTime) {
        setId(id);
        setName(name);
        setOrder(order);
        setArrivalTime(arrivalTime);
        setAngryTime();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setAngryTime() {
        this.arrivalTime = arrivalTime.plusMinutes(Math.round(Math.random() * 10));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Order getOrder() {
        return order;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalTime getAngryTime() {
        return angryTime;
    }
}
