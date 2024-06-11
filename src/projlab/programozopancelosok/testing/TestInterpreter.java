package projlab.programozopancelosok.testing;

import projlab.programozopancelosok.commands.CmdHandler;
import projlab.programozopancelosok.commands.CmdResult;
import projlab.programozopancelosok.control.Control;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class TestInterpreter {

    public static void main(String[] args) throws Exception {
        String filepath = args[1];
        System.out.println("Running test at \"" + filepath + "\".");
        List<String> lines = Files.readAllLines(Paths.get(filepath));

        Control control = new Control(new Random());

        CmdHandler cmdHandler = new CmdHandler(control);
        cmdHandler.registerTestCmds();

        int testC = 1;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            CmdResult result = cmdHandler.executeCmd(line);

            if (!result.isCmdSuccess()) {
                if (result == CmdResult.ASSERT_FAIL) {
                    System.out.println("TEST " + testC + " FAILED!");
                    System.out.println("\tat line " + (i + 1));
                } else
                    System.out.println("Error at line " + (i + 1) + ": \"" + line + "\"\n\t" + result.getMsg());

                System.out.println("Test aborted.");
                return;
            }

            if (result == CmdResult.ASSERT_SUCCESS) {
                System.out.println("TEST " + testC++ + " SUCCESS");
                System.out.println("(up to line " + (i + 1) + ")\n");
            }

            control.processTick();
        }

        System.out.println("All test were successful.");
    }
}
