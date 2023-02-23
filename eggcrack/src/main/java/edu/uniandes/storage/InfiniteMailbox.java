package edu.uniandes.storage;

import java.util.ArrayList;
import java.util.List;

import edu.uniandes.factory.Product;

public class InfiniteMailbox implements Mailbox<Product> {
  private List<Product> messageBuffer;

  public InfiniteMailbox() {
    messageBuffer = new ArrayList<Product>();
  }

  @Override
  public boolean isFull() {
    return false;
  }

  @Override
  public boolean isEmpty() {
    if (messageBuffer.size() == 0) {
      return true;
    }
    return false;
  }

  @Override
  public boolean send(Product m) {
    messageBuffer.add(m);
  
    return true;
  }

  @Override
  public Product get() {

    var c = messageBuffer.get(messageBuffer.size() - 1);
    messageBuffer.remove(messageBuffer.size() - 1);
  
    return c;
  }

  @Override
  public void display() {
    for (var m : messageBuffer) {
      if (m != null) {
        System.out.println(m);
      }
    }
  }
}
