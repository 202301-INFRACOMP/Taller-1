package edu.uniandes.factory.worker;

import edu.uniandes.factory.GenerateId;
import edu.uniandes.factory.Product;
import edu.uniandes.storage.Mailbox;
import java.util.Random;

public class OrangeWorker extends Thread {
  private Mailbox<Product> mailBox;
  private Mailbox<Product> finalmailbox;
  private int contProduct;
  private int phase;

  // Constructor for the first phase
  public OrangeWorker( int contProduct, Mailbox<Product> finalmailbox) {
    this.phase = 1;
    this.finalmailbox = finalmailbox;
    this.contProduct = contProduct;
  }

  public OrangeWorker( int contProduct, Mailbox<Product> mailbox, Mailbox<Product> finalmailbox, int phase ) {
    this.finalmailbox = finalmailbox;
    this.contProduct = contProduct;
    this.mailBox = mailbox;
    this.phase = phase;
  }

  @Override
  public void run() {
    
    for (int i = 0; i < this.contProduct; i++) {

      if (phase == 1) {
        Product toSend = GenerateId.createObject("Orange", 1);
        send(toSend);
      

      } else {
        
        Product actual = get();
        actual.updateMessageReceived(phase);
        Random random = new Random();
        int sleepTime = random.nextInt(451) + 50;
        try {
          sleep(sleepTime);
        } catch (InterruptedException e) {        // 
          e.printStackTrace();
        }
        actual.transformMessage(phase, sleepTime);
        actual.updateMessageSent(phase);
        send(actual);
      }
    }
  }

  private void send(Product e) {
    boolean cont = true;
    while (cont) {
      if (finalmailbox.isFull()){
        Thread.yield();}
    
    synchronized (finalmailbox) {

      if (finalmailbox.send(e)){
       
        cont = false;
      }
      finalmailbox.notifyAll();
      
    }}
  }

  private Product get() {
    boolean cont = true;
    Product toSend = null;
    
    while (cont) {
      if (mailBox.isEmpty()){
         Thread.yield();
         
      }
      synchronized (mailBox) {
        if (!mailBox.isEmpty()){
          toSend = mailBox.get();
          if (toSend.getType().equals("Orange")) {
            cont = false;
           
          }
          // If the product is not an Orange product,
          // it gets back the object and restart the cycle.
          else {
            mailBox.send(toSend);
          }
        }
        mailBox.notifyAll();
      } 
    }
    return toSend;
  }
}
