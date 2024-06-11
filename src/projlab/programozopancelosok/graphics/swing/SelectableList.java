package projlab.programozopancelosok.graphics.swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Na hogy ez micsoda: Ez egy olyan cucc, amibe be lehet pakolni Content-eket, és be lehet választani,
 * hogy melyik legyen kivákasztva, az kap egy nagyon menő szegélyt :D
 */
public class SelectableList extends JPanel {
    int selectedIndex = -1;
    List<JComponent> components;
    Color selectionColor;

    public SelectableList(Color selectionColor) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.selectionColor = selectionColor;
        components = new ArrayList<>();
    }

    public void addToList(JComponent component) {
        components.add(component);
        this.add(component);
    }

    /**
     * Ezzel lehet beállítani, hogymelyik indexű item legyen kiválasztva, tröli az előző kijelölést, majd beállítja az újat.
     * @param index Kiválasztja melyik elem legyen kijelölve. Ha negatív szám, nincs kijelölés
     */
    public void setSelectedIndex(int index) {
        if(index<0 || index>components.size()) return;
        int prevIndex = selectedIndex;
        selectedIndex = index;
        //Előző kiválasztás törlése
        if (prevIndex >= 0 && !components.isEmpty()) {
            components.get(prevIndex).setBorder(BorderFactory.createEmptyBorder());
        }
        //Új választás kijelölése
        if (selectedIndex >= 0 && !components.isEmpty()) {
            components.get(selectedIndex).setBorder(BorderFactory.createLineBorder(selectionColor));
        }
    }
}
