package com.example.oblig_restaurant;

import java.time.Duration;
import java.time.LocalTime;

public class Customer {
    private int id;
    private String name;
    private Order order;
    private LocalTime arrivalTime;
    private LocalTime angryTime;
    private long exitTime;
    private Status status;

    //In precentages
    private final int HAPPY_THRESH = 80;
    //private final int NORMAL_THRESH = ;
    private final int ANGRY_THRESH = 30;
    private final int DEFAULT_VARIANCE = 10;

    public enum Status{
        HAPPY,
        NORMAL,
        ANGRY
    }

    //The arrival time is when the customer is created
    Customer(int id, String name, Order order) {
        setId(id);
        setName(name);
        setOrder(order);
        setArrivalTime(LocalTime.now());
        setAngryTime(DEFAULT_VARIANCE);
        setStatus(Status.HAPPY);
    }

    public void setId(int id) {
        if (id < 0) { throw new IllegalArgumentException("id cannot be negative"); }
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) { throw new IllegalArgumentException("Name cannot be blank"); }
        this.name = name;
    }

    public void setOrder(Order order) {
        if (order == null) { throw new IllegalArgumentException("Order cannot be null"); }
        this.order = order;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        if (arrivalTime == null) { throw new IllegalArgumentException("ArrivalTime cannot be null"); }
        this.arrivalTime = arrivalTime;
    }

    public void setAngryTime(int variance) {
        if (variance < 0) { throw new IllegalArgumentException("variance cannot be negative"); }
        //Random number between 0 and VARIANCE
        angryTime = LocalTime.now().plusSeconds(variance);
        exitTime = Duration.between(arrivalTime ,angryTime).toMinutes();
    }

    public void setStatus(Status status) {
        if (status == null) { throw new IllegalArgumentException("Status cannot be null"); }
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void updateStatus() {
        long timeLeft = Duration.between(arrivalTime, LocalTime.now()).toMinutes();

        if(timeLeft%exitTime < ANGRY_THRESH) setStatus(Status.ANGRY);
        else if(timeLeft%exitTime < HAPPY_THRESH) setStatus(Status.NORMAL);
    }

    /**
     *
     * @return Returns how long it is left before the customer gets angry/leaves in precentages (int)
     */
    public int checkProgress() {
        //Return precentage of total progress since the order was made.

        long timeLeft = Duration.between(arrivalTime, LocalTime.now()).toMinutes();
        return (int) ((timeLeft/exitTime)/100);
    }

    public String toString(){
        return "Not implemented yet";
    }
}
