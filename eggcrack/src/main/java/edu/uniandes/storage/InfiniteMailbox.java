package edu.uniandes.storage;

import java.util.ArrayList;
import java.util.List;



public class InfiniteMailbox<T> implements Mailbox<T>{
    private  List<T> messageBuffer;

    public InfiniteMailbox(){
        messageBuffer = new ArrayList<T>();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        if (messageBuffer.size()==0){
            return true;
        }
        return false;
    }

    @Override
    public void send(T m) {
        messageBuffer.add(m);
    }

    @Override
    public T get() { 
        var c =messageBuffer.get(messageBuffer.size()-1) ; 
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