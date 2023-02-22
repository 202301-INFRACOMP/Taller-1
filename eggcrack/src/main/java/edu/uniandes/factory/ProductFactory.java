package edu.uniandes.factory;

import edu.uniandes.factory.worker.BlueWorker;
import edu.uniandes.factory.worker.RedWorker;
import edu.uniandes.storage.FiniteMailbox;
import edu.uniandes.storage.InfiniteMailbox;
import edu.uniandes.storage.Mailbox;
import java.util.ArrayList;
import java.util.List;

public class ProductFactory {
  private List<Thread> threads = new ArrayList<>();

  public ProductFactory(int bufferSize, int stageGroupSize, int productCount) {
    var stages = 3;
    var lastMailbox = new InfiniteMailbox<Product>();
    Mailbox<Product> prevMailbox = null;
    for (int i = 0; i < stages; i++) {
      Mailbox<Product> nextMailbox = null;
      if (i == stages - 1) {
        nextMailbox = lastMailbox;
      } else {
        nextMailbox = new FiniteMailbox<>(bufferSize, Product.class);
      }

      // TODO: construct orangeworker
      // threads.add(new Thread(new OrangeWorker(prevMailBox, nextMailbox)));
      for (int j = 0; j < stageGroupSize - 1; j++) {
        // TODO: construct blueworker
        // threads.add(new Thread(new BlueWorker(prevMailBox, nextMailbox));
        threads.add(new Thread(new BlueWorker(prevMailbox, nextMailbox, productCount, i)));
      }

      prevMailbox = nextMailbox;
    }

    threads.add(new Thread(new RedWorker(productCount, lastMailbox)));
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
