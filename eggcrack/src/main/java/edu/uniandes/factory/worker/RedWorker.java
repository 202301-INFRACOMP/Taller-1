package edu.uniandes.factory.worker;

import edu.uniandes.factory.Product;
import edu.uniandes.storage.Mailbox;
import java.util.PriorityQueue;

public class RedWorker implements Runnable {
  private final int productCount;
  private final Mailbox<Product> receiveTo;
  private final Object receiveToLock;
  private int currentId = 0;

  private final PriorityQueue<Product> pq = new PriorityQueue<>();

  public RedWorker(int productCount, Mailbox<Product> receiveTo, Object lock) {
    this.productCount = productCount;
    this.receiveTo = receiveTo;
    this.receiveToLock = lock;
  }

  @Override
  public void run() {
    for (int i = 0; i < productCount; i++) {
      Product lastProduct = null;
      synchronized (receiveToLock) {
        while (receiveTo.isEmpty()) {}

        lastProduct = receiveTo.get();
      }
      pq.add(lastProduct);

      var p = pq.peek();

      if (p.id == currentId) {

        System.out.println(String.format("📕-%d: %s", p.id, p.message));
        pq.poll();

        currentId++;
      }
    }

    for (int i = currentId; i < productCount; i++) {
      var p = pq.poll();
      System.out.println(String.format("📕-%d: %s", p.id, p.message));
    }
  }
}
