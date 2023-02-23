package edu.uniandes.factory.worker;

import edu.uniandes.factory.GenerateId;
import edu.uniandes.factory.Product;
import edu.uniandes.storage.Mailbox;
import java.util.Random;

public class BlueWorker extends Thread {
  private Mailbox<Product> receiveMailBox = null;
  private Mailbox<Product> sendMailBox;
  private int productCount;
  private int phase;

  // Constructor for the first phase
  public BlueWorker( Mailbox<Product> sendMailBox, int productCount ) {
    this.phase = 1;
    this.sendMailBox = sendMailBox;
    this.productCount = productCount;
  }

  // Constructor for the second phase and the third phase
  public BlueWorker(Mailbox<Product> receiveMailbox, Mailbox<Product> sendMailBox , int productCount, int phase ) {
      this.phase = phase;
      this.receiveMailBox = receiveMailbox;
      this.sendMailBox = sendMailBox;
      this.productCount = productCount;
  }

   

  @Override
  public void run() {

    for (int i = 0; i < productCount; i++) {

      // Create the product and send it to the next worker
      if (phase == 1) {
          
          Product toSend = GenerateId.createObject("Blue", 1);
          sendTo(toSend);
      } 
      // Receive the product from the previous buffer, transform it and send it to the next worker
      else {
        Product toSend = receiveFrom();
        toSend.updateMessageReceived(phase);

        // Simulate the transformation time
        Random random = new Random();
        int sleepTime = random.nextInt(451) + 50;
        try {
          sleep(sleepTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        toSend.transformMessage(phase, sleepTime);
        toSend.updateMessageSent(phase);
        sendTo(toSend);
      }
    }
}
    
    
  // Receive a product from the previous buffer
  private Product receiveFrom() {

    Product toSend = null;
    boolean isBlue = false;

    // Wait until the product found is blue
    while (!isBlue) {

      // Wait until the buffer is not empty and get the product
      synchronized (receiveMailBox) {
        while (receiveMailBox.isEmpty()) {
          // passive wait
          try {
            receiveMailBox.wait();
          } catch (InterruptedException e) {
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


  // Send a product to the next buffer
  private void sendTo(Product product) {

    // Wait until the buffer is not full and send the product
    synchronized (sendMailBox) {
      while (sendMailBox.isFull()) {
        // passive wait
        try {
          sendMailBox.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      sendMailBox.send(product);
      sendMailBox.notifyAll(); 
    }
  }
}
