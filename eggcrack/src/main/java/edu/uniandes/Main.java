package edu.uniandes;

import edu.uniandes.factory.Product;
import edu.uniandes.factory.worker.RedWorker;
import edu.uniandes.storage.FiniteMailbox;

public class Main {
  public static void main(String[] args) {
    var productSize = 5;
    var lock = new Object();

    var mailbox = new FiniteMailbox<>(5, Product.class);
    mailbox.send(new Product(4, "four"));
    mailbox.send(new Product(3, "three"));
    mailbox.send(new Product(2, "two"));
    mailbox.send(new Product(1, "one"));
    mailbox.send(new Product(0, "zero"));
    // mailbox.displayMailbox();

    var redWorker = new RedWorker(productSize, mailbox, lock);
    redWorker.run();
  }
}
