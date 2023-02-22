package edu.uniandes.factory;

public class Product implements Comparable<Product> {
  public int id;
  public String message;

  public Product(int id, String message) {
    this.id = id;
    this.message = message;
  }

  @Override
  public String toString() {
    return "Product{" + "id=" + id + ", message='" + message + '\'' + '}';
  }

  public String updateMessage(String update) {
    return message + " - " + update;
  }

  @Override
  public int compareTo(Product o) {
    return id - o.id;
  }

  public String getType() {
    return message.substring(0, 2);
  }
}
