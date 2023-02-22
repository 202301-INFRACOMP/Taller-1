package edu.uniandes.factory.worker;

import edu.uniandes.factory.GenerateId;
import edu.uniandes.factory.Product;
import edu.uniandes.storage.FiniteMailbox;
import edu.uniandes.storage.Mailbox;

public class OrangeWorker implements Runnable {
  private int contProd;
  private FiniteMailbox<Product> mailBox;
  private FiniteMailbox<Product> finalmailbox;
  private boolean first;
  private int phase;

  public OrangeWorker(
      int contProd,
      FiniteMailbox<Product> finalmailbox,
      boolean first) {
    this.first = first;
    this.finalmailbox = finalmailbox;
    this.contProd = contProd;

  }

  public OrangeWorker(
      int contProd, FiniteMailbox<Product> mailbox, FiniteMailbox<Product> finalmailbox, int phase) {
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
        Product toSend = GenerateId.createObject("🟡");
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
      mailBox.send(e);
    }
  }

  private Product get() {
    boolean cont = true;
    Product toSend = null;
    while (cont) {
      synchronized (mailBox) {
        while (mailBox.isEmpty()) {
          Thread.yield();
        }
        toSend = mailBox.get();
        if (toSend.getType().equals("O")) {
          cont = false;
        }
        // If the product is not an Orange product,
        // it gets back the object and restart the cycle.
        else {
          mailBox.send(toSend);
        }
      }
    }
    return toSend;
  }
}
