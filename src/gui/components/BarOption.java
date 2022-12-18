package gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BarOption extends JLabel{
    public BarOption(String text){
        super(text);
        setFont(new Font("Tahoma", Font.BOLD, 13));
        setForeground(Color.GRAY);
        setOpaque(true);
        setBackground(Color.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                setBackground(Color.LIGHT_GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                setBackground(Color.WHITE);
            }
        });
    }


}
