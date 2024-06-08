package com.load.balancer;

import java.io.Serializable;

public class Cloudlet implements Serializable {
    private int id;
    private double length;
    private double deadline;
    private double arrivalTime;

    public Cloudlet(int id, double length, double deadline, double arrivalTime) {
        this.id = id;
        this.length = length;
        this.deadline = deadline;
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    public double getLength() {
        return length;
    }

    public double getDeadline() {
        return deadline;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }
}
