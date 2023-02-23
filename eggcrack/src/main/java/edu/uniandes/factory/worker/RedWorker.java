package edu.uniandes.factory.worker;

import edu.uniandes.factory.Product;
import edu.uniandes.storage.Mailbox;
import java.util.PriorityQueue;

public class RedWorker implements Runnable {
  private final int productCount;
  private final Mailbox<Product> receiveTo;
  private int currentId = 0;
  private final PriorityQueue<Product> pq = new PriorityQueue<>();

  public RedWorker(int productCount, Mailbox<Product> receiveTo, int workers) {
    this.productCount = productCount*workers;
    this.receiveTo = receiveTo;
  }

  @Override
  public void run() {
    for (int i = 0; i < productCount; i++) {
      var lastProduct = getProduct();

      pq.add(lastProduct);
      var p = pq.peek();

      if (i == 0) {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

      }

      if (p.id == currentId) {
        System.out.println("Red Worker " + p.id + ": \n" + p.message);
        pq.poll();

        currentId++;
      }
    }
    for (int i = currentId; i < productCount; i++) {
      var p = pq.poll();
      System.out.println("Red Worker " + p.id + ": \n" + p.message);
    }
  }

  Product getProduct() {
    while (true) {
      synchronized (receiveTo) {
        if (!receiveTo.isEmpty()) {
          var p = receiveTo.get();
          receiveTo.notifyAll();
         
          return p;
        }
      }
    }
  }
}
