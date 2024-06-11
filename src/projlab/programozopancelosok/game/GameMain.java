package projlab.programozopancelosok.game;

import projlab.programozopancelosok.commands.CmdHandler;
import projlab.programozopancelosok.commands.CmdResult;
import projlab.programozopancelosok.control.Control;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameMain {
    public static void main(String[] args) throws Exception {
        Control control = new Control(new Random());

        //Initialization of the map
        if (args.length >= 2) {
            String initFile = args[1];
            CmdHandler initCmdHandler = new CmdHandler(control);
            initCmdHandler.registerTestCmds();

            List<String> lines = Files.readAllLines(Paths.get(initFile));

            for (String line : lines) {
                CmdResult result = initCmdHandler.executeCmd(line);

                if (!result.isCmdSuccess()) {
                    System.out.println("Error in initialization file \"" + initFile + "\"!!!");
                    System.out.println("Error at line : \"" + line + "\"\n\t" + result.getMsg());
                    return;
                }
            }

        }

        CmdHandler cmdHandler = new CmdHandler(control);
        cmdHandler.registerGameCmds();
        Scanner scanner = new Scanner(System.in);

        while (control.getGameState().isRunning()) {
            String actPlayer = cmdHandler.getCurrentPlayerId();
            System.out.println("Tick #" + control.getTicksElapsed());
            System.out.println("SHOWING PLAYER " + actPlayer);
            control.getPersonCtrlManager().getPersonCtrl(actPlayer).getPerson().getCurrentRoom().printInfo();
            control.getPersonCtrlManager().getPersonCtrl(actPlayer).getPerson().printInfo(true);

            CmdResult result;
            do {
                String line = scanner.nextLine();
                result = cmdHandler.executeCmd(line);
                if (!result.isCmdSuccess()) {
                    System.out.println(result.getMsg());
                }
            } while (!result.isCmdSuccess());
            control.processTick();
        }
        System.out.println("Victory! The game has ended.");
    }
}
