package GUI.views;

import javax.swing.*;

public class HelpPanel extends JPanel {
    private JTextArea text;
    public HelpPanel(){
        add(getText());
    }

    public JTextArea getText(){
        if (text==null){
            text = new JTextArea("Hola, esta es la ayuda");
            text.setBounds(10, 10, 400, 200);
        }
        return text;
    }
}
