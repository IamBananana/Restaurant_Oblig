package com.example.oblig_restaurant;

import java.time.Duration;
import java.time.LocalTime;
import javafx.application.Platform;

public class Customer implements Runnable {
    private String name;
    private LocalTime arrivalTime;
    private LocalTime maxWaitTime;
    private Status status;

    // Terskler i prosent
    private final int HAPPY_THRESH = 0;
    private final int NORMAL_THRESH = 33;
    private final int ANGRY_THRESH = 67;

    // Maks ventetid i sekunder
    private final int MAX_WAIT_SECONDS = 20;

    public enum Status {
        HAPPY,
        NORMAL,
        SERVED,  // Bestillingen er mottatt
        ANGRY,
        LEFT
    }

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

    public int checkProgress() {
        Duration waited = Duration.between(arrivalTime, LocalTime.now());
        Duration maxWait = Duration.between(arrivalTime, maxWaitTime);
        return (int)(waited.toMillis() * 100 / maxWait.toMillis());
    }

    public void updateStatus() {
        // Ikke oppdater hvis kunden allerede er servert eller har forlatt
        if(getStatus() == Status.SERVED || getStatus() == Status.LEFT) {
            return;
        }
        int progress = checkProgress();
        if(progress >= ANGRY_THRESH) {
            setStatus(Status.ANGRY);
            System.out.println(name + " got angry and left!");
        } else if(progress >= NORMAL_THRESH) {
            setStatus(Status.NORMAL);
        } else {
            setStatus(Status.HAPPY);
        }
    }

    // Metode for å motta bestillingen
    public void receiveOrder() {
        setStatus(Status.SERVED);
        System.out.println(name + " has received the order and is leaving happily!");
    }

    @Override
    public void run() {
        // Oppdater kundens status hvert sekund inntil den er servert eller blir sint
        while(getStatus() != Status.ANGRY && getStatus() != Status.SERVED) {
            updateStatus();
            Platform.runLater(() -> {
                // Fjern gamle oppføringer og legg til oppdatert status
                SimulationData.customerData.removeIf(s -> s.contains(name));
                SimulationData.customerData.add(toString());
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        // Sluttoppdatering når kunden er ferdig (enten servert eller har forlatt)
        Platform.runLater(() -> {
            SimulationData.customerData.removeIf(s -> s.contains(name));
            SimulationData.customerData.add(name + " (" + status + ") has left.");
        });
    }

    @Override
    public String toString(){
        return name + " (" + status + ", " + checkProgress() + "% waited)";
    }
}