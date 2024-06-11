package projlab.programozopancelosok.item;

import projlab.programozopancelosok.SKELETON;
import projlab.programozopancelosok.control.Control;
import projlab.programozopancelosok.control.GameState;
import projlab.programozopancelosok.entity.Person;

/**
 * Represents a Logar item in the game.
 */
public class Logar extends Item {
    Runnable onVictory;

    public Logar(boolean validItem, boolean sticky, Control control) {
        super(validItem, sticky);
        this.onVictory = () -> {
            if (this.isValid())
                control.setGameState(GameState.VICTORY);
        };
    }

    /**
     * Executes logic when the Logar item is picked up.
     * This method will signal that the players have won.
     */
    @Override
    public void onPickup(Person p) {
        onVictory.run();
    }

    @Override
    public void printInfo() {
        System.out.print("Logar");
    }

    @Override
    public String toString() {
        return "Logar";
    }
}
