package edu.uniandes.factory;

public class GenerateId {

  private static int id = 0;

    public synchronized static Product createObject(String color) {

    Product product = null;


        if (color.equals("Orange")) {
            product = new Product(getId(), "Orange");
        } 
        else {
            product = new Product(getId(), "Blue");
        }

        return product;
    }

    

  public static int getId() {
    return id++;
  }
}
