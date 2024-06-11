package projlab.programozopancelosok.graphics;

/**
 * The main class of the graphical user interface.
 */
public class GraphicMain {
    static MainMenu mainMenu;

    /**
     * The main method of the graphical user interface.
     *
     * @param args The command line arguments.
     */

    public static void main(String[] args) {
        mainMenu = new MainMenu();

        mainMenu.setVisible(true);
    }
}
