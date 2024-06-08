package com.load.balancer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LoadBalancer {
    private List<VirtualM> vms;
    private List<Cloudlet> cloudlets;
    private ServerSocket serverSocket;

    public LoadBalancer(int port) throws IOException {
        this.vms = new ArrayList<>();
        this.cloudlets = new ArrayList<>();
        this.serverSocket = new ServerSocket(port);
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
                System.out.println("Cloudlet " + cloudlet.getId() + " assigned to VM " + selectedVM.getId());
            } else {
                System.out.println("Cloudlet " + cloudlet.getId() + " could not be assigned to any VM.");
            }
        }
    }

    public void start() throws IOException {
        System.out.println("LoadBalancer is listening on port " + serverSocket.getLocalPort());

        while (true) {
            Socket socket = serverSocket.accept();
            new CloudletHandler(this, socket).start();
        }
    }

    public static void main(String[] args) {
        try {
            // Create LoadBalancer : hna n7ot port li 3ndk
            LoadBalancer lb = new LoadBalancer(8080/*port*/);

            // Create VMs : hna ratzid ga3 les Vm li 3ndk

            lb.addVM(new VirtualM(1, 1000));
            lb.addVM(new VirtualM(2, 2000));

            lb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class CloudletHandler extends Thread {
    private Socket socket;
    private LoadBalancer loadBalancer;

    public CloudletHandler(LoadBalancer loadBalancer, Socket socket) {
        this.loadBalancer = loadBalancer;
        this.socket = socket;
    }

    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            Cloudlet cloudlet = (Cloudlet) ois.readObject();
            loadBalancer.addCloudlet(cloudlet);
            loadBalancer.balanceLoad();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
