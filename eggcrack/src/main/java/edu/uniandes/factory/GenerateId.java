package edu.uniandes.factory;


public class GenerateId {

    private static int id = 0;

    public static Product createObject() {

        Product product = null;

        if (id == 0) {
            product = new Product(getId(), "🟡 - product created in phase 1");
        } 
        else {
            product = new Product(getId(), "🔵 - product created in phase 1");
        }

        return product;
    }


    public static int getId() {
        return id++;
    }


}