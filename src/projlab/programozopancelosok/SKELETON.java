package projlab.programozopancelosok;

import java.util.Scanner;

import projlab.programozopancelosok.entity.Student;
import projlab.programozopancelosok.entity.Teacher;
import projlab.programozopancelosok.graphics.PlayerView;
import projlab.programozopancelosok.graphics.Screen;
import projlab.programozopancelosok.item.*;

import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.room.strategy.BoardCleanerStrategy;
import projlab.programozopancelosok.room.strategy.CamembertStrategy;
import projlab.programozopancelosok.room.strategy.NormalStrategy;
import projlab.programozopancelosok.room.strategy.PoisonStrategy;
import projlab.programozopancelosok.room.Door;

public class SKELETON {

    //GRAFIKUS DEMO CUCC

    @Deprecated
    public static void main(String[] args) {
        //ÁTMOZGATVA IDE: GraphicMain
        /*
        Student player1 = new Student("St1");
        Student player2 = new Student("St2");
        Room room1 = new Room("K1");
        Room room2 = new Room("K2");
        Room room3 = new Room("K3");
        Door door1 = new Door(room1, room2, true, true);
        Door door2 = new Door(room1, room3, true, true);



        player1.setCurrentRoom(room1);
        player2.setCurrentRoom(room2);


        room1.addItem(new FFP2(true, true));
        room1.addItem(new AirFreshener(true, true));

        //player1.pickUpItem(room1.getItemAt(0));

        PlayerView playerView1 = new PlayerView(player1);
        PlayerView playerView2 = new PlayerView(player2);
        Screen screen = new Screen(playerView1, playerView2);*/
    }
    /*
    public static final double TICK_LENGTH_s = 0.01;


    //Skeleton igy irva valami letezo abstract osztaly volt, azert lett CAPS LOCK

    public static void testTeacherEnterRoomSuccess() {
        Teacher teacher = new Teacher();
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptTeacher(teacher);
        r2.addStrategy(new NormalStrategy());
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nTeachers: teacher\tRooms: r1, r2\tDoors: door\t");
        teacher.chooseDoor(door);
    }

    public static void testTeacherEnterRoomFailed() {
        Teacher teacher = new Teacher();
        Room r1 = new Room(1);
        Room r2 = new Room(0);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptTeacher(teacher);
        r2.addStrategy(new NormalStrategy());
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nTeachers: teacher\tRooms: r1, r2\tDoors: door\t");
        teacher.chooseDoor(door);
    }

    public static void testStudentEnterRoomFailed() {
        Student student = new Student();
        Room r1 = new Room(1);
        Room r2 = new Room(0);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptStudent(student);
        r2.addStrategy(new NormalStrategy());
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tRooms: r1, r2\tDoors: door\t");
        student.chooseDoor(door);
    }

    public static void testStudentEnterRoomSuccess() {
        Student student = new Student();
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptStudent(student);
        r2.addStrategy(new NormalStrategy());
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tRooms: r1, r2\tDoors: door\t");
        student.chooseDoor(door);
    }

    public static void testStudentEnterPoisonousRoom() {
        Student student = new Student();
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptStudent(student);
        r2.addStrategy(new PoisonStrategy());
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tRooms: r1, r2\tDoors: door\t");
        student.chooseDoor(door);
    }

    public static void testStudentEnterPoisonousRoomMask() {
        Student student = new Student();
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptStudent(student);
        r2.addStrategy(new PoisonStrategy());
        FFP2 mask = new FFP2();
        student.addItem(mask);
        mask.onUse(student);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tRooms: r1, r2\tDoors: door\tItems: FFP2 Mask\t");

        student.chooseDoor(door);
    }

    public static void testTeacherEnterPoisonousRoom() {
        Teacher teacher = new Teacher();
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptTeacher(teacher);
        r2.addStrategy(new PoisonStrategy());
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nTeachers: teacher\tRooms: r1, r2\tDoors: door\t");
        teacher.chooseDoor(door);
    }

    public static void testTeacherEnterPoisonousRoomMask() {
        Teacher teacher = new Teacher();
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptTeacher(teacher);
        r2.addStrategy(new PoisonStrategy());
        FFP2 mask = new FFP2();
        teacher.addItem(mask);
        mask.onUse(teacher);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nTeachers: teacher\tRooms: r1, r2\tDoors: door\tItems: FFP2 Mask\t");
        teacher.chooseDoor(door);
    }

    public static void testStudentEnterCamemembertRoom() {
        Student student = new Student();
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptStudent(student);
        r2.addStrategy(new CamembertStrategy(30.0));
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tRooms: r1, r2\tDoors: door\t");
        student.chooseDoor(door);
    }

    public static void testTeacherEnterCamembertRoom() {
        Teacher teacher = new Teacher();
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptTeacher(teacher);
        r2.addStrategy(new CamembertStrategy(30));
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nTeachers: teacher\tRooms: r1, r2\tDoors: door\t");
        teacher.chooseDoor(door);
    }

    public static void testTeacherEnterCamembertRoomMask() {
        Teacher teacher = new Teacher();
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptTeacher(teacher);
        r2.addStrategy(new CamembertStrategy(30));
        FFP2 mask = new FFP2();
        teacher.addItem(mask);
        mask.onUse(teacher);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nTeachers: teacher\tRooms: r1, r2\tDoors: door\tItems: FFP2 Mask\t");
        teacher.chooseDoor(door);
    }

    public static void testStudentEnterBoardCleanerRoom() {
        Student student = new Student();
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r1.acceptStudent(student);
        r2.addStrategy(new BoardCleanerStrategy());
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tRooms: r1, r2\tDoors: door\t");
        student.chooseDoor(door);
    }

    public static void testStudentPickUpItemSuccess() {
        Room room = new Room();
        FFP2 mask = new FFP2();
        room.addItem(mask);
        Student student = new Student();
        room.acceptStudent(student);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tRooms: r\tItems: mask\t");
        //KONTROLL RÉSZE LESZ MAJD!
        pickUpItemStd(student,mask,room);
    }

    public static void testStudentPickUpItemFailed() {
        Room r = new Room();
        Student student = new Student();
        HolyBeerCup cup = new HolyBeerCup();
        TVSZ tvsz = new TVSZ();
        Camembert camambert = new Camembert();
        Transistor t = new Transistor();
        BoardCleaner boardcleaner = new BoardCleaner();
        student.addItem(cup);
        student.addItem(t);
        student.addItem(tvsz);
        student.addItem(camambert);
        student.addItem(boardcleaner);
        FFP2 mask = new FFP2();
        r.addItem(mask);
        r.acceptStudent(student);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tRooms: r\tItems: tvsz, boardcleaner, transistor, camembert, holybeercup, mask\t");

        //KONTROLL RÉSZE
        pickUpItemStd(student,mask,r);
    }

    public static void testTeacherMeetsStudentNoProtection() {
        //Initialization
        Teacher teacher = new Teacher();
        Student student = new Student();
        Room room1 = new Room(1);
        Room room2 = new Room(2);
        Door door = new Door(room1, room2);
        room1.addDoor(door);
        room2.addDoor(door);
        room2.addStrategy(new NormalStrategy());
        room1.acceptTeacher(teacher);
        room2.acceptStudent(student);
        System.out.CALLSTACK_PRINTLN("------------Initialization is completed.------------\nNew objects: \nTeachers: teacher\tStudents: student\tRooms: room1, room2\tDoors: door\t\n");
        //Test
        teacher.chooseDoor(door);
        System.out.CALLSTACK_PRINTLN("------------TestTeacherMeetsStudentNoProtection Completed------------\n");

    }

    public static void testTeacherMeetsStudentWhoHasTVSZ() {

        //Initialization
        Teacher teacher = new Teacher();
        Student student = new Student();
        Room r1 = new Room(1);
        Room r2 = new Room(2);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r2.addStrategy(new NormalStrategy());
        r1.acceptTeacher(teacher);
        r2.acceptStudent(student);
        student.pickUpItem(new TVSZ());
        System.out.CALLSTACK_PRINTLN("------------Initialization is completed.------------\nNew objects: \nTeachers: teacher\tStudents: student\tRooms: r1, r2\tDoors: door\tItems: TVSZ\t\n");
        //Test
        teacher.chooseDoor(door);
        System.out.CALLSTACK_PRINTLN("------------TestTeacherMeetsStudentWhoHasTVSZ Completed------------\n");
    }

    public static void testTeacherMeetsStudentWhoHasInvincibilitytime() {
        //Initialization
        Teacher teacher = new Teacher();
        Student student = new Student();
        Room r1 = new Room(1);
        Room r2 = new Room(2);
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        r2.addStrategy(new NormalStrategy());
        r1.acceptTeacher(teacher);
        r2.acceptStudent(student);
        //Megivott egy sört effektíve
        student.setInvincibility(30);
        System.out.CALLSTACK_PRINTLN("------------Initialization is completed.------------\nNew objects: \nTeachers: teacher\tStudents: student\tRooms: r1, r2\tDoors: door\tItems: HolyBeerCup\t\n");
        //Test
        teacher.chooseDoor(door);
        System.out.CALLSTACK_PRINTLN("------------TestTeacherMeetsStudentWhoHasInvincibilitytime Completed------------\n");
    }

    public static void testTeacherPickUpItemSuccess() {
        Room r = new Room();
        FFP2 mask = new FFP2();
        r.addItem(mask);
        Teacher teacher = new Teacher();
        r.acceptTeacher(teacher);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nTeachers: teacher\tRooms: r\tItems: mask\t");
        if (teacher.pickUpItem(mask)) {
            r.removeItem(mask);
        }
    }

    public static void testTeacherPickUpItemFailed() {
        Room r = new Room();
        Teacher teacher = new Teacher();
        HolyBeerCup cup = new HolyBeerCup();
        TVSZ tvsz = new TVSZ();
        Camembert camambert = new Camembert();
        Transistor t = new Transistor();
        BoardCleaner boardcleaner = new BoardCleaner();
        teacher.addItem(cup);
        teacher.addItem(t);
        teacher.addItem(tvsz);
        teacher.addItem(camambert);
        teacher.addItem(boardcleaner);
        FFP2 mask = new FFP2();
        r.addItem(mask);
        r.acceptTeacher(teacher);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nTeacher: teacher\tRooms: r\tItems: tvsz, boardcleaner, transistor, camembert, holybeercup, mask\t");
        if (teacher.pickUpItem(mask)) {
            r.removeItem(mask);
        }
    }

    public static void testItemOpenCamembert() {
        Room r = new Room(1);
        Camembert c = new Camembert();
        Student student = new Student();
        r.acceptStudent(student);
        student.pickUpItem(c);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tCamemberts: c\tRooms: r\n Student's inventory contains a Camembert object. Student is in Room r.\t");
        c.onUse(student);
        System.out.CALLSTACK_PRINTLN("--------");
    }

    public static void testStudentDropItem() {
        Room r = new Room(1);
        FFP2 f = new FFP2();
        Student student = new Student();
        r.acceptStudent(student);
        student.pickUpItem(f);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tItems: f\tRooms: r\n Student's inventory contains an FFP2 object. Student is in Room r.\t");
        student.dropSelectedItem(f);
        System.out.CALLSTACK_PRINTLN("-----------");

    }

    public static void testRoomMerge() {
        Room r1 = new Room();
        Room r2 = new Room();
        r1.createDoorBetween(r2);
        System.out.CALLSTACK_PRINTLN("------------Initialization is completed.------------\nNew objects: \nRooms: r1, r2\t");
        r1.mergeWith(r2);
        System.out.CALLSTACK_PRINTLN("------------TestRoomMerge Completed------------\n");
    }

    public static void testRoomSplit() {
        Room room1 = new Room(10);
        Room room2 = new Room(20);
        Room room3 = new Room(50);
        Room room4 = new Room(13);
        Room room5 = new Room(7);
        room1.createDoorBetween(room2);
        room1.createDoorBetween(room3);
        room1.createDoorBetween(room4);
        room1.createDoorBetween(room5);
        System.out.CALLSTACK_PRINTLN("------------Initialization is completed.------------\nNew objects: \nRooms: r\t");
        Room newRoom = room1.split();
        System.out.CALLSTACK_PRINTLN("------------TestRoomSplit Completed------------\n");
    }

    public static void testCloseDoor() {
        Room r1 = new Room();
        Room r2 = new Room();
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        System.out.CALLSTACK_PRINTLN("------------Initialization is completed.------------\nNew objects: \nRooms: r1\t");
        door.disappear();
        System.out.CALLSTACK_PRINTLN("------------TestCloseDoor Completed------------\n");
    }

    public static void testOpenDoor() {
        Room r1 = new Room();
        Room r2 = new Room();
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        door.disappear();
        System.out.CALLSTACK_PRINTLN("------------Initialization is completed.------------\nNew objects: \nRooms: r1\t");
        door.reappear();
        System.out.CALLSTACK_PRINTLN("------------TestOpenDoor Completed------------\n");
    }

    public static void testCreateDoor() {
        Room r1 = new Room();
        Room r2 = new Room();
        System.out.CALLSTACK_PRINTLN("------------Initialization is completed.------------\nNew objects: \nRooms: r1, r2\t");
        r1.createDoorBetween(r2);
        System.out.CALLSTACK_PRINTLN("------------TestCreateDoor Completed------------\n");
    }

    public static void testRemoveDoor() {
        Room r1 = new Room();
        Room r2 = new Room();
        Door door = new Door(r1, r2);
        r1.addDoor(door);
        r2.addDoor(door);
        System.out.CALLSTACK_PRINTLN("------------Initialization is completed.------------\nNew objects: \nRooms: r1, r2\t");
        r1.removeDoorBetween(r2);
        System.out.CALLSTACK_PRINTLN("------------TestDeleteDoor Completed------------\n");
    }
    public static void testItemUseHolyBeerCup() {
        Room r = new Room(1);
        HolyBeerCup h = new HolyBeerCup();
        Student student = new Student();
        r.acceptStudent(student);
        student.pickUpItem(h);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tHolyBeerCups: h\tRooms: r\n Student's inventory contains a HolyBeerCup object. Student is in Room r.\t");
        h.onUse(student);
        System.out.CALLSTACK_PRINTLN("-----------testUseHolyBeerCup----------");

    }

    public static void testItemUseMask() {
        Room r = new Room(1);
        FFP2 mask = new FFP2();
        Student student = new Student();
        r.acceptStudent(student);
        student.pickUpItem(mask);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tMasks: f\tRooms: r\n Student's inventory contains an FFP2 object. Student is in Room r.\t");
        mask.onUse(student);
        System.out.CALLSTACK_PRINTLN("-----------testUseMask()----------");

    }

    public static void testItemTransistorConnect() {
        Room r = new Room(1);
        Transistor t1 = new Transistor();
        Transistor t2 = new Transistor();
        Student student = new Student();
        r.acceptStudent(student);
        student.pickUpItem(t1);
        student.pickUpItem(t2);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tTransistors: t1, t2\tRooms: r\n Student's inventory contains two Transistor objects. Student is in Room r.\t");
        t1.connect(t2);
        System.out.CALLSTACK_PRINTLN("-----------testTransistorConnect()----------");
    }

    public static void testItemTransistorPlace() {
        Room r = new Room(1);
        Transistor t1 = new Transistor();
        Transistor t2 = new Transistor();
        Student student = new Student();
        r.acceptStudent(student);
        student.pickUpItem(t1);
        student.pickUpItem(t2);
        t1.connect(t2);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tTransistors: t1, t2\tRooms: r\n Student's inventory contains two connected Transistor objects. Student is in Room r.\t");
        t1.place(r);
        System.out.CALLSTACK_PRINTLN("-----------testTransistorPlace()----------");
    }

    public static void testItemTransistorTeleportSuccess() {
        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Transistor t1 = new Transistor();
        Transistor t2 = new Transistor();
        Student student = new Student();
        r1.acceptStudent(student);
        student.pickUpItem(t1);
        student.pickUpItem(t2);
        t1.connect(t2);
        t1.place(r2);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tTransistors: t1, t2\tRooms: r1, r2\n Student's inventory contains one Transistor object, with one connected and placed in Room r2. Student is in Room r1.\t");
        t2.onUse(student);
        System.out.CALLSTACK_PRINTLN("-----------testTransistorPlace()----------");
    }

    public static void testItemTransistorTeleportFail() {
        Room r1 = new Room(1);
        Room r2 = new Room(0);
        Transistor t1 = new Transistor();
        Transistor t2 = new Transistor();
        Student student = new Student();
        r1.acceptStudent(student);
        student.pickUpItem(t1);
        student.pickUpItem(t2);
        t1.connect(t2);
        t1.place(r2);
        System.out.CALLSTACK_PRINTLN("------------\nInitialization is completed.\nNew objects: \nStudents: student\tTransistors: t1, t2\tRooms: r1, r2\n Student's inventory contains one Transistor object, with one connected and placed in Room r2. Student is in Room r1.\t");
        t2.onUse(student);
        System.out.CALLSTACK_PRINTLN("-----------testTransistorPlace()----------");
    }

    public SKELETON() {
        handleMainMenu();
    }


    public static void handleMainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.CALLSTACK_PRINTLN("1. Szobaba belepes.");
            System.out.CALLSTACK_PRINTLN("2. Targyak felvetele.");
            System.out.CALLSTACK_PRINTLN("3. Targyak hasznalata.");
            System.out.CALLSTACK_PRINTLN("4. Jatekosok kozotti interakciok.");
            System.out.CALLSTACK_PRINTLN("5. Szobaval valo interakciok");
            System.out.CALLSTACK_PRINTLN("6. Ajtok interakcioi");
            System.out.CALLSTACK_PRINTLN("9. Kilepes.");
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.print("Valassz egy menupontot: ");
            choice = scanner.nextInt();

            System.out.print("\n\n\n");
            switch (choice) {
                case 1 -> handleRoomEntryMenu(scanner);
                case 2 -> handleItemPickupMenu(scanner);
                case 3 -> handleItemUsageMenu(scanner);
                case 4 -> handlePlayerInteractionMenu(scanner);
                case 5 -> handleRoomInteractionMenu(scanner);
                case 6 -> handleDoorInteractionMenu(scanner);
                case 9 -> System.out.CALLSTACK_PRINTLN("Kilepes...");
                default -> System.out.CALLSTACK_PRINTLN("Ervenytelen valasztas. Kerlek, valassz ujra.");
            }
        } while (choice != 9);

        scanner.close();
    }

    private static void handleRoomEntryMenu(Scanner scanner) {
        clearConsole();
        int choice;
        do {
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.CALLSTACK_PRINTLN("1. Hallgato szobaba lep");
            System.out.CALLSTACK_PRINTLN("2. Oktato szobaba lep");
            System.out.CALLSTACK_PRINTLN("9. Visszalepes.");
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.print("Valassz egy menupontot: ");
            choice = scanner.nextInt();

            System.out.print("\n\n\n");
            switch (choice) {
                case 1 -> handleStudentRoomEntryMenu(scanner);
                case 2 -> handleInstructorRoomEntryMenu(scanner);
                case 9 -> System.out.CALLSTACK_PRINTLN("Visszalepes...");
                default -> System.out.CALLSTACK_PRINTLN("Ervenytelen valasztas. Kerlek, valassz ujra.");
            }
        } while (choice != 9);
    }

    private static void handleStudentRoomEntryMenu(Scanner scanner) {
        clearConsole();
        int choice;
        do {
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.CALLSTACK_PRINTLN("1. Hallgato belep a szobaba sikeresen.");
            System.out.CALLSTACK_PRINTLN("2. Hallgato nem tud belepni a szobaba.");
            System.out.CALLSTACK_PRINTLN("3. Hallgato mergezo szobaba lep es nincs maszkja.");
            System.out.CALLSTACK_PRINTLN("4. Hallgato mergezo szobaba lep, van maszkja.");
            System.out.CALLSTACK_PRINTLN("9. Visszalepes.");
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.print("Valassz egy menupontot: ");
            choice = scanner.nextInt();

            System.out.print("\n\n\n");
            switch (choice) {
                case 1 -> testStudentEnterRoomSuccess();
                case 2 -> testStudentEnterRoomFailed();
                case 3 -> testStudentEnterPoisonousRoom();
                case 4 -> testStudentEnterPoisonousRoomMask();
                case 9 -> System.out.CALLSTACK_PRINTLN("Visszalepes...");
                default -> System.out.CALLSTACK_PRINTLN("Ervenytelen valasztas. Kerlek, valassz ujra.");
            }
        } while (choice != 9);
    }

    private static void handleInstructorRoomEntryMenu(Scanner scanner) {
        clearConsole();
        int choice;
        do {
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.CALLSTACK_PRINTLN("1. Oktato belep a szobaba sikeresen.");
            System.out.CALLSTACK_PRINTLN("2. Oktato nem tud belepni a szobaba.");
            System.out.CALLSTACK_PRINTLN("3. Oktato mergezo szobaba lep es nincs maszkja.");
            System.out.CALLSTACK_PRINTLN("4. Oktato mergezo szobaba lep es van maszkja.");
            System.out.CALLSTACK_PRINTLN("5. Oktato camembert-es szobaba lep es nincs maszkja.");
            System.out.CALLSTACK_PRINTLN("6. Oktato camembert-es szobaba lep es van maszkja.");
            System.out.CALLSTACK_PRINTLN("9. Visszalepes.");
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.print("Valassz egy menupontot: ");
            choice = scanner.nextInt();

            System.out.print("\n\n\n");
            switch (choice) {
                case 1 -> testTeacherEnterRoomSuccess();
                case 2 -> testTeacherEnterRoomFailed();
                case 3 -> testTeacherEnterPoisonousRoom();
                case 4 -> testTeacherEnterPoisonousRoomMask();
                case 5 -> testTeacherEnterCamembertRoom();
                case 6 -> testTeacherEnterCamembertRoomMask();
                case 7 -> testStudentEnterBoardCleanerRoom();
                case 9 -> System.out.CALLSTACK_PRINTLN("Visszalepes...");
                default -> System.out.CALLSTACK_PRINTLN("Ervenytelen valasztas. Kerlek, valassz ujra.");
            }
        } while (choice != 9);
    }

    private static void handleItemPickupMenu(Scanner scanner) {
        clearConsole();
        int choice;
        do {
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.CALLSTACK_PRINTLN("1. Hallgato felvesz egy targyat sikeresen.");
            System.out.CALLSTACK_PRINTLN("2. Hallgato nem tud felvenni egy targyat.");
            System.out.CALLSTACK_PRINTLN("3. Hallgato eldob egy targyat.");
            System.out.CALLSTACK_PRINTLN("4. Oktato nem tud felvenni egy targyat.");
            System.out.CALLSTACK_PRINTLN("5. Hallgato eldob egy targyat.");
            System.out.CALLSTACK_PRINTLN("9. Visszalepes.");
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.print("Valassz egy menupontot: ");
            choice = scanner.nextInt();

            System.out.print("\n\n\n");
            switch (choice) {
                case 1 -> testStudentPickUpItemSuccess();
                case 2 -> testStudentPickUpItemFailed();
                case 3 -> testTeacherPickUpItemSuccess();
                case 4 -> testTeacherPickUpItemFailed();
                case 5 -> testStudentDropItem();
                case 9 -> System.out.CALLSTACK_PRINTLN("Visszalepes...");
                default -> System.out.CALLSTACK_PRINTLN("Ervenytelen valasztas. Kerlek, valassz ujra.");
            }
        } while (choice != 9);
    }

    private static void handleItemUsageMenu(Scanner scanner) {
        clearConsole();
        int choice;
        do {
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.CALLSTACK_PRINTLN("1. Camembert kinyitasa.");
            System.out.CALLSTACK_PRINTLN("2. Sorospohar hasznalata.");
            System.out.CALLSTACK_PRINTLN("3. Ket tranzisztor osszekapcsolasa.");
            System.out.CALLSTACK_PRINTLN("4. Tranzisztor lerakasa.");
            System.out.CALLSTACK_PRINTLN("5. Tranzisztor aktiválása és sikeres teleportalas.");
            System.out.CALLSTACK_PRINTLN("6. Tranzisztor aktiválása és sikertelen teleportalas.");
            System.out.CALLSTACK_PRINTLN("7. Maszk hasznalata.");
            System.out.CALLSTACK_PRINTLN("9. Visszalepes.");
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.print("Valassz egy menupontot: ");
            choice = scanner.nextInt();

            System.out.print("\n\n\n");
            switch (choice) {
                case 1 -> testItemOpenCamembert();
                case 2 -> testItemUseHolyBeerCup();
                case 3 -> testItemTransistorConnect();
                case 4 -> testItemTransistorPlace();
                case 5 -> testItemTransistorTeleportSuccess();
                case 6 -> testItemTransistorTeleportFail();
                case 7 -> testItemUseMask();
                case 9 -> System.out.CALLSTACK_PRINTLN("Visszalepes...");
                default -> System.out.CALLSTACK_PRINTLN("Ervenytelen valasztas. Kerlek, valassz ujra.");
            }
        } while (choice != 9);
    }

    private static void handlePlayerInteractionMenu(Scanner scanner) {
        clearConsole();
        int choice;
        do {
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.CALLSTACK_PRINTLN("1. Hallgato oktatoval talalkozik es nem tud vedekozni.");
            System.out.CALLSTACK_PRINTLN("2. Hallgato oktatoval talalkozik es tud vedekozni.");
            System.out.CALLSTACK_PRINTLN("3. Sebezhetetlen hallgato oktatoval talalkozik.");
            System.out.CALLSTACK_PRINTLN("9. Visszalepes.");
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.print("Valassz egy menupontot: ");
            choice = scanner.nextInt();

            System.out.print("\n\n\n");
            switch (choice) {
                case 1 -> testTeacherMeetsStudentNoProtection();
                case 2 -> testTeacherMeetsStudentWhoHasTVSZ();
                case 3 -> testTeacherMeetsStudentWhoHasInvincibilitytime();
                case 9 -> System.out.CALLSTACK_PRINTLN("Visszalepes...");
                default -> System.out.CALLSTACK_PRINTLN("Ervenytelen valasztas. Kerlek, valassz ujra.");
            }
        } while (choice != 9);
    }

    private static void handleRoomInteractionMenu(Scanner scanner) {
        clearConsole();
        int choice;
        do {
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.CALLSTACK_PRINTLN("Szobak kozotti interakcio");
            System.out.CALLSTACK_PRINTLN("1. Szobak osztodasa");
            System.out.CALLSTACK_PRINTLN("2. Szobak szetvalasa");
            System.out.CALLSTACK_PRINTLN("9. Visszalepes.");
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.print("Valassz egy menupontot: ");
            choice = scanner.nextInt();

            System.out.print("\n\n\n");
            switch (choice) {
                case 1 -> testRoomMerge();
                case 2 -> testRoomSplit();
                case 9 -> System.out.CALLSTACK_PRINTLN("Visszalepes...");
                default -> System.out.CALLSTACK_PRINTLN("Ervenytelen valasztas. Kerlek, valassz ujra.");
            }
        } while (choice != 9);
    }

    private static void handleDoorInteractionMenu(Scanner scanner) {
        clearConsole();
        int choice;
        do {
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.CALLSTACK_PRINTLN("Ajto kezelese");
            System.out.CALLSTACK_PRINTLN("1. Ajto letrejon");
            System.out.CALLSTACK_PRINTLN("2. Ajto megszunik");
            System.out.CALLSTACK_PRINTLN("3. Ajto kinyilik");
            System.out.CALLSTACK_PRINTLN("4. Ajto becsukodik");
            System.out.CALLSTACK_PRINTLN("9. Visszalepes.");
            System.out.CALLSTACK_PRINTLN("------------");
            System.out.print("Valassz egy menupontot: ");
            choice = scanner.nextInt();

            System.out.print("\n\n\n");
            switch (choice) {
                case 1 -> testCreateDoor();
                case 2 -> testRemoveDoor();
                case 3 -> testOpenDoor();
                case 4 -> testCloseDoor();
                case 9 -> System.out.CALLSTACK_PRINTLN("Visszalepes...");
                default -> System.out.CALLSTACK_PRINTLN("Ervenytelen valasztas. Kerlek, valassz ujra.");
            }
        } while (choice != 9);
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void playersWin() {
        System.out.CALLSTACK_PRINTLN("THE PLAYERS HAVE WON! :DD");
    }


    //KONTROLL RÉSZE!!!
    private static void pickUpItemStd(Student student, Item item, Room room) {
        if (student.pickUpItem(item)) {
            room.removeItem(item);
        }
    }//*/
}
