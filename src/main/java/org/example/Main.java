package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        final CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final KVDB kvdb = new KVDB();
        Thread getterThread = new GetterThread(kvdb);
        Thread compactionThread = new CompactionThread(kvdb);
        getterThread.start();
        compactionThread.start();
        while (true) {
            final String input = reader.readLine();
            Command command = new Command(input);
            if(command.getType().equals("get"))System.out.println(kvdb.get(command.getKey()).getValue());
            else if (command.getType().equals("set"))kvdb.set(command.getCommand());
            // commandExecutorFactory.getCommandExecutor(command).execute(command);
        }
    }
}