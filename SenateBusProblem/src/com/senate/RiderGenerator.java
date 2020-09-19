package com.senate;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RiderGenerator extends Thread {
    private final int riderMean;
    private final SharedData sharedData;

    public RiderGenerator(SharedData sharedData, int mean) {
        this.sharedData = sharedData;
        this.riderMean = mean;
    }

    @Override
    public void run() {

        long nextRiderInterval = 0;
        while (true) {
            nextRiderInterval = (long) (-(Math.log(Math.random())) * riderMean);
            System.out.println("#Next rider arrives in " + nextRiderInterval + " ms");
            try {
                // new rider generated after the nextBusInterval
                Rider.sleep(nextRiderInterval);
                new Rider(sharedData).start();
            } catch (InterruptedException ex) {
                Logger.getLogger(BusGenerator.class.getName()).log(Level.SEVERE, "unable to start new Rider thread", ex);
            }
        }
    }
}
