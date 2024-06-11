package projlab.programozopancelosok.graphics;

import projlab.programozopancelosok.control.Control;
import projlab.programozopancelosok.control.GameData;
import projlab.programozopancelosok.control.GameState;
import projlab.programozopancelosok.entity.Cleaner;
import projlab.programozopancelosok.entity.Teacher;
import projlab.programozopancelosok.entity.personcontroller.BasicAIController;
import projlab.programozopancelosok.entity.personcontroller.DummyPersonController;
import projlab.programozopancelosok.entity.personcontroller.PersonController;
import projlab.programozopancelosok.entity.personcontroller.input.InputPersonController;
import projlab.programozopancelosok.entity.personcontroller.input.Layout;
import projlab.programozopancelosok.item.*;
import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.room.RoomManager;
import projlab.programozopancelosok.room.strategy.PoisonStrategy;
import projlab.programozopancelosok.util.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Screen extends JFrame {
    /**
     * The control object.
     */
    Control control;
    /**
     * The player views.
     */
    PlayerView[] playerViews = new PlayerView[4];
    /**
     * The number of players.
     */
    int playerCount;


    /**
     * Constructs a screen object.
     *
     * @param control The control object.
     */
    private Screen(Control control) {
        this.control = control;

        //Csak maga a screen
    }


    /**
     * Builds the map for the game.
     *
     * @param control The control object.
     */
    private static void buildLevel(Control control, boolean isTeacherDisabled, boolean isCleanerDisabled) {

        GameData gameData = control.getGameData();
        //region Labyrinth init
        //region Rooms init

        /**
         * The room where the teacher is.
         */
        String teacherRoom = "K00";

        /**
         * The room where the equipment is stored.
         */
        String eqpRoom = "K76f";

        /**
         * Adding rooms to the game.
         */
        RoomManager roomManager = gameData.getRoomManager();
        roomManager.addRoom(new Room("K01", 10));
        roomManager.addRoom(new Room("K02", 2));
        roomManager.addRoom(new Room("K03", 3));
        roomManager.addRoom(new Room("K04", 5));
        roomManager.addRoom(new Room("K05", 5));
        roomManager.addRoom(new Room("K06", 5));
        roomManager.addRoom(new Room("K07", 5));
        roomManager.addRoom(new Room("K08", 5));
        roomManager.addRoom(new Room("K09", 4));
        roomManager.addRoom(new Room("K10", 5));
        roomManager.addRoom(new Room("K11", 2));
        roomManager.addRoom(new Room("K12", 1));
        roomManager.addRoom(new Room(teacherRoom, 3));
        roomManager.addRoom(new Room(eqpRoom, 2));
        roomManager.addRoom(new Room("WC1", 1));
        roomManager.addRoom(new Room("WC2", 2));

        roomManager.addRoom(new Room("WC3", 2));
        roomManager.addRoom(new Room("Sewer", 3,true));

        /**
         * Adding doors to connect the rooms.
         */

        roomManager.createDoorBetween("d1-4", "K01", "K04", false);
        roomManager.createDoorBetween("d1-3", "K01", "K03", false);
        roomManager.createDoorBetween("d1-2", "K01", "K02", true);
        roomManager.createDoorBetween("d12-3", "K12", "K03", true);
        roomManager.createDoorBetween("d3-10", "K03", "K10", false);
        roomManager.createDoorBetween("d4-WC1", "K04", "WC1", true);
        roomManager.createDoorBetween("d2-10", "K02", "K10", true);
        roomManager.createDoorBetween("d2-8", "K02", "K08", false);
        roomManager.createDoorBetween("d10-5", "K10", "K05", false);
        roomManager.createDoorBetween("d5-11", "K05", "K11", false);
        roomManager.createDoorBetween("d5-12", "K05", "K12", true);
        roomManager.createDoorBetween("d10-WC2", "K10", "WC2", false);
        roomManager.createDoorBetween("dWC2-5", "WC2", "K05", false);
        roomManager.createDoorBetween("d7-WC2", "K07", "WC2", true);
        roomManager.createDoorBetween("d7-2", "K07", "K02", true);
        roomManager.createDoorBetween("d7-8", "K07", "K08", false);
        roomManager.createDoorBetween("d8-9", "K08", "K09", true);
        roomManager.createDoorBetween("dT9", "K09", teacherRoom, true);
        roomManager.createDoorBetween("dT8", teacherRoom, "K08", false);
        roomManager.createDoorBetween("dT11", teacherRoom, "K11", false);
        roomManager.createDoorBetween("d11-10", "K11", "K10", false);
        roomManager.createDoorBetween("dT12", teacherRoom, "K12", false);
        roomManager.createDoorBetween("dTWC", teacherRoom, "WC1", false);
        roomManager.createDoorBetween("d8ER", "K08", eqpRoom, true);
        roomManager.createDoorBetween("d8WC1", "K08", "WC1", false);
        roomManager.createDoorBetween("dERWC", "WC1", eqpRoom, false);


        roomManager.createDoorBetween("df1", "K04", eqpRoom, true);
        roomManager.createDoorBetween("df2", "K03", "WC3", true);
        roomManager.createDoorBetween("df3", "WC1", "Sewer", true);


        roomManager.getRoom("K03").addStrategy(new PoisonStrategy());
        roomManager.getRoom("K09").addStrategy(new PoisonStrategy());
        roomManager.getRoom("K05").addStrategy(new PoisonStrategy());

        //endregion

        //region Tárgyak

        /**
         * Adding items to the equipment room.
         */
        roomManager.insertItem(new FFP2(true, false), eqpRoom);
        roomManager.insertItem(new AirFreshener(true, false), eqpRoom);
        roomManager.insertItem(new HolyBeerCup(true, false), eqpRoom);
        roomManager.insertItem(new Transistor(true, false), eqpRoom);
        roomManager.insertItem(new Transistor(true, false), eqpRoom);
        roomManager.insertItem(new TVSZ(true, false), eqpRoom);


        /**
         * Add the logar item to the teacher room.
         */
        roomManager.insertItem(new Logar(true, false, control), teacherRoom);


        //K4-be ragadós tárgy

        /**
         * Add a sticky and false item to room K03.
         */
        roomManager.insertItem(new Logar(false, true, control), "K03");


        roomManager.insertItem(new FFP2(true, false), "K02");
        roomManager.insertItem(new AirFreshener(true, false), "K08");
        roomManager.insertItem(new HolyBeerCup(true, false), "K05");
        roomManager.insertItem(new Transistor(true, false), "K12");
        roomManager.insertItem(new TVSZ(true, false), "K10");
        roomManager.insertItem(new FFP2(true, false), "K07");
        roomManager.insertItem(new AirFreshener(true, false), "WC1");
        roomManager.insertItem(new HolyBeerCup(true, false), "WC3");
        roomManager.insertItem(new Transistor(true, false), "K07");
        roomManager.insertItem(new TVSZ(true, false), "K05");
        roomManager.insertItem(new TVSZ(false, false), "K04");
        roomManager.insertItem(new FFP2(false, false), "K01");
        roomManager.insertItem(new Camembert(true, false), "K01");
        //endregion


        //Adding teachers to the game.
        //Gajdos
        Teacher gajdos = new Teacher("Gajdos");
        PersonController gajdosCtrl;

        if (!isTeacherDisabled) gajdosCtrl = new BasicAIController(gajdos, gameData, 700, 1500);
        else gajdosCtrl = new DummyPersonController(gajdos, gameData);

        gameData.addPersonCtrl(gajdosCtrl, teacherRoom);

        //Tasnadi
        Teacher tasnadi = new Teacher("Tasnadi");
        PersonController tasnadiCtrl;

        if (!isTeacherDisabled) tasnadiCtrl = new BasicAIController(tasnadi, gameData, 700, 1500);
        else tasnadiCtrl = new DummyPersonController(tasnadi, gameData);

        gameData.addPersonCtrl(tasnadiCtrl, teacherRoom);

        // Adding cleaner to the game, cleaner starts from WC2
        //Mr. Cleaner
        Cleaner mrClean = new Cleaner("Mr. Clean");
        PersonController mrCleanCtrl;

        if (!isCleanerDisabled) mrCleanCtrl = new BasicAIController(mrClean, gameData, 600, 1500);
        else mrCleanCtrl = new DummyPersonController(mrClean, gameData);

        gameData.addPersonCtrl(mrCleanCtrl, "WC2");

        //Mrs. Cleaner
        Cleaner mrsClean = new Cleaner("Mrs. Clean");
        PersonController mrsCleanCtrl;

        if (!isCleanerDisabled) mrsCleanCtrl = new BasicAIController(mrsClean, gameData, 200, 1500);
        else mrsCleanCtrl = new DummyPersonController(mrsClean, gameData);

        gameData.addPersonCtrl(mrsCleanCtrl, "WC3");
    }

    /**
     * Creates a screen object for the game.
     *
     * @param isMultiplayer True if the game is multiplayer, false otherwise.
     * @return The created screen object.
     */
    public static Screen createScreen(boolean isMultiplayer, boolean isTeacherDisabled, boolean isCleanerDisabled, double multiplier) {
        Control control = new Control(new Random());
        GameData gameData = control.getGameData();

        buildLevel(control, isTeacherDisabled, isCleanerDisabled);


        control.initRandomActions(multiplier);
        //endregion

        //region Creating the screen

        Screen screen;

        //P1 always exists
        InputPersonController p1Ctrl = InputPersonController.CreatePlayerAndCtrl("p1", gameData, Layout.P1_LAYOUT);
        gameData.addPersonCtrl(p1Ctrl, "K01");
        PlayerView playerView1 = new PlayerView(p1Ctrl, control);

        if (!isMultiplayer) {
            screen = new Screen(control, playerView1);
        } else {
            InputPersonController p2Ctrl = InputPersonController.CreatePlayerAndCtrl("p2", gameData, Layout.P2_LAYOUT);
            gameData.addPersonCtrl(p2Ctrl, "K01");
            PlayerView playerView2 = new PlayerView(p2Ctrl, control);
            screen = new Screen(control, playerView1, playerView2);
        }

        //endregion

        screen.init();

        return screen;
    }


    /**
     * Constructor for a one-player game.
     *
     * @param control    Kontroll
     * @param playerView A playerView
     */
    public Screen(Control control, PlayerView playerView) {
        this(control);
        playerViews[0] = playerView;
        playerCount = 1;

        build1playerGui();
        subscribePlayer();
    }

    /**
     * Constructor for a two-player game.
     *
     * @param control     Kontroll
     * @param playerView1 A playerView
     * @param playerView2 A playerView
     */
    public Screen(Control control, PlayerView playerView1, PlayerView playerView2) {
        this(control);
        playerViews[0] = playerView1;
        playerViews[1] = playerView2;
        playerCount = 2;

        build2playerGui();
        subscribePlayer();
    }

    /**
     * Builds the GUI for a one-player game.
     */

    private void build1playerGui() {
        this.add(playerViews[0]);
        this.setPreferredSize(new Dimension(750, 750));
    }

    /**
     * Builds the GUI for a two-player game.
     */

    private void build2playerGui() {
        this.add(playerViews[0], BorderLayout.WEST);
        this.add(playerViews[1], BorderLayout.EAST);
        this.setPreferredSize(new Dimension(1500, 750));
    }

    private void subscribePlayer() {
        for (int i = 0; i < playerCount; i++) {
            InputPersonController personCtrl = playerViews[i].getPlayerCtrl();
            personCtrl.subscribeToEvent(this);
        }
    }

    /**
     * Initializes the screen.
     */

    public void init() {
        this.setLayout(new GridLayout(1, 2, 10, 10));
        this.setTitle("K épület labirintus");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    /**
     * Runs the game.
     *
     * @throws Exception If an error occurs during the game.
     */
    public void run() throws Exception {
        this.setVisible(true);
        long tickExecTime = 0;
        long start = -1;
        long sleepTime = -1;
        final long TARGET_EXEC_TIME = Control.TICK_LENGTH_ms;
        try {
            while (control.getGameState().isRunning()) {
                start = System.currentTimeMillis();
                //-----
                gameLoop();
                graphicLoop();
                shouldSubmit32();
                //-----
                tickExecTime = System.currentTimeMillis() - start;
                sleepTime = TARGET_EXEC_TIME - tickExecTime;

                if (sleepTime < 0) {
                    System.out.println((-sleepTime) + "ms behind!");
                    continue;
                }

                Thread.sleep(sleepTime);
            }
        } catch (Exception e) {
            control.setGameState(GameState.ERROR);
        }
        gameFinished();
    }

    private void gameLoop() {
        control.processTick();
    }

    private void graphicLoop() {

        for (int i = 0; i < playerCount; i++) {
            PlayerView view = playerViews[i];
            view.onTick();
        }

    }

    /**
     * Checks if there is at least one player alive (érted 32-es kérvény XDDDD)
     */
    private void shouldSubmit32() {
        int deadPlayerCount = 0;

        for (int i = 0; i < playerCount; i++) {
            InputPersonController personCtrl = playerViews[i].getPlayerCtrl();
            if (!personCtrl.getPerson().isAlive())
                deadPlayerCount++;
        }
        if (deadPlayerCount == playerCount)
            control.setGameState(GameState.DEFEAT);

    }

    /**
     * Handles the game ending.
     */

    private void gameFinished() {
        switch (control.getGameState()) {
            case VICTORY -> JOptionPane.showMessageDialog(this, "Gratulálunk, megszereszte a diplomát :)");
            case DEFEAT, ERROR -> JOptionPane.showMessageDialog(this, "Jöjjön legközelebb :(");
        }

        this.setVisible(false);
        this.dispose();
        Logger.DEBUG_PRINTLN("Window closed.");
    }
}
