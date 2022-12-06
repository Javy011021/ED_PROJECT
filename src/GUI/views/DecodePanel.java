package GUI.views;

import GUI.components.PButton;
import GUI.components.TextAreaScroll;
import logic.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DecodePanel extends JPanel {
    private JLabel fileLabel;
    private JLabel fileNameLabel;
    private TextAreaScroll codeText;
    private PButton codeButton;
    private JLabel outputLabel;
    private TextAreaScroll outputText;
    public DecodePanel() {
        add(getFileLabel());
        add(getFileNameLabel());
        add(getCodeText());
        add(getCodeButton());
        add(getOutputLabel());
        add(getOutputText());
    }

    public JLabel getFileLabel(){
        if (fileLabel==null) {
            fileLabel = new JLabel("File: ");
            fileLabel.setBounds(10, 20, 30, 20);

        }
        return fileLabel;
    }
    public JLabel getFileNameLabel(){
        if (fileNameLabel==null) {
            fileNameLabel = new JLabel("Current");
            fileNameLabel.setBounds(getFileLabel().getX()+getFileLabel().getWidth(), getFileLabel().getY(), 50, 20);

        }
        return fileNameLabel;
    }

    public PButton getCodeButton(){
        if (codeButton==null){
            codeButton = new PButton("Decode");
            codeButton.setBounds(10,codeText.getY()+codeText.getHeight()+4,70,30);
            codeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //call huffman
                    String code= System.getInstance().getHuffmanCoding(codeText.getText());
                    refreshOutput(code);
                }
            });
        }
        return codeButton;
    }



    public TextAreaScroll getCodeText(){
        if (codeText==null){
            codeText=new TextAreaScroll(10,40,800,200);
            codeText.getTextArea().addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if (e.getKeyChar()!='0' && e.getKeyChar()!='1'){
                        e.consume();
                    }
                }
            });
        }
        return codeText;
    }

    public JLabel getOutputLabel(){
        if (outputLabel==null) {
            outputLabel = new JLabel("Output: ");
            outputLabel.setBounds(10, 300, 50, 20);

        }
        return outputLabel;
    }
    public TextAreaScroll getOutputText(){
        if (outputText==null) {
            outputText = new TextAreaScroll();
            outputText.setBounds(10, 320, 800, 200);
        }
        return outputText;
    }

    private void refreshOutput(String code){
        outputText.setText(code);
    }
}
