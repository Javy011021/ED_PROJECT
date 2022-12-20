package gui.components;

import javax.swing.*;

public class TextAreaScroll extends JScrollPane {
    private JTextArea textArea;

    public TextAreaScroll(){
        this.setViewportView(getPhraseText());
    }

    public TextAreaScroll(int x, int y, int width, int height){
        this.setBounds(x,y,width,height);
        this.setViewportView(getPhraseText());
    }

    public JTextArea getPhraseText(){
        if (textArea==null){
            textArea=new JTextArea();
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
        }
        return textArea;
    }

    public String getText(){
        return textArea.getText();
    }

    public void setText(String text){
        textArea.setText(text);
    }

    public JTextArea getTextArea(){
        return textArea;
    }

}
