package four;

import javax.swing.*;

public class CellButton extends JButton {

    public CellButton(String text, String name) {
        super(text);
        setName(name);
        setFocusPainted(false);
    }

}
