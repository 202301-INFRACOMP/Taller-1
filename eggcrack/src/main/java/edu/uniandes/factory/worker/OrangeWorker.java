package edu.uniandes.factory.worker;
import java.util.Random;

import edu.uniandes.factory.GenerateId;
import edu.uniandes.factory.Product;
import edu.uniandes.storage.Mailbox;

public class OrangeWorker extends Thread {
  private int contProd;
  private Mailbox<Product> mailBox;
  private Mailbox<Product> finalmailbox;
  private boolean first;
  private int phase;

  public OrangeWorker( int contProd, Mailbox<Product> finalmailbox, boolean first ) {
    this.first = first;
    this.finalmailbox = finalmailbox;
    this.contProd = contProd;

  }

  public OrangeWorker( int contProd, Mailbox<Product> mailbox, Mailbox<Product> finalmailbox, int phase ) {
    this.first = false;
    this.finalmailbox = finalmailbox;
    this.contProd = contProd;
    this.mailBox = mailbox;
    this.phase = phase;
  }

  @Override
  public void run() {
    
    for (int i = 0; i < this.contProd; i++) {
      if (first) {
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
