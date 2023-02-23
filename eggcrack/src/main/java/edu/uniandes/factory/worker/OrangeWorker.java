package edu.uniandes.factory.worker;
import edu.uniandes.factory.GenerateId;
import edu.uniandes.factory.Product;
import edu.uniandes.storage.Mailbox;

public class OrangeWorker implements Runnable {
  private int contProd;
  private Mailbox<Product> mailBox;
  private Mailbox<Product> finalmailbox;
  private boolean first;
  private int phase;

  public OrangeWorker( int contProd, Mailbox<Product> finalmailbox, boolean first ) {
    this.first = first;
    this.finalmailbox = finalmailbox;
    this.contProd = contProd;

  }

  public OrangeWorker( int contProd, Mailbox<Product> mailbox, Mailbox<Product> finalmailbox, int phase ) {
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
        Product toSend = GenerateId.createObject("O");
        send(toSend);
        System.out.println("Cree un objeto.");
        

      } else {
        
        Product actual = get();
        actual.updateMessage(phase);
        send(actual);
      }
    }
  }

  private void send(Product e) {
    while (finalmailbox.isFull()) {
      System.out.println("Se inicia un Yield en send");
      Thread.yield();
    }
    synchronized (finalmailbox) {
      System.out.println("Empezo a correr naranja de la estapa " + phase);
      if (finalmailbox.send(e)){
        System.out.println("Realmente se mando1");
      }
      
      System.out.println("Logre mandar un coso Naranja");
    }
  }

  private Product get() {
    boolean cont = true;
    Product toSend = null;
    
    while (cont) {
      
      while (mailBox.isEmpty()) {
        Thread.yield();
      
      }
      synchronized (mailBox) {
        
        toSend = mailBox.get();
        if (toSend.getType().equals("O")) {
          cont = false;
          System.out.println("Logre Traer un coso Naranja " + phase);
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
