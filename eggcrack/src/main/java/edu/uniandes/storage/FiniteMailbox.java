package edu.uniandes.storage;

import java.util.ArrayList;
import java.util.List;

public class FiniteMailbox<T> implements Mailbox<T> {
  private final List<T> messageBuffer;
  private int front = -1;
  private int rear = -1;

  public FiniteMailbox(int size, Class<T> clazz) {
    messageBuffer = new ArrayList<T>(size);

    for (int i = 0; i < size; i++) {
      messageBuffer.add(null);
    }
  }

  @Override
  public boolean isFull() {
    if (front == 0 && rear == messageBuffer.size() - 1) {
      return true;
    }
    if (front == rear + 1) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isEmpty() {
    if (front == -1) {
      return true;
    } else {
      return false;
    }
  }

  private void enQueue(T e) {
    if (front == -1) {
      front = 0;
    }
    rear = (rear + 1) % messageBuffer.size();
    messageBuffer.set(rear, e);
  }

  private T deQueue() {
    var e = messageBuffer.get(front);
    if (front == rear) {
      front = -1;
      rear = -1;
    } else {
      front = (front + 1) % messageBuffer.size();
    }
    return e;
  }

  @Override
  public void send(T m) {
    enQueue(m);
  }

  @Override
  public T get() {
    return deQueue();
  }

  @Override
  public void displayMailbox() {
    for (var m : messageBuffer) {
      if (m != null) {
        System.out.println(m);
      }
    }
  }
}
