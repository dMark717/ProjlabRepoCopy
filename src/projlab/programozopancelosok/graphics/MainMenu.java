package projlab.programozopancelosok.graphics;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.CompletableFuture;

/**
 * The main menu of the game.
 */

public class MainMenu extends JFrame {
    class TitleListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            boolean demoVisibility = demo.isVisible();
            demo.setVisible(!demoVisibility);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    JLabel title;
    JButton singlePlayer;
    JButton multiPLayer;
    JButton exit;

    JPanel demo;
    JCheckBox disableTeacher;
    JCheckBox disableCleaner;
    JTextField randomMultiplier;


    /**
     * Constructs a MainMenu object.
     */

    public MainMenu() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Cím
        title = new JLabel(AssetHandler.TITLE);
        title.addMouseListener(new TitleListener());

        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 4;
        gbc.weighty = 2;
        this.add(title, gbc);

        //P1 button
        singlePlayer = new JButton("Singleplayer");
        singlePlayer.addActionListener((a) -> runGame(false));

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(singlePlayer, gbc);

        //P2 button
        multiPLayer = new JButton("Multiplayer");
        multiPLayer.addActionListener((a) -> runGame(true));

        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(multiPLayer, gbc);

        //Exit
        exit = new JButton("Exit");
        exit.addActionListener((a) -> exitGame());

        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(exit, gbc);


        demo = new JPanel(new FlowLayout());
        demo.setBorder(new TitledBorder("Demo Settings:"));

        disableTeacher = new JCheckBox("Disable Teacher Movement");
        demo.add(disableTeacher);

        disableCleaner = new JCheckBox("Disable Cleaner Movement");
        demo.add(disableCleaner);

        randomMultiplier = new JTextField("1", 3);
        demo.add(new JLabel("Event Multiplier:"));
        demo.add(randomMultiplier);


        gbc.gridx = 1;
        gbc.gridy = 4;
        this.add(demo, gbc);


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        demo.setVisible(false);
    }

    /**
     * Runs the game.
     *
     * @param isMultiplayer True if the game is multiplayer, false otherwise.
     */
    void runGame(boolean isMultiplayer) {
        double multiplier = 1;

        //Try to parse
        try {
            multiplier = Double.parseDouble(randomMultiplier.getText());
        } catch (Exception ignored) {
        }

        Screen screen = Screen.createScreen(isMultiplayer, disableTeacher.isSelected(), disableCleaner.isSelected(), multiplier);

        //Starts the game on a new thread.
        CompletableFuture.runAsync(() -> {
            setVisible(false);
            try {
                screen.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).thenRun(() -> setVisible(true));
    }

    void exitGame() {
        System.exit(0);
    }
}
