package projlab.programozopancelosok.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Flagger<T> {
    private final Map<T, Boolean> flags;

    public Flagger() {
        flags = new HashMap<>();
    }

    public void setFlag(T flag) {
        flags.put(flag, true);
    }

    public boolean isFlagSet(T flag) {
        return flags.getOrDefault(flag, false);
    }

    public Set<T> getFlagSet() {
        return flags.keySet();
    }

    @SafeVarargs
    public static <F> boolean isFlagSet(F flag, Flagger<F>... flaggers) {
        for (Flagger<F> flagger : flaggers) {
            if (flagger.isFlagSet(flag))
                return true;
        }
        return false;
    }

    public void resetFlags() {
        flags.replaceAll((f, v) -> false);
    }
}
