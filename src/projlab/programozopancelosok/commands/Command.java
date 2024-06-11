package projlab.programozopancelosok.commands;

public class Command {
    public interface RunnableCmd {
        CmdResult run(String[] cmd);
    }

    String name;
    RunnableCmd command;

    public Command(String name, RunnableCmd command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public CmdResult run(String[] cmd) {
        return command.run(cmd);
    }
}
