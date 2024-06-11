package projlab.programozopancelosok.graphics;

import projlab.programozopancelosok.control.Control;
import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.entity.personcontroller.input.InputPersonController;
import projlab.programozopancelosok.graphics.swing.SelectableList;
import projlab.programozopancelosok.item.Item;
import projlab.programozopancelosok.item.Transistor;
import projlab.programozopancelosok.room.Door;
import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.util.Flagger;
import projlab.programozopancelosok.util.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * The view of the player.
 */
public class PlayerView extends JPanel {
    private final InputPersonController playerCtrl;
    Control control;
    Room roomInView;
    JPanel mainPanel;


    JPanel playerInfoPanel;
    SelectableList roomInventory;
    SelectableList playerInventory;
    JPanel playerStatus;
    JPanel roomPanel;
    JPanel peopleInRoomPanel;

    SelectableList doors;

    /**
     * Constructs a PlayerView object.
     *
     * @param playerController The controller of the player.
     * @param control          The control object of the game.
     */
    public PlayerView(InputPersonController playerController, Control control) {
        super(new BorderLayout());
        playerCtrl = playerController;
        this.control = control;

        roomInView = playerCtrl.getPerson().getCurrentRoom();

        buildAll();
        this.add(mainPanel, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(720, 720));
    }

    /**
     * Updates everything that ha changed since the last tick on the display.
     */
    public void onTick() {
        fullUpdate();
    }

    /**
     * Returns the controller of the player.
     *
     * @return The controller of the player.
     */

    public InputPersonController getPlayerCtrl() {
        return playerCtrl;
    }

    //region Updates

    /**
     * Updates everything on the display.
     */
    private void fullUpdate() {
        roomInView = playerCtrl.getPerson().getCurrentRoom();

        this.remove(mainPanel);
        buildAll();
        this.add(mainPanel);
        this.validate();
    }

    //endregion

    /**
     * Builds everything on the display.
     */
    private void buildAll() {
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel = new JPanel(new GridBagLayout());

        buildPlayerInfo();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 4;
        gbc.weighty = 1;
        mainPanel.add(playerInfoPanel, gbc);


        buildRoom();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 4;
        gbc.weighty = 4;
        mainPanel.add(roomPanel, gbc);

        buildDoors();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 3;
        mainPanel.add(doors, gbc);
        mainPanel.add(new JLabel("Player: " + playerCtrl.getPerson().getNeptun()), gbc);
    }

    //region Build PlayerInfo

    /**
     * Builds the player's information on the display.
     */
    private void buildPlayerInfo() {
        playerInfoPanel = new JPanel(new GridLayout(1, 2));
        playerInfoPanel.setBackground(Color.orange);
        buildPlayerInventory();
        playerInfoPanel.add(playerInventory);
        buildPlayerStatus();
        playerInfoPanel.add(playerStatus);
    }

    /**
     * Builds the player's inventory on the display.
     */
    private void buildPlayerInventory() {
        playerInventory = new SelectableList(Color.BLACK);
        Person player = playerCtrl.getPerson();
        if (player.getInventory().size() > 0) {
            for (Item item : player.getInventory()) {
                playerInventory.addToList(new JLabel(item.toString()));
            }

            playerInventory.setSelectedIndex(playerCtrl.getSelectedInvItemIndex());
        } else {
            playerInventory.addToList(new JLabel("No items"));
        }

        playerInventory.setBorder(new TitledBorder("Inventory:"));
        //ASD
    }

    /**
     * Builds the player's status on the display.
     */
    private void buildPlayerStatus() {
        playerStatus = new JPanel(new FlowLayout());
        //String.format("%10.2f",val)
        playerStatus.add(new JLabel("Mask time: " + String.format("%10.2f", playerCtrl.getPerson().getMaskTime())));
        playerStatus.add(new JLabel("Invincibility time: " + String.format("%10.2f", playerCtrl.getPerson().getInvincibilityTime())));
        playerStatus.add(new JLabel("Stun time: " + String.format("%10.2f", playerCtrl.getPerson().getStunTime())));

        if (!playerCtrl.getPerson().isAlive()) playerStatus.add(new JLabel("PLAYER IS DEAD :("));
        playerStatus.setPreferredSize(new Dimension(200, 50));

        playerStatus.setBorder(new TitledBorder("Status:"));
    }


    //endregion

    //region Build Room
    private static final Color roomBgColor = new Color(0xD3D3D3);
    private static final Color roomPoisonBgColor = new Color(0x5AD232);
    private static final Color roomPoisonTextColor = new Color(0x306E1B);

    /**
     * Builds the room on the display.
     */
    private void buildRoom() {
        roomPanel = new JPanel(new BorderLayout());
        Room room = playerCtrl.getPerson().getCurrentRoom();

        Color roomColor;
        if (room.isPoisonous())
            roomColor = roomPoisonBgColor;
        else
            roomColor = roomBgColor;


        buildRoomPeople();
        roomPanel.setBackground(roomColor);
        roomPanel.add(peopleInRoomPanel, BorderLayout.NORTH);

        JLabel nameLabel = new JLabel(playerCtrl.getPerson().getCurrentRoom().getRoomCode());
        Font oldFont = nameLabel.getFont();
        nameLabel.setFont(new Font(oldFont.getName(), oldFont.getStyle(), 55));
        nameLabel.setBackground(roomColor);
        roomPanel.add(nameLabel, BorderLayout.CENTER);


        if (room.isPoisonous())
            nameLabel.setForeground(roomPoisonTextColor);
        else
            nameLabel.setForeground(Color.GRAY);


        //placed items (transistors)
        JPanel placedItemsPanel = new JPanel(new GridLayout(3, 1));
        placedItemsPanel.setBackground(roomColor);
        if (!room.getTransistorsInRoom().isEmpty()) {
            placedItemsPanel.add(new JLabel("Transistors: "));
            for (Transistor t : room.getTransistorsInRoom())
                placedItemsPanel.add(new JLabel(t.toString()));
        }
        roomPanel.add(placedItemsPanel, BorderLayout.EAST);
        //-------lerakott tárgyak (tranzisztorok vége)

        buildRoomInventory();
        roomInventory.setBackground(roomColor);
        roomPanel.add(roomInventory, BorderLayout.SOUTH);

    }

    /**
     * Builds the people in the room on the display.
     */
    private void buildRoomPeople() {
        peopleInRoomPanel = new JPanel();

        if (!roomInView.getPeopleInRoom().isEmpty()) {
            peopleInRoomPanel.add(new JLabel("People in room: "));
            for (Person person : roomInView.getPeopleInRoom()) {
                peopleInRoomPanel.add(new JLabel(person.getNeptun()));
            }
        } else {
            peopleInRoomPanel.add(new JLabel("No person in room."));
        }
    }

    /**
     * Builds the room's inventory on the display.
     */
    private void buildRoomInventory() {
        roomInventory = new SelectableList(Color.BLACK);
        roomInventory.setBackground(Color.RED);

        if (!roomInView.getItemsInRoom().isEmpty()) {
            for (Item item : roomInView.getItemsInRoom()) {
                if (item.isSticky()) roomInventory.addToList(new JLabel("(sticky) " + item.toString()));
                else roomInventory.addToList(new JLabel(item.toString()));
            }
            roomInventory.setSelectedIndex(playerCtrl.getSelectedRoomItemIndex());
        } else {
            roomInventory.addToList(new JLabel("No items in room."));
        }
        roomInventory.setSelectedIndex(playerCtrl.getSelectedRoomItemIndex());
    }

    //endregion

    /**
     * Builds the doors on the display.
     */
    private void buildDoors() {
        doors = new SelectableList(Color.BLACK);
        for (Door d : roomInView.getDoorsInRoom()) {
            Room opposite = d.getOpposite(roomInView);
            JPanel doorPanel = new JPanel();

            ImageIcon image = AssetHandler.getDoorIcon(d, getPlayerCtrl().getPerson());

            doorPanel.add(new JLabel(image));
            doorPanel.add(new JLabel(opposite.getRoomCode()));

            doors.addToList(doorPanel);
        }
        doors.setSelectedIndex(playerCtrl.getSelectedDoorIndex());
    }
}
