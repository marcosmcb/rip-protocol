package com.ufscar.labRedes.rip;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author marcoscavalcante
 */
public class Node0 extends Thread{
    
    private static ReentrantLock lock;
    private int idNode = 0;
    private final int numNodes = 4;
    private final int infinity = 999;
    private final int undefined = -1;
    private final int[][] distanceMatrix = new int[4][4]; /*This matrix will be used to store the distance amongst the nodes. */
    private final int tracing = 0; /*Variable used for debugging. */

    private static ServerSocket server;
    private final Socket node0;
    
    public Node0( Socket client ) {
        this.node0 = client;
        nodeInitialize();
    }
    
    public static void createServer() throws IOException {
        server = new ServerSocket(8001);
    }
    
    public static void initializeServer() {
        lock.lock();
        new Thread() {
            public void run() {
                try{
                    while(true){
                        Socket listener = server.accept();
                        Node0 node0 = new Node0(listener);
                        node0.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        lock.unlock();
    }
    
    
    
    
    
    
    public void run() {
        try{
           
            ObjectInputStream inputNode0 = new ObjectInputStream(node0.getInputStream());
            Package node0Package = (Package) inputNode0.readObject();
            
            
            
            
            
            
            
        }catch ( IOException e ) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Node0.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    
    
    
    
    
    
    /*
    This is basically the method which initializes the distanceMatrix. 
    Some distances are hardcoded because they were given. 
    I reckon that is not the best way to do that, so, feel free to change it. 
    */
    public void nodeInitialize (){
        
        this.distanceMatrix[0][0] = 0;
        this.distanceMatrix[0][1] = 1;
        this.distanceMatrix[0][2] = 3;
        this.distanceMatrix[0][3] = 7;
        
        this.distanceMatrix[1][0] = 1;
        this.distanceMatrix[2][0] = 3;
        this.distanceMatrix[3][0] = 7;
        
        
        
    }
    
    /*
    This is the method which calculates the distance between our "main" node, in this
    case, node 0, and the other nodes. We use the Bellman-Ford equation to do so.
    
    */
    
    public void nodeUpdate (Package node0Package){

        int updateDistances = 0;
        int[] sourceDistances;
        
        
        if(node0Package.getDestinationID() != idNode){
            /* If we are not the receiver, we relay the packet */
            toLayer2(node0Package); 
            return ;
        }
        
        sourceDistances = node0Package.getMinCostArr();
        
        
        /*Here is where the Bellman-Ford equation is trully applied, 
          We firstly get the distance from our node to the source node, to calculate the minimun cost,
          and if it happens to have changed, we update our own distance matrix.
        */
        for(int i = 0; i < sourceDistances.length; i++){
            
            int newDistance = distanceMatrix[idNode][node0Package.getSourceID()] + sourceDistances[i];
            
            if(newDistance < distanceMatrix[node0Package.getSourceID()][i]){
                
                updateDistances++;
                
                distanceMatrix[idNode][node0Package.getSourceID()] = newDistance;
            }
            
        }
        
        
        /*
          In case our variable, updateDistances, has been incremented, 
          We must send out our distanceVector to every other node
        */
        
        if(updateDistances > 0){
            //toLayer2();
            printDistancesNode();
        }
  
    }
    
    
    public static void main(String[] args) throws IOException {
        createServer();
        initializeServer();
    }

    private void toLayer2(Package node0Package) {
        /*TODO*/
    }
    
    private void printDistancesNode(){
        /*TODO*/
    }

    
}
