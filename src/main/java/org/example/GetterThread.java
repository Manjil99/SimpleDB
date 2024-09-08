package org.example;

import java.io.IOException;

public class GetterThread extends Thread{
    private KVDB kvdb;
    public GetterThread(KVDB kvdb){this.kvdb = kvdb;}

    @Override
    public void start() {
        super.start();
        System.out.println("Getter Thread has started will get key po in every 10s");
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                System.out.println(kvdb.get("po").getValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
