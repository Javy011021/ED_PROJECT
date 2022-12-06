package GUI.views;


import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.components.PButton;
import GUI.components.TextAreaScroll;
import logic.System;

public class CodePanel extends JPanel {
    private JLabel phraseLabel;
    private TextAreaScroll phraseText;
    private PButton codeButton;
    private PButton clearButton;
    private PButton copyButton;
    private PButton saveButton;
    private PButton sendButton;
    private PButton treeButton;
    private JLabel outputLabel;
    private TextAreaScroll outputCodeText;
    public CodePanel() {
        super();
        add(getPhraseLabel());
        add(getPhraseText());
        add(getCodeButton());
        add(getClearButton());
        add(getOutputLabel());
        add(getOutputCodeLabel());
        add(getSaveButton());
        add(getCopyButton());
        add(getSendButton());
        add(getTreeButton());
    }

    public JLabel getPhraseLabel(){
        if (phraseLabel==null) {
            phraseLabel = new JLabel("Phrase: ");
            phraseLabel.setBounds(10, 20, 50, 20);

        }
        return phraseLabel;
    }
    public TextAreaScroll getPhraseText(){
        if (phraseText==null){
            phraseText = new TextAreaScroll(10,40,800,200);
        }
        return phraseText;
    }

    public PButton getCodeButton(){
        if (codeButton==null){
            codeButton = new PButton("Code");
            codeButton.setBounds(10,phraseText.getY()+phraseText.getHeight()+4,70,30);
            codeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //call huffman
                    String code = System.getInstance().createHuffman(phraseText.getText());
                    refreshOutput(code);
                }
            });
        }
        return codeButton;
    }

    public PButton getClearButton(){
        if (clearButton==null){
            clearButton = new PButton("Clear");
            clearButton.setBounds(90,phraseText.getY()+phraseText.getHeight()+4,70,30);
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    phraseText.setText("");
                    outputCodeText.setText("");
                }
            });
        }
        return clearButton;
    }
    public PButton getCopyButton(){
        if (copyButton==null){
            copyButton = new PButton("Copy");
            copyButton.setBounds(10,getOutputCodeLabel().getY()+getOutputCodeLabel().getHeight()+4,70,30);
            copyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StringSelection stringSelection = new StringSelection(getOutputCodeLabel().getText());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }
            });
        }
        return copyButton;
    }

    public PButton getSaveButton(){
        if (saveButton==null){
            saveButton = new PButton("Save");
            saveButton.setBounds(90,getOutputCodeLabel().getY()+getOutputCodeLabel().getHeight()+4,70,30);
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   //save in file
                }
            });
        }
        return saveButton;
    }

    public PButton getSendButton(){
        if (sendButton==null){
            sendButton = new PButton("Send");
            sendButton.setBounds(170,getOutputCodeLabel().getY()+getOutputCodeLabel().getHeight()+4,70,30);
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //send file
                }
            });
        }
        return sendButton;
    }

    public PButton getTreeButton(){
        if (treeButton==null){
            treeButton = new PButton("Animate Tree");
            treeButton.setBounds(getOutputCodeLabel().getWidth()-100,getOutputCodeLabel().getY()+getOutputCodeLabel().getHeight()+4,100,30);
            treeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //show tree
                }
            });
        }
        return treeButton;
    }



    public JLabel getOutputLabel(){
        if (outputLabel==null) {
            outputLabel = new JLabel("Output: ");
            outputLabel.setBounds(10, 300, 50, 20);

        }
        return outputLabel;
    }
    public TextAreaScroll getOutputCodeLabel(){
        if (outputCodeText==null) {
            outputCodeText = new TextAreaScroll(10, 320, 800, 200);

        }
        return outputCodeText;
    }




    private void refreshOutput(String code){
        outputCodeText.setText(code);
    }

}
