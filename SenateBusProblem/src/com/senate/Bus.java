package com.senate;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Bus extends Thread {

    private final int SEAT_LIMIT = 50;  //maximum number of passengers allowed per bus
    private final SharedData sharedData;    // shared locks

    public Bus(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        try {
            sharedData.getMutexLock().acquire();    // acquire the riders lock to prevent new riders arrival
            System.out.format("Bus %d arrived to the stop\n", this.getId());
        } catch (InterruptedException ex) {
            Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, "unable to acquire the the riders arrival lock", ex);
        }

        // check whether more passengers to the SEAT_LIMIT
        int allowedRidersCount = Math.min(sharedData.getWaitingRidersCount().get(), SEAT_LIMIT);
        System.out.format("\n%d out of %d Riders can be boarded\n", allowedRidersCount, sharedData.getWaitingRidersCount().get());

        // This loop Notifies the allowed number of passengers who are allowed this time
        for (int i = 0; i < allowedRidersCount; i++) {
            sharedData.getBusPermit().release();      // signals each rider
            System.out.format("#Bus %d permit a Rider to get in\n", this.getId());
            try {
                sharedData.getBoardedPermit().acquire();  // Bus acquires boarded semaphore to stop more riders get in after
                sharedData.getWaitingRidersCount().decrementAndGet();
                System.out.format("Bus %d has been boarded with a Rider\n", this.getId());
            } catch (InterruptedException ex) {
                Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        sharedData.getMutexLock().release();    // Release the lock and leave the bus stop. Then the bus to depart
        System.out.format("Bus %d has departed\n\n", this.getId());
    }
}
