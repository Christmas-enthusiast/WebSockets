/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.server2;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.ArrayList;



/**
 *
 * @author Zebra User
 */
public class Server2 {

    public static void main(String[] args) {
        BackendServer server = new BackendServer();
        
    }
}


class BackendServer {
    ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();
    ArrayList<String> messageList = new ArrayList<String>();
    public BackendServer() {
        try {
            //ServerSocket cliConnection = new ServerSocket(8080, 50, InetAddress.getLocalHost());
            ServerSocket cliConnection = new ServerSocket(8080, 50, InetAddress.getByName("0.0.0.0"));

            System.out.println("Port connection successful");
            
            
            while (true) {
                
                Socket client =  cliConnection.accept();
                System.out.println("Accepted client");
                ClientHandler connectedClient = new ClientHandler(client);
                
                connectedClient.out.println("SENDING Is working");
                clientList.add(connectedClient);
                System.out.println(clientList.size());
                connectedClient.clientID = clientList.size();
                connectedClient.start();
                
                
            }
            
        }
        catch (IOException e) {
            System.out.println(e);
            System.out.println("Server Failure");
            
        } 
    }
    public void broadcast(String msg) {
        System.out.println("hello");
        System.out.println(msg);
        System.out.println(clientList);
        messageList.add(msg);
        for (ClientHandler client: clientList) {
            client.out.println(messageList.toString());
            client.out.flush();
        }
    }

    

class ClientHandler extends Thread {
    
    Socket client;
    PrintWriter out;
    BufferedReader in;
    int clientID;
    
    public ClientHandler(Socket client) {
        this.client = client;
        try {
            PrintWriter toClient = new PrintWriter(this.client.getOutputStream(), true);
            this.out = toClient;
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.in = fromClient;
        }
        catch (IOException e) {
            System.out.println("Couldn't establish channels");
        }
        
    }
    
    
    //Once thread starts (ClientHandler.start()), runs once automatically
    
    @Override
    public void run(){
        //for reading from client
        while (true) {
            String line;
            
            try {
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                broadcast(Integer.toString(clientID) + ": " + line);
            }
            }
            catch (IOException e) {
                System.out.println("error");
            }
        }
    }
}
    

    
    
    
}
