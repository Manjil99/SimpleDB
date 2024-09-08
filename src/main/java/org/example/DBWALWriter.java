package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DBWALWriter {
    private final String WALFile = "wal.txt";
    public DBWALWriter(){
    }
    public void write(String command) {
        try{
            File walFile = new File(WALFile);
            walFile.createNewFile();
            BufferedWriter walFileWriter = new BufferedWriter(new FileWriter(walFile,true));
            walFileWriter.write(command);
            walFileWriter.newLine();
            walFileWriter.flush();
            walFileWriter.close();
            System.out.println("Success");
        } catch (IOException e) {
            System.out.println("Failed");
            e.printStackTrace();
        }
    }
}
