package gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SidePanel extends JPanel {
    private JLabel copyrightLabel;
    private JLabel huffmanLogo;

    public SidePanel(int x, int y, int width, int height){
        super();
        setLayout(null);
        setBounds(x, y, width, height);
        setBackground(Color.WHITE);
        add(getCopyrightLabel());
        add(getHuffmanLogo());
    }

    public JLabel getCopyrightLabel(){
        if (copyrightLabel==null){
            copyrightLabel = new JLabel("©2022-Richard, Javier");
            copyrightLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
            copyrightLabel.setBounds(0, 0, 120,20);
            copyrightLabel.setBounds(this.getWidth()/2-copyrightLabel.getWidth()/2, this.getHeight()-30, copyrightLabel.getWidth(),copyrightLabel.getHeight());

        }
        return copyrightLabel;
    }

    public JLabel getHuffmanLogo(){
        if (huffmanLogo==null){
            huffmanLogo = new JLabel();
            huffmanLogo.setBounds(10,0, getWidth()-50, 150);
            ImageIcon ico=new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Huffman Code.png")));
            ImageIcon img=new ImageIcon(ico.getImage().getScaledInstance(huffmanLogo.getWidth(), huffmanLogo.getHeight(), Image.SCALE_SMOOTH));
            huffmanLogo.setIcon(img);
        }
        return huffmanLogo;
    }



}
