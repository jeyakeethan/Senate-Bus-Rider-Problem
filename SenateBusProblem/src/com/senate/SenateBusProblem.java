package com.senate;

public class SenateBusProblem {

    private static final SharedData sharedData = new SharedData();

    public static void main(String[] args) {
        int riderArrivalMean = 30000;
        int busArrivalMean = 1200000;
        new RiderGenerator(sharedData, riderArrivalMean).start();
        new BusGenerator(sharedData, busArrivalMean).start();
    }
}
