package gui.views;

import gui.components.TextAreaScroll;

import javax.swing.*;

public class HelpPanel extends JPanel {
    private TextAreaScroll text;
    public HelpPanel(){
        add(getText());
    }

    public TextAreaScroll getText(){
        if (text==null){
            text = new TextAreaScroll("""
            Code:
            Type or load the phrase to be encoded in the first text area. Touching the code button will generate the binary tree and the binary code capable of extracting the message from the tree.  Both are saved to a file by tapping the save button, or sending via email. The extra button animate tree will represent the steps that Huffman's algorithm uses to generate the tree.
            
            Decode:
            Select the file that was previously encoded with this program. Then with decode botton the text will be decoded and displayed.
            """);
            text.setBounds(20, 20, 780, 200);
            text.setBorder(BorderFactory.createEmptyBorder());
        }
        return text;
    }
}
