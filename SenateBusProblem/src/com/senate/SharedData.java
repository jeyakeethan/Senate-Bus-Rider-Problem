package com.senate;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedData {
    private final AtomicInteger waitingRidersCount = new AtomicInteger(0);    //the number of riders in the waiting queue
    private final Semaphore mutexLock = new Semaphore(1);   //protects   waitingRidersCount, so that it controls access to passenger to get onboard
    private final Semaphore busPermit = new Semaphore(0);     // number of passengers permitted to the bus
    private final Semaphore boardedPermit = new Semaphore(0);  // number of passengers permitted to get boarded

    public AtomicInteger getWaitingRidersCount() {
        return waitingRidersCount;
    }

    public Semaphore getMutexLock() {
        return mutexLock;
    }

    public Semaphore getBusPermit() {
        return busPermit;
    }

    public Semaphore getBoardedPermit() {
        return boardedPermit;
    }
}
