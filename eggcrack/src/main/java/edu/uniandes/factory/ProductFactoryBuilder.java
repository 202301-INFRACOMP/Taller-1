package edu.uniandes.factory;

import java.util.Scanner;

public class ProductFactoryBuilder {
  public ProductFactoryBuilder() {}

  public ProductFactory build() {

    // read from console
    Scanner scanner = new Scanner(System.in);

    // read and save the parameter
    System.out.println("Ingrese el tamanio de los buffer: ");
    int bufferSize = scanner.nextInt();

    // read and save the parameter
    System.out.println("Ingrese el numero de productores: ");
    int stageGroupSize = scanner.nextInt();

    // read and save the parameter
    System.out.println("Ingrese el numero de procesos que cada productor realizara: ");
    int productCount = scanner.nextInt();

    // close the scanner
    scanner.close();

    // ret new ProductFractory(params)
    return new ProductFactory(bufferSize, stageGroupSize, productCount);
  }
}
