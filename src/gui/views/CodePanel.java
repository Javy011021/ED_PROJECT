package gui.views;


import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gui.components.PButton;
import gui.components.TextAreaScroll;
import logic.*;
import logic.utils.Email;

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
    private JPanel tarjetPanelTree;
    private TreeAnimate treePanel;
    private String phrase;
    private String code;
    public CodePanel(JPanel panel) {
        super();
        tarjetPanelTree = panel;
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

        treePanel = new TreeAnimate();
        tarjetPanelTree.add(treePanel);
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
                    phrase=phraseText.getText();
                    code = Huffman.createHuffman(phrase);
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
                    StringSelection stringSelection = new StringSelection(code);
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
                    if (Huffman.isHuffmanCreated()) {
                        FileDialog dialog = new FileDialog((Frame) null, "Save file");
                        dialog.setMode(FileDialog.SAVE);
                        dialog.setLocationRelativeTo(dialog.getOwner());
                        dialog.setVisible(true);
                        String file = dialog.getDirectory() + dialog.getFile();
                        dialog.dispose();
                        HuffmanFile.save(file, phrase, code);
                    }
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
                    String email = JOptionPane.showInputDialog(null,"Write the email to send", "Send file", JOptionPane.INFORMATION_MESSAGE);
                    //Regular Expression
                    String regex = "^(.+)@(.+)$";
                    //Compile regular expression to get the pattern
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(email);
                    if (matcher.matches()){
                        File file = new File("temp");
                        try {
                            file.createNewFile();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        HuffmanFile.save("temp", phrase, code);
                        Email.sendMail("temp",email);
                    }

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
                    if (Huffman.isHuffmanCreated()){

                        treePanel.setLayout(null);
                        treePanel.setBounds(0,0,tarjetPanelTree.getWidth(),tarjetPanelTree.getHeight());
                        treePanel.setBackground(tarjetPanelTree.getBackground());
                        treePanel.init(phrase);
                        outer().setVisible(false);
                    }


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
            outputCodeText.getTextArea().setEditable(false);
        }
        return outputCodeText;
    }


    private CodePanel outer() {
        return CodePanel.this;
    }

    private void refreshOutput(String code){
        outputCodeText.setText(code);
    }

}
