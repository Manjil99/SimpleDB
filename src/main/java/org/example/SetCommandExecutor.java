package org.example;

public class SetCommandExecutor extends CommandExecutor{
    private final DBWALWriter dBWALWriter = new DBWALWriter();
    public SetCommandExecutor(){
        super();
    }
    public void execute(Command command) {
        dBWALWriter.write(command.getCommand());
    }
}
