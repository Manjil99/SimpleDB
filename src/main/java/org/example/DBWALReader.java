package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DBWALReader {
    private final String WALFile = "wal.txt";
    public DBWALReader(){
    }
    public List<Command> read() {
        List<Command> commandsList = new ArrayList<>();
        try{
            File walFile = new File(WALFile);
            BufferedReader walFileReader = new BufferedReader(new FileReader(walFile));
            String line;
            while ((line = walFileReader.readLine()) != null){
                commandsList.add(new Command(line));
            }
            System.out.println("Success");
        } catch (IOException e) {
            System.out.println("Failed");
            e.printStackTrace();
        }
        return commandsList;
    }
}
