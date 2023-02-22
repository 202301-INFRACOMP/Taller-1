package edu.uniandes.factory.worker;

import edu.uniandes.factory.GenerateId;
import edu.uniandes.factory.Product;
import edu.uniandes.storage.Mailbox;

public class BlueWorker extends Thread {
    private Mailbox<Product> receiveMailBox;
    private Mailbox<Product> sendMailBox;
    private int productCount;
    private int phase;
    private int cantProductos;


    public BlueWorker(Mailbox<Product> sendMailBox, int productCount, int cantProductos) {
        this.phase = 1;
        this.sendMailBox = sendMailBox;
        this.productCount = productCount;
        this.cantProductos = cantProductos;
    }

    public BlueWorker(Mailbox<Product> receiveMailBox, Mailbox<Product> sendMailBox, int productCount, int phase) {
        this.phase = phase;
        this.receiveMailBox = receiveMailBox;
        this.sendMailBox = sendMailBox;
        this.productCount = productCount;
    }


    @Override
    public void run() {
    
        for (int i = 0; i < productCount; i++) {

            if (phase == 1) {
               
                Product toSend = GenerateId.createObject("🔵");
                sendTo(toSend);
            
            } 
            else {
                Product toSend = receiveFrom();
                toSend.updateMessage(phase);
                sendTo(toSend);
            }
        }
    }

    private Product receiveFrom() {

        Product toSend = null;
        boolean isBlue = false;

        while (!isBlue) {

            synchronized (receiveMailBox) {
                while (receiveMailBox.isEmpty()) {
                    // passive wait
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                toSend = receiveMailBox.get();
                //equales blue
                if (toSend.getType().equals("🔵")) {
                    isBlue = true;
                } 

                else {
                    receiveMailBox.send(toSend);
                }
            }
        }
        return toSend;
    }

    private void sendTo(Product product) {
        synchronized (sendMailBox) {
            while (sendMailBox.isFull()) {
                // passive wait
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            sendMailBox.send(product);
        }
    }

}
