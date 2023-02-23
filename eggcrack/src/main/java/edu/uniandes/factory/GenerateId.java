package edu.uniandes.factory;

public class GenerateId {

  private static int id = 0;

    public static Product createObject(String color) {

    Product product = null;


        if (color.equals("O")) {
            product = new Product(getId(), "O");
        } 
        else {
            product = new Product(getId(), "B");
        }

        return product;
    }

    

  public static int getId() {
    return id++;
  }
}
