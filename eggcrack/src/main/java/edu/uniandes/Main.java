package edu.uniandes;

import edu.uniandes.factory.ProductFactoryBuilder;

public class Main {

  public static void main(String[] args) {
    var pfb = new ProductFactoryBuilder();
    var pf = pfb.build();
    pf.run();
  }
}
