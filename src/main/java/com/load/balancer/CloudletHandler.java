package com.load.balancer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

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