package org.example;

import java.util.HashMap;
import java.util.List;

public class GetCommandExecutor extends CommandExecutor{
    private final HashMap<String,String> walHashTable = new HashMap<>();
    private final DBWALReader dbwalReader = new DBWALReader();
    public GetCommandExecutor(){
        super();
    }
    public void execute(Command command){
        List<Command> commandList = dbwalReader.read();
        for(Command setCommand: commandList){
            String key = setCommand.getKey();
            String value = setCommand.getValue();
            walHashTable.put(key,value);
        }
        System.out.println(walHashTable.get(command.getKey()));
    }
}
