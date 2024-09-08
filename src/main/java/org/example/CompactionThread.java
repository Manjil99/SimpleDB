package org.example;

import java.io.IOException;

public class CompactionThread extends Thread{
    private KVDB kvdb;
    public CompactionThread(KVDB kvdb) {
        this.kvdb = kvdb;
    }

    @Override
    public void start() {
        super.start();
        System.out.println("Compaction Thread has started will compact in every 1 minute");
    }
    @Override
    public void run(){
        try {
            kvdb.compaction();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
