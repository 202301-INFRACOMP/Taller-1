package edu.uniandes.factory;

public class Product implements Comparable<Product> {
  public int id;
  public String message;
  public String color;


  public Product(int id, String color, int phase) {
    this.id = id;
    this.color = color;
    this.message = "Phase " + phase + ": " + "Product " + id + " Created by " + color + " worker \n";
  }


  @Override
  public String toString() {
    return "Product{" + "id=" + id + ", message='" + message + '\'' + '}';
  }


  // Received message
  public void updateMessageReceived(int phase) {
    message = message + "Phase " + phase + ": " + "Product " + id + " Received by " + color + " worker \n";
  }


  // Sent message
  public void updateMessageSent(int phase) {
    message = message + "Phase " + phase + ": " + "Product " + id + " Sent by " + color + " worker \n";
  }


  // Modify message
  public void transformMessage(int phase, int sleepTime) {
    message = message + "Phase " + phase + ": " + "Product " + id + " Was modified by " + color + " worker in " + sleepTime + " ms \n";
  }


  @Override
  public int compareTo(Product o) {
    return id - o.id;
  }

  // Get color of product
  public String getType() {
    return color;
  }


}
