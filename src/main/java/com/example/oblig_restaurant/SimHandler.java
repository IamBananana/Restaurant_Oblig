package com.example.oblig_restaurant;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class SimHandler implements Runnable {
    boolean gameOn = true;

    //all the objects
    public SimHandler() {
        PriorityBlockingQueue<Order> queue = new PriorityBlockingQueue<Order>(10);
        Thread chefBurger = new Thread(this);
        Thread chefPizza = new Thread(this);
        Thread chefSushi = new Thread(this);
    }
    @Override
    public void run() {
        while (gameOn) {

        }
    }
}
