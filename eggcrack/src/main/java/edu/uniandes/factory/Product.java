package edu.uniandes.factory;

public class Product implements Comparable<Product> {
  public int id;
  public String message;
  public String color;

  public Product(int id, String color) {
    this.id = id;
    this.color = color;
    this.message = "Product Created";
  
   
  }

  @Override
  public String toString() {
    return "Product{" + "id=" + id + ", message='" + message + '\'' + '}';
  }

  public String updateMessage(int phase) {

    String xd = message + " - " + color + " - product currently in phase " + phase;
    System.out.println(xd);
    return xd;
  }

  @Override
  public int compareTo(Product o) {
    return id - o.id;
  }


  public String getType() {
    return color;
  }


}
