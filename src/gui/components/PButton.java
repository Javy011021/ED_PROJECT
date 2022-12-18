package gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PButton extends JButton {
    public PButton(String text){
        super(text);
        setBorder(BorderFactory.createEmptyBorder());
        setBackground(Color.LIGHT_GRAY);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setBackground(Color.GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setBackground(Color.LIGHT_GRAY);
            }
        });
    }
}
