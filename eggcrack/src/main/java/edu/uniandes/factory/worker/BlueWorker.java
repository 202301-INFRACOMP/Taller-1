package edu.uniandes.factory.worker;

import edu.uniandes.factory.GenerateId;
import edu.uniandes.factory.Product;
import edu.uniandes.storage.Mailbox;

public class BlueWorker extends Thread {
  private Mailbox<Product> receiveMailBox;
  private Mailbox<Product> sendMailBox;
  private int productCount;
  private int phase;

  public BlueWorker( Mailbox<Product> sendMailBox, int productCount ) {
    this.phase = 1;
    this.sendMailBox = sendMailBox;
    this.productCount = productCount;
  }

    public BlueWorker(Mailbox<Product> receivMailbox, Mailbox<Product> sendMailBox , int productCount, int phase ) {
        this.phase = phase;
        this.sendMailBox = sendMailBox;
        this.receiveMailBox = sendMailBox;
        this.productCount = productCount;
    }

   

@Override
public void run() {
  System.out.println("Empezo a correr azul de la estapa " + phase);
    for (int i = 0; i < productCount; i++) {

        if (phase == 1) {
            
            Product toSend = GenerateId.createObject("B");
            
            sendTo(toSend);
        
        } 
        else {
            Product toSend = receiveFrom();
            toSend.updateMessage(phase);
            sendTo(toSend);
        }
        }
}
    
    

  private Product receiveFrom() {

    Product toSend = null;
    boolean isBlue = false;

    while (!isBlue) {

      synchronized (receiveMailBox) {
        System.out.println("Syn Azul");
        while (receiveMailBox.isEmpty()) {
          // passive wait
          try {
            receiveMailBox.wait();
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        toSend = receiveMailBox.get();
        // equales blue
        if (toSend.getType().equals("B")) {
          isBlue = true;
        } else {
          receiveMailBox.send(toSend);
          receiveMailBox.notifyAll();
        }
        receiveMailBox.notifyAll();
      }
    }
    return toSend;
  }

  private void sendTo(Product product) {
    synchronized (sendMailBox) {
      System.out.println("Syn Azul");
      while (sendMailBox.isFull()) {
        // passive wait
        try {
          sendMailBox.wait();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      sendMailBox.send(product);
      sendMailBox.notifyAll();
    }
  }
}
