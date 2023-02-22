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

  @Override
  public int compareTo(Product o) {
    return id - o.id;
  }

}
