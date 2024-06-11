package projlab.programozopancelosok.util;

public class Logger {
    public static final boolean CALLSTACK = false;
    public static boolean TEST_MODE = false;
    public static boolean DEBUG_MODE = false;


    public static void CALLSTACK_PRINTLN(Object obj){
        if(!CALLSTACK) return;
        System.out.println(obj.toString());
    }

    public static void TEST_PRINTLN(String s){
        if(!TEST_MODE) return;
        System.out.println(s);
    }
    public static void TEST_PRINT(String s){
        if(!TEST_MODE) return;
        System.out.print(s);
    }

    public static void DEBUG_PRINTLN(Object obj){
        if(!DEBUG_MODE) return;
        System.out.println(obj.toString());
    }
}
