package edu.uniandes.factory;

import edu.uniandes.factory.worker.BlueWorker;
import edu.uniandes.factory.worker.OrangeWorker;
import edu.uniandes.storage.FiniteMailbox;
import edu.uniandes.storage.InfiniteMailbox;

import java.util.ArrayList;
import java.util.List;

public class ProductFactory {
  private List<Thread> threads = new ArrayList<>();

  public ProductFactory(int bufferSize, int stageGroupSize, int productCount) {

    FiniteMailbox<Product> mail1 = new FiniteMailbox<> (bufferSize);
    FiniteMailbox<Product> mail2 = new FiniteMailbox<> (bufferSize);
    InfiniteMailbox<Product> mail3 = new InfiniteMailbox<> ();

    for (int i = 0 ; i <stageGroupSize ; i++ ){

      if (i==0){
        OrangeWorker og = new OrangeWorker (productCount, mail1, true);
        threads.add(new Thread(og));
        System.out.println("Se crea un creador naranja");
        
      }
      else{
        BlueWorker bw =  new BlueWorker(mail1, productCount);
        threads.add(bw);
        System.out.println("Se crea un creador azul");
      }

    }
    for (int i = 0 ; i < stageGroupSize ; i++ ){
      if (i==0){
        System.out.println("Se crea un creador naranja");
        OrangeWorker og = new OrangeWorker (productCount, mail1, mail2,2);
        threads.add(new Thread(og));
      }
      else{
        System.out.println("Se crea un creador azul");
        BlueWorker bw =  new BlueWorker(mail1, mail2, productCount, 2);
        threads.add(bw);
      }

    }
    for (int i = 0 ; i < stageGroupSize ; i++ ){
      if (i==0){
        System.out.println("Se crea un creador naranja");
        OrangeWorker og = new OrangeWorker (productCount, mail2, mail3,3);
        threads.add(new Thread(og));
      }
      else{
        System.out.println("Se crea un creador azul");
        BlueWorker bw =  new BlueWorker(mail2, mail3, productCount, 3);
        threads.add(bw);
      }

    }

    
  }

  public void run() {
    for (Thread t : threads){
      
      t.start();
      
    }
   
    
  }
}
