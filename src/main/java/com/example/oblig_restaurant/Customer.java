package com.example.oblig_restaurant;

import java.time.Duration;
import java.time.LocalTime;

public class Customer implements Runnable {
    private String name;
    private LocalTime arrivalTime;
    private LocalTime maxWaitTime; // The time by which the customer must have been served.
    private Status status;

    // Thresholds in percentages
    private final int HAPPY_THRESH = 50;   // 0-50% waiting time: Happy
    private final int NORMAL_THRESH = 80;  // 50-80% waiting time: Normal
    private final int ANGRY_THRESH = 100;  // 100% waiting time: Angry (leaves)

    // Maximum waiting time in seconds (this can be parameterized)
    private final int MAX_WAIT_SECONDS = 120;

    public enum Status {
        HAPPY,
        NORMAL,
        ANGRY,
        LEFT  // if the customer leaves after waiting too long
    }

    // The arrival time is when the customer is created
    public Customer(String name) {
        setName(name);
        setArrivalTime(LocalTime.now());
        setMaxWaitTime(MAX_WAIT_SECONDS);
        setStatus(Status.HAPPY);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.name = name;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        if (arrivalTime == null) {
            throw new IllegalArgumentException("ArrivalTime cannot be null");
        }
        this.arrivalTime = arrivalTime;
    }

    public void setMaxWaitTime(int seconds) {
        if (seconds <= 0) {
            throw new IllegalArgumentException("Max wait time must be positive");
        }
        this.maxWaitTime = arrivalTime.plusSeconds(seconds);
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns the percentage of the maximum wait time that has elapsed.
     */
    public int checkProgress() {
        Duration waited = Duration.between(arrivalTime, LocalTime.now());
        Duration maxWait = Duration.between(arrivalTime, maxWaitTime);
        return (int)(waited.toMillis() * 100 / maxWait.toMillis());
    }

    public void updateStatus() {
        int progress = checkProgress();
        if(progress >= ANGRY_THRESH) {
            setStatus(Status.ANGRY);
            System.out.println(name + " got angry and left!");
            // Here you can trigger additional cleanup or removal from UI simulation
        } else if(progress >= NORMAL_THRESH) {
            setStatus(Status.NORMAL);
        } else {
            setStatus(Status.HAPPY);
        }
    }

    @Override
    public void run() {
        // Customer thread keeps checking status until they get angry (or order is served)
        while(getStatus() != Status.ANGRY) {
            updateStatus();
            try {
                Thread.sleep(1000); // Check every second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public String toString(){
        return name + " (" + status + ", " + checkProgress() + "% waited)";
    }
}
