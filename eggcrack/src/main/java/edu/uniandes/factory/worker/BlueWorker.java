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

    for (int i = 0; i < productCount; i++) {

        if (phase == 1) {
            
            Product toSend = GenerateId.createObject("Blue");
            System.out.println(toSend.message + " by " + toSend.color + " in phase" + phase);
            sendTo(toSend);
        
        } 
        else {
          if (!receiveMailBox.isEmpty()) {
            Product toSend = receiveFrom();
            System.out.println("Blue " + phase + " received" + toSend.message);
            toSend.updateMessage(phase);
            System.out.println(toSend.message + " by " + toSend.color + " in phase" + phase);
            sendTo(toSend);
          }
        }
    }
}
    
    

  private Product receiveFrom() {

    Product toSend = null;
    boolean isBlue = false;

    while (!isBlue && !receiveMailBox.isEmpty()) {

      synchronized (receiveMailBox) {
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
        if (toSend.getType().equals("Blue")) {
          isBlue = true;
        } else {
          receiveMailBox.send(toSend);
        }
        receiveMailBox.notifyAll();
      }
    }
    return toSend;
  }

  private void sendTo(Product product) {
    synchronized (sendMailBox) {
      while (sendMailBox.isFull()) {
        // passive wait
        try {
          sendMailBox.wait();
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      System.out.println(product.color + " " + phase + " sent");
      sendMailBox.send(product);
      sendMailBox.notifyAll(); 
    }
  }
}
