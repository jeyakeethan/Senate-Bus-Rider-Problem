package com.senate;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BusGenerator extends Thread {
    private final int busMean;
    private final SharedData sharedData;

    public BusGenerator(SharedData sharedData, int mean) {
        this.sharedData = sharedData;
        this.busMean = mean;
    }

    @Override
    public void run() {

        long nextBusInterval = 0;
        while (true) {
            nextBusInterval = (long) (-(Math.log(Math.random())) * busMean);
            System.out.println("#Next bus arrives in " + nextBusInterval + " ms");
            try {
                // new bus generated after the nextBusInterval
                Thread.sleep(nextBusInterval);
                new Bus(sharedData).start();
            } catch (InterruptedException ex) {
                Logger.getLogger(BusGenerator.class.getName()).log(Level.SEVERE, "Unable to start new bus thread", ex);
            }
        }
    }
}
