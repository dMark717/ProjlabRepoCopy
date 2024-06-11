package projlab.programozopancelosok.control;

public enum GameState {
    RUNNING,
    VICTORY,
    ERROR,
    DEFEAT;

    public boolean isRunning() {
        return this == RUNNING;
    }
}
