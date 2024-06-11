package projlab.programozopancelosok.commands;

import projlab.programozopancelosok.control.Control;
import projlab.programozopancelosok.control.GameData;
import projlab.programozopancelosok.control.GameState;
import projlab.programozopancelosok.entity.personcontroller.BasicAIController;
import projlab.programozopancelosok.entity.personcontroller.DummyPersonController;
import projlab.programozopancelosok.entity.personcontroller.PersonController;
import projlab.programozopancelosok.entity.*;
import projlab.programozopancelosok.item.*;
import projlab.programozopancelosok.room.Door;
import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.room.RoomManager;
import projlab.programozopancelosok.util.Logger;
import projlab.programozopancelosok.util.Util;

import javax.swing.text.html.parser.Parser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CmdHandler {

    private final Control control;
    private final GameData gameData;
    private final RoomManager roomManager;
    private final PersonCtrlManager personCtrlManager;
    private final Map<String, Command> commands;

    private String currentPlayerId;

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public Logger logger;

    public static final String[] playerIds = {"p1", "p2", "p3", "p4"};

    public CmdHandler(Control control) {
        this.control = control;
        gameData = control.getGameData();
        roomManager = control.getRoomManager();
        personCtrlManager = control.getPersonCtrlManager();
        commands = new HashMap<>();
        currentPlayerId = "p1";
    }


    public void registerTestCmds() {
        registerCmd(new Command("create", this::create));
        registerCmd(new Command("connect", this::connect));
        registerCmd(new Command("split", this::split));
        registerCmd(new Command("merge", this::merge));
        registerCmd(new Command("skip", this::skip));
        registerCmd(new Command("ct_move", this::person_move));
        registerCmd(new Command("ct_pickup", this::person_pickup));
        registerCmd(new Command("ct_select", this::person_select));
        registerCmd(new Command("ct_use", this::person_use));
        registerCmd(new Command("ct_drop", this::person_drop));
        registerCmd(new Command("assert", this::assertCmd));
        registerCmd(new Command("print_all", this::print_all));
        registerCmd(new Command("ct_connect_trans", this::trans_connect_ct));
        registerCmd(new Command("ct_place", this::trans_place));
    }

    public void registerGameCmds() {
        registerCmd(new Command("move", this::player_move));
        registerCmd(new Command("pickup", this::player_pickup));
        registerCmd(new Command("select", this::player_select));
        registerCmd(new Command("use", this::player_use));
        registerCmd(new Command("drop", this::player_drop));
        registerCmd(new Command("connect", this::player_connect_trans));
        registerCmd(new Command("place", this::player_place_trans));
        registerCmd(new Command("switch_player", this::switch_player));
        registerCmd(new Command("end", this::end));
    }

    private void registerCmd(Command command) {
        commands.put(command.getName(), command);
    }

    public CmdResult executeCmd(String rawCmd) {
        String wholeCmd = rawCmd.trim();
        //Ha komment, akkor sikeres
        if (wholeCmd.equals("") || wholeCmd.charAt(0) == '#') return CmdResult.COMMENT;

        String[] parts = wholeCmd.split(" ");


        if (!commands.containsKey(parts[0])) return CmdResult.ERR_BAD_CMD;

        Command cmd = commands.get(parts[0]);
        return cmd.run(parts);
    }

    //region Init cmds
    private CmdResult create(String[] cmd) {
        return switch (cmd[1]) {
            case "room" -> create_Room(cmd);
            case "item" -> create_Item(cmd);
            default -> create_Person(cmd);
        };
    }

    private CmdResult create_Person(String[] cmd) {
        if (cmd.length < 5) return CmdResult.ERR_BAD_PARAM;
        String type = cmd[1];
        String id = cmd[2];
        String room = cmd[3];
        String ctrlType = cmd[4];

        Person person;

        switch (type) {
            case "student" -> person = new Student(id);
            case "teacher" -> person = new Teacher(id);
            case "cleaner" -> person = new Cleaner(id);
            default -> {
                return CmdResult.ERR_BAD_PARAM;
            }
        }

        PersonController personCtrl;

        switch (ctrlType) {
            case "dummy" -> personCtrl = new DummyPersonController(person, control.getGameData());
            case "ai_basic" -> personCtrl = new BasicAIController(person, control.getGameData(), 3, 5);
            default -> {
                return CmdResult.ERR_BAD_PARAM;
            }
        }

        control.getGameData().addPersonCtrl(personCtrl, room);
        logger.TEST_PRINTLN(type + " created with id: " + id + ", in room " + room);
        return CmdResult.SUCCESS;
    }

    private CmdResult create_Room(String[] cmd) {
        if (cmd.length < 4) return CmdResult.ERR_BAD_PARAM;
        String id = cmd[2];

        int capacity;

        boolean poisonous = false;

        try {
            capacity = Integer.parseInt(cmd[3]);
            if (cmd.length > 4)
                poisonous = Util.parseBoolean(cmd[4]);
        } catch (Exception e) {
            return CmdResult.ERR_BAD_PARAM;
        }

        roomManager.addRoom(new Room(id, capacity, poisonous));

        if (poisonous) Logger.TEST_PRINTLN("Room created with id: " + id + " and is poisonous");
        Logger.TEST_PRINTLN("Room created with id: " + id);

        return CmdResult.SUCCESS;
    }

    private CmdResult create_Item(String[] cmd) {
        if (cmd.length < 4) return CmdResult.ERR_BAD_PARAM;
        String itemType = cmd[2];
        String roomId = cmd[3];
        boolean isValid = true;
        boolean isSticky = false;


        try {
            if (cmd.length > 4) {
                isValid = Util.parseBoolean(cmd[4]);
            }
            if (cmd.length > 5) {
                isSticky = Util.parseBoolean(cmd[5]);
            }
        } catch (Exception e) {
            return CmdResult.ERR_BAD_PARAM;
        }


        Item item = itemFromType(itemType, isValid, isSticky);
        if (item == null) return CmdResult.ERR_BAD_PARAM;

        roomManager.insertItem(item, roomId);
        Logger.TEST_PRINT(itemType + " created in room " + roomId);
        if (isValid) Logger.TEST_PRINT(" and is valid");
        if (isSticky) Logger.TEST_PRINT(" and is sticky");
        Logger.TEST_PRINTLN("");
        return CmdResult.SUCCESS;
    }

    private Item itemFromType(String type, boolean isValid, boolean isSticky) {
        return switch (type) {
            default -> null;
            case "air_freshener" -> new AirFreshener(isValid, isSticky);
            case "board_cleaner" -> new BoardCleaner(isValid, isSticky);
            case "camembert" -> new Camembert(isValid, isSticky);
            case "ffp2" -> new FFP2(isValid, isSticky);
            case "holy_beer" -> new HolyBeerCup(isValid, isSticky);
            case "logar" -> new Logar(isValid, isSticky, control);
            case "transistor" -> new Transistor(isValid, isSticky);
            case "tvsz" -> new TVSZ(isValid, isSticky);
        };
    }

    private CmdResult connect(String[] cmd) {
        if (cmd.length < 4) return CmdResult.ERR_BAD_PARAM;
        String room1 = cmd[1];
        String room2 = cmd[2];
        String doorId = cmd[3];

        if (!roomManager.isValidRoomId(room1) || !roomManager.isValidRoomId(room2))
            return CmdResult.ERR_ID_NOT_FOUND;

        boolean isTwoWay = true;
        boolean isOpen = true;

        try {
            if (cmd.length > 4)
                isTwoWay = Util.parseBoolean(cmd[4]);
            if (cmd.length > 5)
                isOpen = Util.parseBoolean(cmd[5]);
        } catch (Exception e) {
            return CmdResult.ERR_BAD_PARAM;
        }

        roomManager.createDoorBetween(doorId, room1, room2, isTwoWay, isOpen);
        Logger.TEST_PRINTLN(room1 + " connected with " + room2);

        return CmdResult.SUCCESS;
    }

    private CmdResult split(String[] cmd) {
        if (cmd.length < 2) return CmdResult.ERR_BAD_PARAM;
        String roomId = cmd[1];

        if (!roomManager.isValidRoomId(roomId))
            return CmdResult.ERR_ID_NOT_FOUND;

        roomManager.splitRoom(roomId);
        Logger.TEST_PRINTLN(roomId + " splitted.");

        return CmdResult.SUCCESS;
    }

    private CmdResult merge(String[] cmd) {
        if (cmd.length < 3) return CmdResult.ERR_BAD_PARAM;
        String room1Id = cmd[1];
        String room2Id = cmd[2];

        if (!roomManager.isValidRoomId(room1Id) || !roomManager.isValidRoomId(room2Id))
            return CmdResult.ERR_ID_NOT_FOUND;
        roomManager.mergeRoomWith(room1Id, room2Id);
        logger.TEST_PRINTLN(room1Id + " and " + room2Id + " merged into a new room: " + room1Id);
        return CmdResult.SUCCESS;
    }

    private CmdResult skip(String[] cmd) {
        int ticks = 1;

        try {
            if (cmd.length > 1)
                ticks = Integer.parseInt(cmd[1]);
        } catch (Exception e) {
            return CmdResult.ERR_BAD_PARAM;
        }

        control.skip(ticks);
        logger.TEST_PRINTLN("Skipped " + ticks + " ticks");
        return CmdResult.SUCCESS;
    }
    //endregion

    //region Person cmds
    private CmdResult person_move(String[] cmd) {
        if (cmd.length < 3) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!personCtrlManager.isValidNeptun(neptun)) return CmdResult.ERR_ID_NOT_FOUND;
        int index = -1;

        try {
            index = Integer.parseInt(cmd[2]);
        } catch (Exception e) {
            return CmdResult.ERR_BAD_PARAM;
        }

        PersonController personCtrl = personCtrlManager.getPersonCtrl(neptun);
        if (personCtrl.moveTo(index)) {
            logger.TEST_PRINTLN(neptun + " moved through door with index: " + index);
        } else {
            logger.TEST_PRINTLN(neptun + " couldn't enter through door with index: " + index);
        }
        return CmdResult.SUCCESS;
    }

    private CmdResult person_pickup(String[] cmd) {
        if (cmd.length < 3) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!personCtrlManager.isValidNeptun(neptun)) return CmdResult.ERR_ID_NOT_FOUND;

        int index = -1;

        try {
            index = Integer.parseInt(cmd[2]);
        } catch (Exception e) {
            return CmdResult.ERR_BAD_PARAM;
        }

        PersonController personCtrl = personCtrlManager.getPersonCtrl(neptun);
        boolean couldPickUp = personCtrl.pickupItem(index);
        if (couldPickUp) logger.TEST_PRINTLN(neptun + " picked up item with index: " + index);
        else logger.TEST_PRINTLN(neptun + " could not pick up item, inventory full or sticky item");

        return CmdResult.SUCCESS;
    }

    private CmdResult person_select(String[] cmd) {
        if (cmd.length < 3) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!personCtrlManager.isValidNeptun(neptun)) return CmdResult.ERR_ID_NOT_FOUND;
        int index = -1;

        try {
            index = Integer.parseInt(cmd[2]);
        } catch (Exception e) {
            return CmdResult.ERR_BAD_PARAM;
        }

        PersonController personCtrl = personCtrlManager.getPersonCtrl(neptun);
        personCtrl.selectItem(index);
        Logger.TEST_PRINTLN(neptun + " selected item with index: " + index);
        return CmdResult.SUCCESS;
    }

    private CmdResult person_use(String[] cmd) {
        if (cmd.length < 1) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!personCtrlManager.isValidNeptun(neptun)) return CmdResult.ERR_ID_NOT_FOUND;

        PersonController personCtrl = personCtrlManager.getPersonCtrl(neptun);

        personCtrl.useSelectedItem();
        Logger.TEST_PRINTLN(neptun + " used the selected item");
        return CmdResult.SUCCESS;
    }

    private CmdResult person_drop(String[] cmd) {
        if (cmd.length < 1) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!personCtrlManager.isValidNeptun(neptun)) return CmdResult.ERR_ID_NOT_FOUND;

        PersonController personCtrl = personCtrlManager.getPersonCtrl(neptun);

        personCtrl.dropSelectedItem();
        Logger.TEST_PRINTLN(neptun + " dropped the selected item");
        return CmdResult.SUCCESS;
    }


    private CmdResult trans_connect_ct(String[] cmd) {
        if (cmd.length < 1) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!personCtrlManager.isValidNeptun(neptun)) return CmdResult.ERR_ID_NOT_FOUND;

        PersonController personCtrl = personCtrlManager.getPersonCtrl(neptun);
        int id0;
        int id1;
        try {
            id0 = Integer.parseInt(cmd[2]);
            id1 = Integer.parseInt(cmd[3]);
        } catch (Exception e) {
            return CmdResult.ERR_BAD_PARAM;
        }


        if (!personCtrl.connectTrans(id0, id1)) return CmdResult.ERR_BAD_PARAM;
        logger.TEST_PRINTLN(neptun + " connected 2 transistors");
        return CmdResult.SUCCESS;
    }

    private CmdResult trans_place(String[] cmd) {
        if (cmd.length < 1) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!personCtrlManager.isValidNeptun(neptun)) return CmdResult.ERR_ID_NOT_FOUND;
        if (!roomManager.isValidRoomId(cmd[2])) return CmdResult.ERR_ID_NOT_FOUND;

        PersonController personCtrl = personCtrlManager.getPersonCtrl(neptun);
        int id;

        try {
            id = Integer.parseInt(cmd[3]);

        } catch (Exception e) {
            return CmdResult.ERR_BAD_PARAM;
        }


        if (!personCtrl.placeTrans(id, cmd[2])) return CmdResult.ERR_BAD_PARAM;
        logger.TEST_PRINTLN(neptun + " placed a transistor in room: " + cmd[2]);
        return CmdResult.SUCCESS;
    }
    //endregion

    //region Player commands

    private boolean isPlayerId(String id) {
        if (id.length() != 2) return false;
        return (id.charAt(0) == 'p' && (id.charAt(1) >= '1' && id.charAt(1) <= '4'));
    }

    private CmdResult player_move(String[] cmd) {
        if (cmd.length < 2) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!isPlayerId(neptun)) return CmdResult.ERR_NOT_PLAYER;
        return person_move(cmd);
    }

    private CmdResult player_pickup(String[] cmd) {
        if (cmd.length < 2) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!isPlayerId(neptun)) return CmdResult.ERR_NOT_PLAYER;
        return person_pickup(cmd);
    }

    private CmdResult player_select(String[] cmd) {
        if (cmd.length < 2) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!isPlayerId(neptun)) return CmdResult.ERR_NOT_PLAYER;
        return person_select(cmd);
    }

    private CmdResult player_use(String[] cmd) {
        if (cmd.length < 2) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!isPlayerId(neptun)) return CmdResult.ERR_NOT_PLAYER;
        return person_use(cmd);
    }

    private CmdResult player_drop(String[] cmd) {
        if (cmd.length < 3) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!isPlayerId(neptun)) return CmdResult.ERR_NOT_PLAYER;
        return person_drop(cmd);
    }

    private CmdResult player_connect_trans(String[] cmd) {
        if (cmd.length < 3) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!isPlayerId(neptun)) return CmdResult.ERR_NOT_PLAYER;
        return trans_connect_ct(cmd);
    }

    private CmdResult player_place_trans(String[] cmd) {
        if (cmd.length < 2) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!isPlayerId(neptun)) return CmdResult.ERR_NOT_PLAYER;
        PersonController personCtrl = personCtrlManager.getPersonCtrl(neptun);
        String[] cmdn = new String[cmd.length + 1];
        String key = "";
        for (String k : roomManager.getRooms().keySet()) {
            if (roomManager.getRooms().get(k).equals(personCtrl.getPerson().getCurrentRoom())) {
                key = k;
            }
        }
        System.out.println(key);
        cmdn[0] = cmd[0];
        cmdn[1] = cmd[1];
        cmdn[2] = key;
        cmdn[3] = cmd[2];
        return trans_place(cmdn);
    }

    private CmdResult end(String[] strings) {
        control.skip(1);
        return CmdResult.SUCCESS;
    }

    //endregion

    private CmdResult assertCmd(String[] cmd) {
        if (cmd.length < 5)
            return CmdResult.ERR_BAD_PARAM;

        return switch (cmd[1]) {
            case "room" -> assert_Room(cmd);
            case "person" -> assert_Person(cmd);
            default -> CmdResult.ERR_BAD_PARAM;
        };
    }

    private CmdResult assertResultFrom(boolean doesEqual) {
        if (doesEqual)
            return CmdResult.ASSERT_SUCCESS;
        return CmdResult.ASSERT_FAIL;
    }

    private CmdResult switch_player(String[] cmd) {
        if (cmd.length < 2) return CmdResult.ERR_BAD_PARAM;
        String neptun = cmd[1];
        if (!isPlayerId(neptun)) return CmdResult.ERR_NOT_PLAYER;
        currentPlayerId = neptun;
        return CmdResult.SUCCESS;
    }

    private CmdResult assert_Room(String[] cmd) {
        String id = cmd[2];
        if (!roomManager.isValidRoomId(id)) return CmdResult.ERR_ID_NOT_FOUND;
        Room room = roomManager.getRoom(id);
        String field = cmd[3];
        String expected = cmd[4];

        String fieldValue = "";

        switch (field) {
            case "item_count" -> fieldValue += room.getItemCount();
            case "person_count" -> fieldValue += room.getPeopleInRoom().size();
            case "door_count" -> fieldValue += room.getDoorCount();
            case "capacity" -> fieldValue += room.getCapacity();
            case "is_full" -> fieldValue += room.isFull();
            default -> {
                return CmdResult.ERR_BAD_PARAM;
            }
        }
        return assertResultFrom(fieldValue.equals(expected));
    }

    private CmdResult assert_Person(String[] cmd) {
        String id = cmd[2];
        if (!personCtrlManager.isValidNeptun(id)) return CmdResult.ERR_ID_NOT_FOUND;
        PersonController pCtrl = personCtrlManager.getPersonCtrl(id);
        Person person = pCtrl.getPerson();
        String field = cmd[3];
        String expected = cmd[4];

        String fieldValue = "";

        switch (field) {
            case "mask_on" -> fieldValue += person.isMaskOn();
            case "is_invincible" -> fieldValue += person.isInvincible();
            case "is_stunned" -> fieldValue += person.isStunned();
            case "item_count" -> fieldValue += person.getItemCount();
            default -> {
                return CmdResult.ERR_BAD_PARAM;
            }
        }
        return assertResultFrom(fieldValue.equals(expected));

    }

    //Ne itelj el, csak tesztekre van es borzaszto cursed
    private CmdResult print_all(String[] cmd) {
        //emberek kiirasa
        Map<String, PersonController> personControllers = personCtrlManager.getPersonControllers();
        for (String key : personControllers.keySet()) {
            Person person = personControllers.get(key).getPerson();
            System.out.print(person.getNeptun() + "\n\tInventory: ");
            person.printInventory();
            System.out.println("\n\tCurrent room: " + person.getCurrentRoom().getRoomCode());
            System.out.print("\tIs mask in use: ");
            if (person.isMaskOn()) System.out.print("true");
            else System.out.print("false");
            System.out.print("\n\tMask time: ");
            person.printMaskTime();
            System.out.print("\n\tInvincibility time: ");
            person.printInvincibilityTime();
            System.out.print("\n\tStun time: ");
            person.printStunTime();
            System.out.println();
        }
        System.out.println();
        //szobak kiirasa
        Map<String, Room> rooms = roomManager.getRooms();
        for (String roomKey : rooms.keySet()) {
            Room room = rooms.get(roomKey);
            System.out.print(room.getRoomCode() + "\n\tDoors: ");
            Map<String, Door> doors = roomManager.getDoors();
            for (String doorKey : doors.keySet()) {
                Door door = doors.get(doorKey);
                if (door.getFrom() == room) {
                    System.out.print(door.getTo().getRoomCode() + " ");
                }
            }
            System.out.print(room.getRoomCode() + "\n\tItems in room: ");
            room.printItems();
            System.out.print("\n\tPlaced items: ");
            room.printPlacedItems();
            System.out.print("\n\tPeople in room: ");
            room.printPeople();
            System.out.print("\n\tActive strategies: ");
            room.printStrategies();
            System.out.println();
        }


        System.out.println();
        return CmdResult.SUCCESS;
    }

}
