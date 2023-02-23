package edu.uniandes.storage;

import edu.uniandes.factory.Product;

public interface Mailbox<T> {
  boolean isFull();

  boolean isEmpty();

  boolean send(Product m);

  Product get();

  void display();
}
