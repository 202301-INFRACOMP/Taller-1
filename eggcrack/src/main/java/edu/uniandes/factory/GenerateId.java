package edu.uniandes.factory;

public class GenerateId {

  private static int id = 0;

  public synchronized static Product createObject(String color, int phase) {

    Product product = null;

    // Create the product with the corresponding color
    if (color.equals("Orange")) {
        product = new Product(getId(), "Orange", phase);
    } 
    else {
        product = new Product(getId(), "Blue", phase);
    }

    return product;
  }


  // Method to generate the id of the product
  public static int getId() {
    return id++;
  }
}
