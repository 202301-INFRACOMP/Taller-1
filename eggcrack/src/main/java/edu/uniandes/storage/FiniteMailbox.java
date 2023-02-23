package edu.uniandes.storage;

import java.util.ArrayList;
import java.util.Random;

public class FiniteMailbox<T> implements Mailbox<T> {
  private final ArrayList<T> messageBuffer;
  private int size ;

  public FiniteMailbox(int size) {
    messageBuffer = new ArrayList<T>();
    this.size = size;

  }

  @Override
  public boolean isFull() {
    if (messageBuffer.size() == this.size){
      return true;
    }
    else{
      return false;
    }
  }

  @Override
  public boolean isEmpty() {
    return messageBuffer.isEmpty();
  }




  @Override
  public boolean send(T m) {
    if (size == messageBuffer.size()){
      return false;
    }
    else{
      messageBuffer.add(m);
      return true;
    }
  }

  @Override
  public T get() {
    if (messageBuffer.isEmpty()) {
      return null; // or throw an exception
    }
    int index = new Random().nextInt(messageBuffer.size());
    T obj = messageBuffer.get(index);
    messageBuffer.remove(index);
    return obj;
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