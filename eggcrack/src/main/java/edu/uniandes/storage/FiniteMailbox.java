package edu.uniandes.storage;

import java.util.ArrayList;

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
      System.out.println("Message sent" + m);
      return true;
    }
  }

  @Override
  public T get() {
    T obj= messageBuffer.get(messageBuffer.size()-1);
    messageBuffer.remove(messageBuffer.size()-1);
    System.out.println(obj);
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