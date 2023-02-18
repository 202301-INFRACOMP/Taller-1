package edu.uniandes.storage;

public interface Mailbox<T> {
  boolean isFull();

  boolean isEmpty();

  void send(T m);

  T get();

  void display();
}
