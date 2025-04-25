/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.clientfrontend;
import java.util.*;
import java.util.concurrent.*;
import java.net.InetAddress;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 *
 * @author Zebra User
 */

class Client {
    public Client(BlockingQueue queue) {
        BackendClient client = new BackendClient(queue);
    }
}


class BackendClient {
    public BackendClient(BlockingQueue queue) {
        try {
            System.out.println("client");
            //Socket serConnect = new Socket(InetAddress.getLocalHost(), 8080);

            Socket serConnect = new Socket("192.168.68.100", 8080);
            BufferedReader in = new BufferedReader(new InputStreamReader(serConnect.getInputStream()));

            //For reading from server
            new Thread(() -> {
             String line;
             try {
                 while ((line = in.readLine()) != null) {
                     System.out.println("Server says: " + line);
                     try {
                        queue.put(line);
                     }
                     catch (InterruptedException e) {
                     System.out.println("Error sending msgLst");
                 }
                 }
             } 
             
             catch (IOException e) {
                 System.out.println("Error reading from server");
             }
         }).start();
            
        new ClientSender(serConnect).start();

        }

        catch (IOException e) {
            System.out.println("Connection issue");
        }
    }
}


class ClientSender extends Thread {
    Socket serConnect;
    public ClientSender(Socket server) {
        this.serConnect = server;
        try {
            PrintWriter toServer = new PrintWriter(this.serConnect.getOutputStream(), true);
            
            Scanner myObj = new Scanner(System.in);
            new Thread(() -> {
                while (true) {
                    System.out.println("Send what?: ");
                    String msg = myObj.nextLine();
                    toServer.println(msg);
                    toServer.flush();
                }
            }).start();
            
            
            
        }
        catch (IOException e) {
            System.out.println("Couldn't send message");
        }
        
    }
}
