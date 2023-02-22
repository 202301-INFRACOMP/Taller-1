package edu.uniandes.factory;

public class GenerateId {

  private static int id = 0;

  public static Product createObject() {

    Product product = null;

    if (id == 0) {
      product = new Product(getId(), "🟡");
    } else {
      product = new Product(getId(), "🔵");
    }

    return product;
  }

  public static int getId() {
    return id++;
  }
}
