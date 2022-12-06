package GUI.views;

import GUI.components.PButton;
import GUI.components.TextAreaScroll;
import logic.CodeSystem;
import logic.Convert;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DecodePanel extends JPanel {
    private JLabel fileLabel;
    private JLabel fileNameLabel;
    private TextAreaScroll codeText;
    private PButton codeButton;
    private PButton selectButton;
    private JLabel outputLabel;
    private TextAreaScroll outputText;
    private File file;
    public DecodePanel() {
        add(getFileLabel());
        add(getFileNameLabel());
        add(getCodeText());
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
            fileNameLabel = new JLabel("Current");
            fileNameLabel.setBounds(getFileLabel().getX()+getFileLabel().getWidth(), getFileLabel().getY(), 350, 20);

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
                    if (file == null) {
                        String code = CodeSystem.getInstance().getHuffmanCoding(codeText.getText());
                        refreshOutput(code);
                    }else{
                        try {
                            RandomAccessFile raf = new RandomAccessFile(file, "rw");
                            byte[] codeByte = new byte[100];
                            raf.read(codeByte);
                            String code = (String)Convert.toObject(codeByte);
                            getCodeText().setText(code);
                            raf.close();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                }
            });
        }
        return codeButton;
    }

    public PButton getSelectButton(){
        if (selectButton==null){
            selectButton = new PButton("Select");
            selectButton.setBounds(90,codeText.getY()+codeText.getHeight()+4,70,30);
            selectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
                    dialog.setMode(FileDialog.LOAD);
                    dialog.setVisible(true);
                    String fileName = dialog.getDirectory()+dialog.getFile();
                    dialog.dispose();
                    file = new File(fileName);
                    getFileNameLabel().setText(dialog.getFile());

                }
            });
        }
        return selectButton;
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
