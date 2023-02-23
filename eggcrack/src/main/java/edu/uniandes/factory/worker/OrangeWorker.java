package edu.uniandes.factory.worker;
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
    this.phase = 1;
    this.first = first;
    this.finalmailbox = finalmailbox;
    this.contProd = contProd;

  }

  public OrangeWorker( int contProd, Mailbox<Product> mailbox, Mailbox<Product> finalmailbox, int phase ) {
    this.first = false;
    this.contProd = contProd;
    this.mailBox = mailbox;
    this.finalmailbox = finalmailbox;
    this.phase = phase;
  }

  @Override
  public void run() {

    for (int i = 0; i < this.contProd; i++) {
      if (first) {
        Product toSend = GenerateId.createObject("Orange");
        send(toSend);
        

      } else {
          Product actual = get();
          actual.updateMessage(phase);
          send(actual);
      }
    }
  }

  private void send(Product e) {
    synchronized (finalmailbox) {
      while (finalmailbox.isFull()) {
        Thread.yield();
      }
      System.out.println(e.color + " " + phase + " sent" + " " + e.message);
      finalmailbox.send(e);
      finalmailbox.notifyAll(); 
    }
  }

  private Product get() {
    boolean cont = true;
    Product toSend = null;

    while (cont) {
      synchronized (mailBox) {
        if (mailBox.isEmpty()) {
          Thread.yield();
          continue;
        }
        toSend = mailBox.get();
        if (!toSend.getType().equals("Orange") || toSend == null) {
          // If the product is not an Orange product,
          // it gets back the object and restart the cycle.
          mailBox.send(toSend);
          continue;
        }
        cont = false;
        mailBox.notifyAll(); 
      }
    }
    return toSend;
  }


}
