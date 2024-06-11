package projlab.programozopancelosok;

import projlab.programozopancelosok.game.GameMain;
import projlab.programozopancelosok.graphics.GraphicMain;
import projlab.programozopancelosok.testing.TestInterpreter;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 0) {
                if (args[0].equals("test")) {
                    TestInterpreter.main(args);
                } else if (args[0].equals("text_game")) {
                    GameMain.main(args);
                }
            }
            GraphicMain.main(args);

        } catch (Exception e) {
            System.out.println("Exception during testing:");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}