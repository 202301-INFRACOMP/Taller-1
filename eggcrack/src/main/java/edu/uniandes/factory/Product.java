package edu.uniandes.factory;

public class Product implements Comparable<Product> {
  public int id;
  public String message;
  public String color;

  public Product(int id, String color ) {
    this.id = id;
    this.color = color;
    this.message = "Product " + id + " Created by " + color + " worker \n";
  }

  @Override
  public String toString() {
    return "Product{" + "id=" + id + ", message='" + message + '\'' + '}';
  }

  public void updateMessage(int phase, int sleepTime) {
    message = message + "phase " + phase + ": " + "Product " + id + " received by " + color + " worker \n";
  }

  @Override
  public int compareTo(Product o) {
    return id - o.id;
  }


  public String getType() {
    return color;
  }


}
