package gui.views;

import gui.components.TextAreaScroll;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class InfoPanel extends JPanel {
    private TextAreaScroll text;
    private TextAreaScroll text2;
    private JLabel photo;
    public InfoPanel(){
        add(getPhoto());
        add(getText());
        add(getText2());

    }

    public TextAreaScroll getText2(){
        if (text2==null){
            text2 = new TextAreaScroll("""
                    History:
                            In 1951, David A. Huffman and his MIT information theory classmates were given the choice of a term paper or a final exam. The professor, Robert M. Fano, assigned a term paper on the problem of finding the most efficient binary code. Huffman, unable to prove any codes were the most efficient, was about to give up and start studying for the final when he hit upon the idea of using a frequency-sorted binary tree and quickly proved this method the most efficient.
                                
                            In doing so, Huffman outdid Fano, who had worked with Claude Shannon to develop a similar code. Building the tree from the bottom up guaranteed optimality, unlike the top-down approach of Shannon–Fano coding.
                    """);
            text2.setBounds(20, getText().getY()+getText().getHeight(), 780, 600-getText().getHeight());
            text2.setBorder(BorderFactory.createEmptyBorder());
            text2.getPhraseText().setEditable(false);

        }
        return text2;
    }

    public TextAreaScroll getText(){
        if (text==null){
            text = new TextAreaScroll("""
                        In computer science and information theory, a Huffman code is a particular type of optimal prefix code that
                    is commonly used for lossless data compression. The process of finding or using such a code proceeds by
                    means of Huffman coding, an algorithm developed by David A. Huffman while he was a Sc.D. student at MIT,
                    and published in the 1952 paper "A Method for the Construction of Minimum-Redundancy Codes"
                               
                        The output from Huffman's algorithm can be viewed as a variable-length code table for encoding a source
                    symbol (such as a character in a file). The algorithm derives this table from the estimated probability or
                    frequency of occurrence (weight) for each possible value of the source symbol. As in other entropy encoding
                    methods, more common symbols are generally represented using fewer bits than less common symbols.
                    Huffman's method can be efficiently implemented, finding a code in time linear to the number of input weights
                    if these weights are sorted. However, although optimal among methods encoding symbols separately,
                    Huffman coding is not always optimal among all compression methods - it is replaced with arithmetic coding
                    or asymmetric numeral systems if a better compression ratio is required.
                        
                    """);
            text.setBounds(20, 20, 760-getPhoto().getWidth(), 250);
            text.setBorder(BorderFactory.createEmptyBorder());
            text.getPhraseText().setEditable(false);

        }
        return text;
    }

    public JLabel getPhoto(){
        if (photo==null){
            photo = new JLabel();
            photo.setBounds(640,20, 156, 217);
            ImageIcon ico=new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/David_Albert_Huffman.jpg")));
            ImageIcon img=new ImageIcon(ico.getImage().getScaledInstance(photo.getWidth(), photo.getHeight(), Image.SCALE_SMOOTH));
            photo.setIcon(img);
        }
        return photo;
    }
}
