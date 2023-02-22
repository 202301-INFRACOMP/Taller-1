package edu.uniandes.factory;


public class GenerateId {

    private static int id = 0;

    public static Product createObject(String color) {

        Product product = null;


        if (color.equals("🟡")) {
            product = new Product(getId(), "🟡");
        } 
        else {
            product = new Product(getId(), "🔵");
        }

        return product;
    }


    public static int getId() {
        return id++;
    }


}