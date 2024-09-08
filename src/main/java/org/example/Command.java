package org.example;

public class Command {

    private String command;
    private String type;
    private String key;
    private String value;
    public Command(String commandLine){
        String[] splitedLine = commandLine.split(" ",3);
        this.command = commandLine;
        this.type = splitedLine[0];
        this.key = splitedLine[1];
        if(splitedLine.length >= 3)this.value = splitedLine[2];
    }

    public String getCommand() {
        return command;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
