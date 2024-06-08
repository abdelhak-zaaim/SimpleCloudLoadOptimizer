package com.load.balancer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

/**
 *
 * This class is used to test the Load Balancer.
 * hna 3andna un client y3ayet l Load Balancer
 * hada just pour tester
 */
public class Test {
   public static void main(String[] args) {
      try (Socket socket = new Socket("localhost", 8080);
           ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

         Random rand = new Random();
         for (int i = 1; i <= 5; i++) {
            double length = rand.nextDouble() * 1000;
            double deadline = rand.nextDouble() * 10;
            Cloudlet cloudlet = new Cloudlet(i, length, deadline, rand.nextDouble() * 10);

            oos.writeObject(cloudlet);
            System.out.println("Sent: " + cloudlet);
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}