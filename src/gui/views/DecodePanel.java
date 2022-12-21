package gui.views;

import gui.components.PButton;
import gui.components.TextAreaScroll;
import logic.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class DecodePanel extends JPanel {
    private JLabel fileLabel;
    private JLabel fileNameLabel;
    private PButton codeButton;
    private PButton selectButton;
    private JLabel outputLabel;
    private TextAreaScroll outputText;
    private File file;
    public DecodePanel() {
        add(getFileLabel());
        add(getFileNameLabel());
        add(getCodeButton());
        add(getOutputLabel());
        add(getOutputText());
        add(getSelectButton());
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
            fileNameLabel = new JLabel("No File Selected");
            fileNameLabel.setBounds(getFileLabel().getX()+getFileLabel().getWidth(), getFileLabel().getY(), 350, 20);

        }
        return fileNameLabel;
    }

    public PButton getCodeButton(){
        if (codeButton==null){
            codeButton = new PButton("Decode");
            codeButton.setBounds(10,getFileLabel().getY()+getFileLabel().getHeight()+4,70,30);
            codeButton.addActionListener(e -> {
                //call huffman
                if (file != null) {
                    try {
                        refreshOutput(HuffmanFile.load(file));
                    }catch (Exception exc){
                        System.out.println(exc.getMessage());
                    }

                }

            });
        }
        return codeButton;
    }

    public PButton getSelectButton(){
        if (selectButton==null){
            selectButton = new PButton("Select");
            selectButton.setBounds(90,getFileLabel().getY()+getFileLabel().getHeight()+4,70,30);
            selectButton.addActionListener(e -> {
                FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
                dialog.setMode(FileDialog.LOAD);
                dialog.setLocationRelativeTo(dialog.getOwner());
                dialog.setVisible(true);
                String fileName = dialog.getDirectory()+dialog.getFile();
                dialog.dispose();
                if (dialog.getFile()!=null) {
                    file = new File(fileName);
                    getFileNameLabel().setText(dialog.getFile());
                }
            });
        }
        return selectButton;
    }


    public JLabel getOutputLabel(){
        if (outputLabel==null) {
            outputLabel = new JLabel("Output: ");
            outputLabel.setBounds(10, 100, 50, 20);

        }
        return outputLabel;
    }
    public TextAreaScroll getOutputText(){
        if (outputText==null) {
            outputText = new TextAreaScroll();
            outputText.setBounds(10, 120, 800, 200);
        }
        return outputText;
    }

    private void refreshOutput(String code){
        outputText.setText(code);
    }
}
