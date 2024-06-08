package com.load.balancer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadBalancer {
    private List<VirtualM> vms;
    private List<Cloudlet> cloudlets;

    public LoadBalancer() {
        this.vms = new ArrayList<>();
        this.cloudlets = new ArrayList<>();
    }

    public void addVM(VirtualM vm) {
        vms.add(vm);
    }

    public void addCloudlet(Cloudlet cloudlet) {
        cloudlets.add(cloudlet);
    }

    public void balanceLoad() {
        for (Cloudlet cloudlet : cloudlets) {
            VirtualM selectedVM = null;
            double minCompletionTime = Double.MAX_VALUE;
// pour chaque VM, on calcule le temps d'execution du cloudlet sur cette VM
            for (VirtualM vm : vms) {
                double completionTime = cloudlet.getLength() / vm.getAvailableMips();
                if (completionTime <= cloudlet.getDeadline() && completionTime < minCompletionTime) {
                    minCompletionTime = completionTime;
                    selectedVM = vm;
                }
            }

            // If a suitable VM is found, allocate the cloudlet to it
            if (selectedVM != null) {
                selectedVM.allocateMips(cloudlet.getLength());
                System.out.println("Cloudlet " + cloudlet.getId() + " asigned to VM " + selectedVM.getId());
            } else {
                System.out.println("Cloudlet " + cloudlet.getId() + " could not be asigned to any VM.");
            }
        }
    }

    public static void main(String[] args) {
        LoadBalancer lb = new LoadBalancer();

        // Create VMs : hna ratzid ga3 les Vm li 3ndk
        lb.addVM(new VirtualM(1, 1000));
        lb.addVM(new VirtualM(2, 2000));


        Random rand = new Random();
        for (int i = 1; i <= 10; i++) {
            double length = rand.nextDouble() * 1000;
            double deadline = rand.nextDouble() * 10;
            lb.addCloudlet(new Cloudlet(i, length, deadline, rand.nextDouble() * 10));
        }

        // Balance the load , hna t3awed les cloudlets 3la les VMs
        lb.balanceLoad();
    }
}
