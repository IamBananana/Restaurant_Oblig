package com.example.oblig_restaurant;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class RestaurantManager implements Runnable {
    private final BlockingQueue<Order> orderQueue;
    private final List<Chef> chefList;

    public RestaurantManager(BlockingQueue<Order> orderQueue, List<Chef> chefList) {
        this.orderQueue = orderQueue;
        this.chefList = chefList;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Order order = orderQueue.take();
                Chef selectedChef = null;
                // Først, prøv å finne en kokk med riktig spesialisering
                for (Chef chef : chefList) {
                    if (chef.getSpecializedMeal() == order.getMeal() && chef.isAvailable()) {
                        selectedChef = chef;
                        break;
                    }
                }
                // Om ingen spesialisert kokk er tilgjengelig, bruk en hvilken som helst ledig kokk
                if (selectedChef == null) {
                    for (Chef chef : chefList) {
                        if (chef.isAvailable()) {
                            selectedChef = chef;
                            break;
                        }
                    }
                }
                // Om ingen kokk er ledig, vent litt og legg ordren tilbake i køen
                if (selectedChef == null) {
                    Thread.sleep(1000);
                    orderQueue.put(order);
                    continue;
                }
                selectedChef.assignOrder(order);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
