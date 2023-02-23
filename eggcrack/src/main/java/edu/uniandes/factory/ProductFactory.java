package edu.uniandes.factory;

import edu.uniandes.factory.worker.BlueWorker;
import edu.uniandes.factory.worker.OrangeWorker;
import edu.uniandes.factory.worker.RedWorker;
import edu.uniandes.storage.FiniteMailbox;
import edu.uniandes.storage.InfiniteMailbox;
import java.util.ArrayList;
import java.util.List;

public class ProductFactory {
  private List<Thread> threads = new ArrayList<>();

  public ProductFactory(int bufferSize, int stageGroupSize, int productCount) {
    var stages = 3;
    
    FiniteMailbox<Product> firstMailbox = new FiniteMailbox<>(bufferSize);
    FiniteMailbox<Product> secondMailbox = new FiniteMailbox<>(bufferSize);
    InfiniteMailbox<Product> lastMailbox = new InfiniteMailbox<>();

    int i = 1;
    while (i <= stages) {

      if (i == 1){
         threads.add( new Thread(new OrangeWorker( productCount, firstMailbox, true )));
         System.out.println("Se creo un naranja");

        for (int j = 0; j < stageGroupSize - 1; j++) {
          System.out.println("Se creo un azul");
            threads.add(new BlueWorker( firstMailbox, productCount));
        }
      }

      else if (i == 2){
        threads.add( new Thread(new OrangeWorker( productCount, firstMailbox, secondMailbox, i )));
        System.out.println("Se creo un naranja");
        for (int j = 0; j < stageGroupSize - 1; j++) {
          System.out.println("Se creo un azul");
          threads.add(new BlueWorker(firstMailbox, secondMailbox, productCount, i));
        }
      }

      else {
        System.out.println("Se creo un naranja");
         threads.add( new Thread(new OrangeWorker( productCount, secondMailbox, lastMailbox, i )));
        for (int j = 0; j < stageGroupSize - 1; j++) {
          System.out.println("Se creo un azul");
          threads.add(new BlueWorker(secondMailbox, lastMailbox, productCount, i));
        }
      }
      i++;
    }

    threads.add(new Thread(new RedWorker(productCount, lastMailbox, stageGroupSize)));
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
