package GUI.views;

import GUI.components.PButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecodePanel extends JPanel {
    private JTextArea phraseText;
    private PButton codeButton;
    private JLabel outputLabel;
    private JTextArea outputCodeText;
    public DecodePanel() {
        add(getPhraseText());
        add(getCodeButton());
        add(getOutputLabel());
        add(getOutputCodeLabel());
    }

    public PButton getCodeButton(){
        if (codeButton==null){
            codeButton = new PButton("Decode");
            codeButton.setBounds(10,phraseText.getY()+phraseText.getHeight()+2,70,30);
            codeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //call huffman
                    String code="Javi gay";
                    refreshOutput(code);
                }
            });
        }
        return codeButton;
    }

    public JTextArea getPhraseText(){
        if (phraseText==null){
            phraseText=new JTextArea();
            phraseText.setBounds(10,20,500,200);
            phraseText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        return phraseText;
    }

    public JLabel getOutputLabel(){
        if (outputLabel==null) {
            outputLabel = new JLabel("Output: ");
            outputLabel.setBounds(10, 300, 50, 20);

        }
        return outputLabel;
    }
    public JTextArea getOutputCodeLabel(){
        if (outputCodeText==null) {
            outputCodeText = new JTextArea("");
            outputCodeText.setBounds(10, 320, 500, 200);

        }
        return outputCodeText;
    }

    private void refreshOutput(String code){
        outputCodeText.setText(code);
    }
}
