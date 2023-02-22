package edu.uniandes;

import edu.uniandes.factory.ProductFactoryBuilder;

public class Main {

  public static void main(String[] args) {

    System.out.print(
        """
            ========================================================================
                                                             _   \s
                                                            | |  \s
                         ___  __ _  __ _  ___ _ __ __ _  ___| | __
                        / _ \\/ _` |/ _` |/ __| '__/ _` |/ __| |/ /
                       |  __/ (_| | (_| | (__| | | (_| | (__|   <\s
                        \\___|\\__, |\\__, |\\___|_|  \\__,_|\\___|_|\\_\\
                              __/ | __/ |                        \s
                             |___/ |___/                         \s
            ========================================================================

            """);

    System.out.println("Welcome to case study No.1 of INFRACOMP202301");
    System.out.println("Code name: Project eggcrack");
    System.out.println("Implemented on Java\n");

    var pfb = new ProductFactoryBuilder();
    var pf = pfb.build();
    pf.run();
  }
}
