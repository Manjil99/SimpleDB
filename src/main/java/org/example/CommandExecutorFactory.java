package org.example;

public class CommandExecutorFactory {
    private final SetCommandExecutor setCommandExecutor = new SetCommandExecutor();
    private final GetCommandExecutor getCommandExecutor = new GetCommandExecutor();
    private final String SET = "set";
    private final String GET = "get";
    public CommandExecutorFactory(){}
    public CommandExecutor getCommandExecutor(Command command){
        if(command.getType().equals(SET)){
            return this.setCommandExecutor;
        } else if (command.getType().equals(GET)) {
            return this.getCommandExecutor;
        }
        return null;
    }
}
