package GUI.views;

import javax.swing.*;
import java.awt.*;

public class CodePanel extends JPanel {
    private JButton codeButton;
    public CodePanel() {
        add(getCodeButton());
    }

    public JButton getCodeButton(){
        if (codeButton==null){
            codeButton = new JButton("Code");
            codeButton.setBounds(0,0,100,100);
        }
        return codeButton;
    }
}
