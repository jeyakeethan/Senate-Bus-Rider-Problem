package com.senate;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Rider extends Thread {
    private final SharedData sharedData;

    public Rider(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        try {
            // new rider tries to come, check for whether the lock available
            sharedData.getMutexLock().acquire();
            System.out.format("Rider %d has been to the waiting queue\n", this.getId());

        } catch (InterruptedException ex) {
            Logger.getLogger(Rider.class.getName()).log(Level.SEVERE, "Rider has not been to the queue since a bus had arrived", ex);
        }

        sharedData.getWaitingRidersCount().incrementAndGet();
        sharedData.getMutexLock().release();    // rider release lock after got to the queue

        System.out.format("Rider %d has released the lock\n", this.getId());

        try {
            sharedData.getBusPermit().acquire();  // rider try to acquire the bus lock to get in
            System.out.format("Rider %d has been allowed to get into a Bus\n", this.getId());

        } catch (InterruptedException ex) {
            Logger.getLogger(Rider.class.getName()).log(Level.SEVERE, "Unable to be boarded no bus or seat limit exceeded", ex);
        }

        sharedData.getBoardedPermit().release();   // rider wishes to board in the bus
        System.out.format("Rider %d requested to board the Bus\n", this.getId());
    }
}
