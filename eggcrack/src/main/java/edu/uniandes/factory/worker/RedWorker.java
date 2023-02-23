package edu.uniandes.factory.worker;

import edu.uniandes.factory.Product;
import edu.uniandes.storage.Mailbox;
import java.util.PriorityQueue;

public class RedWorker implements Runnable {
  private final int productCount;
  private final int workerCount;
  private final Mailbox<Product> receiveTo;
  private int currentId = 0;
  private final PriorityQueue<Product> pq = new PriorityQueue<>();

  public RedWorker(int workerCount, int productCount, Mailbox<Product> receiveTo) {
    this.workerCount = workerCount;
    this.productCount = productCount;
    this.receiveTo = receiveTo;
  }

  @Override
  public void run() {
    for (int i = 0; i < productCount * workerCount; i++) {
      var lastProduct = getProduct();

      pq.add(lastProduct);
      var p = pq.peek();

      if (p.id == currentId) {
        System.out.println(String.format("Red %d: %s", p.id, p.message));
        pq.poll();

        currentId++;
      }
    }
    for (int i = currentId; i < productCount * workerCount; i++) {
      var p = pq.poll();
      System.out.println(String.format("Red %d: %s", p.id, p.message));
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
