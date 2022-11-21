package GUI.views;

import GUI.components.PButton;
import logic.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
                    String code= System.getInstance().getHuffmanCoding(phraseText.getText());
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
            phraseText.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if (e.getKeyChar()!='0' && e.getKeyChar()!='1'){
                        e.consume();
                    }
                }
            });
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
