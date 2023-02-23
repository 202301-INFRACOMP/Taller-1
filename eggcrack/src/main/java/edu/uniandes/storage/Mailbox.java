package edu.uniandes.storage;

public interface Mailbox<T> {
  boolean isFull();

  boolean isEmpty();

  boolean send(T m);

  T get();

  void display();
}
