package projlab.programozopancelosok.util;

public class Util {
    public static boolean parseBoolean(String string) throws Exception {
        if (string.equalsIgnoreCase("true")) return true;
        if (string.equalsIgnoreCase("false")) return false;
        throw new Exception("Value is not boolean");
    }

    public static int posMod(int x, int mod) {
        if (mod <= 0) return -1;
        return (((x % mod) + mod) % mod);
    }
}
