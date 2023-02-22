package edu.uniandes.factory.worker;

import edu.uniandes.factory.Product;
import edu.uniandes.storage.FiniteMailbox;
import edu.uniandes.storage.Mailbox;

public class OrangeWorker implements Runnable {
  private int contProd;
  private Mailbox<Product> mailBox;
  private Mailbox<Product> finalmailbox;
  private boolean first;

  public OrangeWorker(
      int contProd, Mailbox<Product> mailbox, Mailbox<Product> finalmailbox, boolean first) {
    this.first = first;
    this.finalmailbox = finalmailbox;
    this.contProd = contProd;
    this.mailBox = mailbox;
  }

  public OrangeWorker(
      int contProd, FiniteMailbox<Product> mailbox, FiniteMailbox<Product> finalmailbox) {
    this.first = false;
    this.finalmailbox = finalmailbox;
    this.contProd = contProd;
    this.mailBox = mailbox;
  }

  @Override
  public void run() {

    for (int i = 0; i < this.contProd; i++) {
      if (first) {
        // Create a new product using a sequence

      } else {
        Product actual = get();
        actual = get();
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
