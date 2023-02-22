package edu.uniandes.factory;

import edu.uniandes.factory.worker.BlueWorker;
import edu.uniandes.storage.FiniteMailbox;
import edu.uniandes.storage.Mailbox;
import java.util.ArrayList;
import java.util.List;

public class ProductFactory {
  private List<Thread> threads = new ArrayList<>();

  public ProductFactory(int bufferSize, int stageGroupSize, int productCount) {
    var stages = 3;

    // TODO: replace Object by InfiniteMailbox in lastMailbox
    var lastMailbox = new Object();
    // TODO: implement blue and orange workers and create a thread passing its corresponding worker
    Mailbox<Product> prevMailbox = null;
    Object prevLock = null;
    for (int i = 0; i < stages; i++) {
      Mailbox<Product> nextMailbox = null;
      Object nextLock = null;
      if (i == stages - 1) {
        // nextMailbox = lastMailbox;
      } else {
        nextMailbox = new FiniteMailbox<>(bufferSize, Product.class);
        nextLock = new Object();
      }

      // threads.add(new Thread(new OrangeWorker(prevMailBox, prevLock, nextMailbox, nextLock)));
      for (int j = 0; j < 2; j++) {
        // threads.add(new Thread(new BlueWorker(prevMailBox, prevLock, nextMailbox, nextLock));
        threads.add(new Thread(new BlueWorker(prevMailbox, nextMailbox, productCount, i)));
      }

      prevMailbox = nextMailbox;
    }

    // threads.add(new Thread(new RedWorker(productCount, lastMailbox)));
  }

  public void run() {
    for (var t : threads) {
      t.start();
    }

    for (var t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
