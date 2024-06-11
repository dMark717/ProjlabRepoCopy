package projlab.programozopancelosok.room;

import projlab.programozopancelosok.control.GameData;
import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.item.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the rooms and doors in the game environment.
 */
public class RoomManager implements java.io.Serializable {

    /**
     * The game data associated with the manager.
     */
    final GameData gameData;

    /**
     * The map of rooms indexed by their unique IDs.
     */
    Map<String, Room> rooms;

    /**
     * The map of doors indexed by their unique IDs.
     */
    Map<String, Door> doors;

    /**
     * Constructs a RoomManager with the specified GameData.
     *
     * @param gameData The GameData associated with the manager.
     */
    public RoomManager(GameData gameData) {
        this.gameData = gameData;
        this.rooms = new HashMap<>();
        this.doors = new HashMap<>();
    }

    /**
     * Adds a room to the manager.
     *
     * @param room The room to add.
     */
    public void addRoom(Room room) {
        rooms.put(room.getRoomCode(), room);
    }

    /**
     * Creates a door between two rooms. The door will be open, and two-way.
     *
     * @param newDoorId  The ID of the new door.
     * @param roomFromID The ID of the first room.
     * @param roomToID   The ID of the second room.
     */
    public void createDoorBetween(String newDoorId, String roomFromID, String roomToID) {
        createDoorBetween(newDoorId, roomFromID, roomToID, true, true);
    }

    /**
     * Creates a door between two rooms. The door will be open, and two-way.
     *
     * @param newDoorId  The ID of the new door.
     * @param roomFromID The ID of the first room.
     * @param roomToID   The ID of the second room.
     * @param isTwoWay   Indicates if the door is two-way.
     */
    public void createDoorBetween(String newDoorId, String roomFromID, String roomToID, boolean isTwoWay) {
        createDoorBetween(newDoorId, roomFromID, roomToID, isTwoWay, true);
    }

    /**
     * Creates a door between two rooms.
     *
     * @param newDoorId  The ID of the new door.
     * @param isTwoWay   Indicates if the door is two-way.
     * @param isOpen     Indicates if the door is open.
     * @param roomFromID The ID of the first room.
     * @param roomToID   The ID of the second room.
     */
    public void createDoorBetween(String newDoorId, String roomFromID, String roomToID, boolean isTwoWay, boolean isOpen) {
        Room room1 = rooms.get(roomFromID);
        Room room2 = rooms.get(roomToID);
        Door door = room1.createDoorBetween(room2, isTwoWay, isOpen);
        doors.put(newDoorId, door);
    }

    /**
     * Splits the room with the specified ID.
     *
     * @param id The ID of the room to split.
     */
    public void splitRoom(String id) {
        Room room = rooms.get(id);
        String newCode = getNewRoomCode();
        Room newRoom = room.split(newCode);
        rooms.put(newCode, newRoom);
        String doorBetweenCode = getNewDoorCode();
        createDoorBetween(doorBetweenCode, id, newCode, true, true);

    }

    /**
     * Merges two rooms with the specified IDs.
     *
     * @param roomId1 The ID of the first room to merge.
     * @param roomId2 The ID of the second room to merge.
     */
    public void mergeRoomWith(String roomId1, String roomId2) {
        Room room1 = rooms.get(roomId1);
        Room room2 = rooms.get(roomId2);
        Room mergedRoom = room1.mergeWith(room2);
        rooms.remove(room1.getRoomCode());
        rooms.remove(room2.getRoomCode());

        for (Door d : mergedRoom.getDoorsInRoom()) {
            String newDoorId = getNewDoorCode();
            doors.put(newDoorId, d);
        }
    }

    /**
     * Removes the door with the specified ID.
     *
     * @param id The ID of the door to remove.
     */
    public void removeDoor(String id) {
        Door door = doors.get(id);
        Room room1 = door.getFrom();
        Room room2 = door.getTo();
        room1.removeDoorBetween(room2);
    }

    /**
     * Retrieves a random room ID.
     *
     * @return A random room ID.
     */
    public String getRandomRoomId() {
        int index = gameData.getRandom().nextInt(rooms.size());
        int i = 0;
        for (String id : rooms.keySet()) {
            if (i != index) i++;
            return id;
        }
        //Soha nem éri el
        return null;
    }

    public String getRandomDoor() {
        int index = gameData.getRandom().nextInt(doors.size());
        int i = 0;
        for (String id : doors.keySet()) {
            if (i != index) i++;
            return id;
        }
        return null;
    }

    /**
     * Performs actions on each room on a tick.
     */
    public void onTick() {
        for (String roomId : rooms.keySet()) {
            Room room = rooms.get(roomId);
            room.onTick();
        }
        for (String d : doors.keySet()) {
            Door door = doors.get(d);
            door.onTick();
        }
    }

    /**
     * Inserts a person into the specified room.
     *
     * @param person The person to insert.
     * @param id     The ID of the room to insert the person into.
     * @return The room where the person was inserted, or null if the room is full.
     */
    public Room insertPerson(Person person, String id) {
        Room room = rooms.get(id);
        if (room.isFull()) return null;
        person.visit(room);
        return room;
    }

    /**
     * Inserts an item into the specified room.
     *
     * @param item   The item to insert.
     * @param roomTo The ID of the room to insert the item into.
     * @return The inserted item.
     */
    public Item insertItem(Item item, String roomTo) {
        rooms.get(roomTo).addItem(item);
        return item;
    }

    /**
     * Prints information about rooms.
     */
    public void printInfo() {
        for (String id : rooms.keySet())
            System.out.println(id + ": People in room: " + rooms.get(id).getPeopleInRoom().size());
    }

    /**
     * Generates a new unique room code.
     *
     * @return A new unique room code.
     */
    private String getNewRoomCode() {
        String newCode = "";
        do {
            newCode = "K" + gameData.getRandom().nextInt(1, 1000);
        } while (rooms.containsKey(newCode));
        return newCode;
    }


    /**
     * Generates a new unique room code.
     *
     * @return A new unique room code.
     */
    private String getNewDoorCode() {
        String newCode = "";
        do {
            newCode = "d_G_" + gameData.getRandom().nextInt(1, 1000);
        } while (doors.containsKey(newCode));
        return newCode;
    }

    /**
     * Checks if the specified room ID is valid.
     *
     * @param roomId The ID of the room to check.
     * @return True if the room ID is valid, otherwise false.
     */
    public boolean isValidRoomId(String roomId) {
        return rooms.containsKey(roomId);
    }

    /**
     * Retrieves the room with the specified ID.
     *
     * @param roomId The ID of the room to retrieve.
     * @return The room with the specified ID.
     */
    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    /**
     * Retrieves the map of rooms managed by the RoomManager.
     *
     * @return The map of rooms.
     */
    public Map<String, Room> getRooms() {
        return rooms;
    }

    /**
     * Retrieves the map of doors managed by the RoomManager.
     *
     * @return The map of doors.
     */
    public Map<String, Door> getDoors() {
        return doors;
    }
}
