package projlab.programozopancelosok.room;

import projlab.programozopancelosok.entity.Cleaner;
import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.entity.Student;
import projlab.programozopancelosok.entity.Teacher;
import projlab.programozopancelosok.item.Item;
import projlab.programozopancelosok.item.Transistor;
import projlab.programozopancelosok.room.strategy.NormalStrategy;
import projlab.programozopancelosok.room.strategy.PoisonStrategy;
import projlab.programozopancelosok.room.strategy.RoomStrategy;
import projlab.programozopancelosok.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in the game.
 */
public class Room {
    /**
     * The capacity of the room.
     */
    private int capacity;

    /**
     * No. of visitors since the last cleaning.
     */
    private int visitorsSinceCleaning;

    public List<RoomStrategy> getStrategies() {
        return strategies;
    }

    /**
     * The strategies applied to the room.
     */
    private List<RoomStrategy> strategies;


    public List<Person> getPeopleInRoom() {
        return peopleInRoom;
    }

    /**
     * The people currently in the room.
     */
    private List<Person> peopleInRoom;
    /**
     * The items currently in the room.
     */
    private List<Item> itemsInRoom;
    /**
     * The doors connected to the room.
     */
    private List<Door> doorsInRoom;
    /**
     * The transistors placed in the room.
     */
    private List<Transistor> transistorsInRoom;

    private final String roomCode;

    /**
     * Constructs a Room object with default capacity.
     */
    public Room(String roomCode) {
        this(roomCode, 1, false);
        //default ertek
        this.strategies.add(new NormalStrategy());
    }

    /**
     * Constructs a Room object with the specified capacity.
     *
     * @param capacity The capacity of the room.
     */
    public Room(String roomCode, int capacity, boolean isPoisonous) {
        this.roomCode = roomCode;
        this.capacity = capacity;

        //------
        this.strategies = new ArrayList<>();
        this.peopleInRoom = new ArrayList<>();
        this.itemsInRoom = new ArrayList<>();
        this.doorsInRoom = new ArrayList<>();
        this.transistorsInRoom = new ArrayList<>();
        if (isPoisonous) {
            this.addStrategy(new PoisonStrategy());
        } else {
            this.addStrategy(new NormalStrategy());
        }
    }

    public Room(String roomCode, int capacity) {
        this.roomCode = roomCode;
        this.capacity = capacity;

        //------
        this.strategies = new ArrayList<>();
        this.peopleInRoom = new ArrayList<>();
        this.itemsInRoom = new ArrayList<>();
        this.doorsInRoom = new ArrayList<>();
        this.transistorsInRoom = new ArrayList<>();

        this.strategies.add(new NormalStrategy());
    }

    /**
     * Checks if the room is full.
     *
     * @return True if the room is full, false otherwise.
     */
    public boolean isFull() {
        Logger.CALLSTACK_PRINTLN("Room.isFull() called.");
        boolean ret = (capacity <= peopleInRoom.size());
        Logger.CALLSTACK_PRINTLN("\tRoom.isFull() returned with " + ret + ".");
        return ret;
    }

    /**
     * Adds a student to the room.
     *
     * @param student The student to add.
     */
    public void acceptStudent(Student student) {
        Logger.CALLSTACK_PRINTLN("Room.acceptStudent(person) called");

        for (RoomStrategy strategy : strategies)
            strategy.executeStudentStrategy(student);

        for (int i = 0; i < peopleInRoom.size(); i++) {
            Person p = peopleInRoom.get(i);
            student.meet(p);
        }
        visitorsSinceCleaning += 1;
        if (visitorsSinceCleaning >= 5) {
            for (Item it : itemsInRoom) {
                it.setSticky(true);
            }
        }
        peopleInRoom.add(student);
    }

    /**
     * Adds a teacher to the room.
     *
     * @param teacher The teacher to add.
     */
    public void acceptTeacher(Teacher teacher) {
        Logger.CALLSTACK_PRINTLN("Room.acceptTeacher(person) called");

        for (RoomStrategy strategy : strategies)
            strategy.executeTeacherStrategy(teacher);

        for (int i = 0; i < peopleInRoom.size(); i++) {
            Person p = peopleInRoom.get(i);
            teacher.meet(p);
        }
        visitorsSinceCleaning += 1;
        if (visitorsSinceCleaning >= 5) {
            for (Item it : itemsInRoom) {
                it.setSticky(true);
            }
        }
        peopleInRoom.add(teacher);
    }

    /**
     * Adds a cleaner to the room.
     *
     * @param cleaner The cleaner to add.
     */
    public void acceptCleaner(Cleaner cleaner) {
        peopleInRoom.add(cleaner);

        if (strategies.isEmpty()) addStrategy(new NormalStrategy());
        for (RoomStrategy strategy : strategies)
            strategy.executeCleanerStrategy(cleaner);

        visitorsSinceCleaning = 0;

        for (int i = 0; i < peopleInRoom.size(); i++) {
            Person p = peopleInRoom.get(i);

            if (p != cleaner)
                cleaner.meet(p);
        }

        for (Item it : itemsInRoom) {
            it.setSticky(false);
        }

    }

    /**
     * Places a transistor in the room.
     *
     * @param t The transistor to place.
     */
    public void placeTransistorInRoom(Transistor t) {
        Logger.CALLSTACK_PRINTLN("Room.placeTransistorInRoom(t) called");
        transistorsInRoom.add(t);
    }

    /**
     * Adds a door to the room.
     *
     * @param door The door to add.
     */
    void addDoor(Door door) {
        Logger.CALLSTACK_PRINTLN("Room.addDoor(door) called");
        doorsInRoom.add(door);
    }

    /**
     * Adds a strategy to the room.
     *
     * @param strategy The strategy to add.
     */
    public void addStrategy(RoomStrategy strategy) {
        Logger.CALLSTACK_PRINTLN("Room.addStrategy(strategy) called");
        this.strategies.add(strategy);
    }

    /**
     * Removes a strategy from the room.
     *
     * @param strategy The strategy to remove.
     */
    public void removeStrategy(RoomStrategy strategy) {
        strategies.remove(strategy);
        if (strategies.isEmpty()) strategies.add(new NormalStrategy());
    }

    /**
     * Removes a person from the room.
     *
     * @param person The person to remove.
     */
    public void removePerson(Person person) {
        Logger.CALLSTACK_PRINTLN("Room.removePerson(person) called");
        peopleInRoom.remove(person);
    }

    /**
     * Executes actions on each tick of the game.
     */
    public void onTick() {
        for (int i = 0; i < strategies.size(); i++) {
            RoomStrategy strategy = strategies.get(i);
            strategy.onTick();

            if (strategy.isExpired()) {
                strategies.remove(i--);
            }
        }
    }

    /**
     * Removes an item from the room.
     *
     * @param item The item to remove.
     */
    public void removeItem(Item item) {
        Logger.CALLSTACK_PRINTLN("Room.removeItem(item) called");
        itemsInRoom.remove(item);
    }

    /**
     * Retrieves the common door between this room and another room.
     *
     * @param room The other room.
     * @return The common door, or null if there isn't any.
     */
    public Door getCommonDoor(Room room) {
        Logger.CALLSTACK_PRINTLN("Room.getCommonDoor(room) called");
        for (Door d : doorsInRoom) {
            if (d.getTo() == room) {
                return d;
            }
            if (d.getFrom() == room) {
                return d;
            }
        }
        return null;
    }

    /**
     * Creates a door between this room and another room.
     *
     * @param room The other room to create the door with.
     */
    Door createDoorBetween(Room room, boolean isTwoWay, boolean isOpen) {
        Logger.CALLSTACK_PRINTLN("Room.createDoorBetween(room) called");
        Door d = new Door(this, room, isTwoWay, isOpen);
        this.addDoor(d);
        room.addDoor(d);
        Logger.CALLSTACK_PRINTLN("Door created between two rooms");
        return d;
    }

    /**
     * Removes the door between this room and another room.
     *
     * @param room The other room connected by the door.
     */
    void removeDoorBetween(Room room) {
        Logger.CALLSTACK_PRINTLN("Room.removeDoor(room) called");
        Door d = getCommonDoor(room);
        this.removeDoor(d);
        room.removeDoor(d);
        Logger.CALLSTACK_PRINTLN("Door removed between two rooms");
    }

    /**
     * Merges this room with another room.
     *
     * @param room2 The room to merge with.
     */
    public Room mergeWith(Room room2) {
        Room room1 = this;
        Logger.CALLSTACK_PRINTLN("Room.mergeRooms(room1, room2) called");

        // Create a new room to hold the merged state
        Room mergedRoom = new Room(room1.getRoomCode(), room1.getCapacity() + room2.getCapacity());

        // Copy strategies from both rooms
        for (RoomStrategy strategy : room1.strategies) {
            if (!this.strategies.contains(strategy)) {
                addStrategy(strategy);
            }
        }
        for (RoomStrategy strategy : room2.strategies) {
            if (!this.strategies.contains(strategy)) {
                addStrategy(strategy);
            }
        }

        // Copy people from both rooms
        mergedRoom.peopleInRoom.addAll(room1.peopleInRoom);
        mergedRoom.peopleInRoom.addAll(room2.peopleInRoom);

        // Copy items from both rooms
        mergedRoom.itemsInRoom.addAll(room1.itemsInRoom);
        mergedRoom.itemsInRoom.addAll(room2.itemsInRoom);

        // Remove door between the two rooms
        Door commonDoor = room1.getCommonDoor(room2);
        room1.removeDoor(commonDoor);
        room2.removeDoor(commonDoor);

        // Copy doors from both rooms except the common door
        mergedRoom.doorsInRoom.addAll(room1.doorsInRoom);
        mergedRoom.doorsInRoom.addAll(room2.doorsInRoom);

        // Print merged room stats
        Logger.CALLSTACK_PRINTLN("---");
        Logger.CALLSTACK_PRINTLN("Rooms merged into a new room:");
        Logger.CALLSTACK_PRINTLN("\tRoom capacity: " + mergedRoom.capacity);
        Logger.CALLSTACK_PRINTLN("\tRoom strategies count: " + mergedRoom.strategies.size());
        Logger.CALLSTACK_PRINTLN("\tRoom people count: " + mergedRoom.peopleInRoom.size());
        Logger.CALLSTACK_PRINTLN("\tRoom items count: " + mergedRoom.itemsInRoom.size());
        Logger.CALLSTACK_PRINTLN("\tRoom doors count: " + mergedRoom.doorsInRoom.size());

        // Return the new merged room
        return mergedRoom;
    }

    /**
     * Splits this room into two rooms.
     *
     * @return The new room created from the split.
     */
    public Room split(String newCode) {
        Logger.CALLSTACK_PRINTLN("Room.split() called");
        // Create a new room
        Room newRoom = new Room(newCode, this.capacity);
        newRoom.strategies = new ArrayList<>(this.strategies);

        Logger.CALLSTACK_PRINTLN("Old room stats");
        Logger.CALLSTACK_PRINTLN("\tRoom capacity: " + this.capacity);
        Logger.CALLSTACK_PRINTLN("\tRoom strategies count: " + this.strategies.size());
        Logger.CALLSTACK_PRINTLN("\tRoom people count: " + this.peopleInRoom.size());
        Logger.CALLSTACK_PRINTLN("\tRoom items count: " + this.itemsInRoom.size());
        Logger.CALLSTACK_PRINTLN("\tRoom doors count: " + this.doorsInRoom.size());

        Logger.CALLSTACK_PRINTLN("New room created");
        Logger.CALLSTACK_PRINTLN("\tRoom capacity: " + newRoom.capacity);
        Logger.CALLSTACK_PRINTLN("\tRoom strategies count: " + newRoom.strategies.size());
        Logger.CALLSTACK_PRINTLN("\tRoom people count: " + newRoom.peopleInRoom.size());
        Logger.CALLSTACK_PRINTLN("\tRoom items count: " + newRoom.itemsInRoom.size());
        Logger.CALLSTACK_PRINTLN("\tRoom doors count: " + newRoom.doorsInRoom.size());

        Logger.CALLSTACK_PRINTLN("\tRoom.split() returned with new Room");
        return newRoom;
    }

    /**
     * Removes the specified door from the room.
     *
     * @param door The door to remove.
     */
    private void removeDoor(Door door) {
        Logger.CALLSTACK_PRINTLN("Room.removeDoor(door) called");
        doorsInRoom.remove(door);
    }

    /**
     * Adds an item to the room.
     *
     * @param item The item to add.
     */
    public void addItem(Item item) {
        Logger.CALLSTACK_PRINTLN("Room.addItem(item) called");
        itemsInRoom.add(item);
    }

    public void addPerson(Person person) {
        Logger.CALLSTACK_PRINTLN("Room.addPerson(person) called");
        peopleInRoom.add(person);
    }

    public int getDoorCount() {
        return doorsInRoom.size();
    }

    public Door getDoor(int index) {
        return doorsInRoom.get(index);
    }

    public List<Door> getAllDoor() {
        return doorsInRoom;
    }

    /**
     * Removes and then returns the item with the given index;
     *
     * @param index The index of the item to be removed and returned.
     * @return The item to be removed and returned.
     */
    public Item removeItemAt(int index) {
        Item item = itemsInRoom.get(index);
        itemsInRoom.remove(index);
        return item;
    }

    /**
     * Retrieves the item at the specified index.
     *
     * @param index The index of the item to retrieve.
     * @return The item at the specified index.
     */
    public Item getItemAt(int index) {
        Item item = itemsInRoom.get(index);
        return item;
    }

    /**
     * Retrieves the number of items in the room.
     *
     * @return The number of items in the room.
     */
    public int getItemCount() {
        return itemsInRoom.size();
    }

    /**
     * Retrieves the room code.
     *
     * @return The room code.
     */
    public String getRoomCode() {
        return roomCode;
    }

    /**
     * Retrieves the capacity of the room.
     *
     * @return The capacity of the room.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Prints information about the room, including its code, neighbors, items, placed items, strategies, and people.
     */
    public void printInfo() {
        System.out.println("CURRENT ROOM: " + roomCode);
        System.out.println("Neighbours:");
        for (int i = 0; i < doorsInRoom.size(); i++) {
            Door door = doorsInRoom.get(i);

            char isOpen;
            if (door.isOpen()) isOpen = '-';
            else isOpen = '|';

            char dirChar;
            if (door.isTwoWay()) dirChar = '-';
            else if (door.getFrom().equals(this)) dirChar = '>';
            else dirChar = '<';

            String oppId = door.getOpposite(this).getRoomCode();

            System.out.print("\t" + i + ".: " + dirChar + isOpen + dirChar + " " + oppId);
        }
        System.out.println("\nItems:");
        if (itemsInRoom.isEmpty()) {
            System.out.println("\t...");
        } else {
            for (int i = 0; i < itemsInRoom.size(); i++) {
                Item item = itemsInRoom.get(i);
                System.out.print("\t" + i + ".: ");
                item.printInfo();
            }
            System.out.println();
        }

        for (RoomStrategy str : strategies) {
            System.out.println("str: " + str.toString());
        }
    }

    /**
     * Retrieves the list of doors in the room.
     *
     * @return The list of doors in the room.
     */
    public List<Door> getDoorsInRoom() {
        return doorsInRoom;
    }

    /**
     * Prints information about the items in the room.
     */
    public void printItems() {
        if (!itemsInRoom.isEmpty()) {
            for (Item i : itemsInRoom) {
                i.printInfo();
                System.out.print(" ");
            }
        }
    }

    /**
     * Prints information about the placed items in the room.
     */
    public void printPlacedItems() {
        for (Item i : transistorsInRoom) {
            i.printInfo();
            System.out.print(" ");
        }
    }

    /**
     * Prints information about the strategies applied to the room.
     */
    public void printStrategies() {
        for (RoomStrategy rs : strategies) {
            rs.printInfo();
            System.out.print(" ");
        }
    }

    /**
     * Prints information about the people in the room.
     */
    public void printPeople() {
        //Módosítás-safe!
        for (int i = 0; i < peopleInRoom.size(); i++) {
            Person p = peopleInRoom.get(i);
            System.out.print(p.getNeptun() + " ");
        }
    }

    public List<Item> getItemsInRoom() {
        return itemsInRoom;
    }

    public List<Transistor> getTransistorsInRoom() {
        return transistorsInRoom;
    }

    public boolean isPoisonous() {
        for (RoomStrategy strategy : strategies)
            if (strategy.doesMakeRoomPoisonous())
                return true;
        return false;
    }
}