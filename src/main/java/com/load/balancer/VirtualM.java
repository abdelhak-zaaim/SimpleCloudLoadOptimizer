package com.load.balancer;


public class VirtualM {
    private int id;
    private double mips; // Million Instructions Per Second
    private double availableMips;

    public VirtualM(int id, double mips) {
        this.id = id;
        this.mips = mips;
        this.availableMips = mips;
    }

    public int getId() {
        return id;
    }

    public double getMips() {
        return mips;
    }

    public double getAvailableMips() {
        return availableMips;
    }

    public void allocateMips(double mips) {
        this.availableMips -= mips;
    }

    public void releaseMips(double mips) {
        this.availableMips += mips;
    }

    public boolean canHandleCloudlet(Cloudlet cloudlet) {
        double completionTime = cloudlet.getLength() / mips;
        return completionTime <= cloudlet.getDeadline();
    }
}
