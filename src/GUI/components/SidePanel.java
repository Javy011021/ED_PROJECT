package GUI.components;

import GUI.App;
import GUI.views.CodePanel;
import GUI.views.DecodePanel;
import GUI.views.HelpPanel;

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
            copyrightLabel = new JLabel("©Richard, Javier");
            copyrightLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
            copyrightLabel.setBounds(0, 0, 90,100);
            copyrightLabel.setBounds(this.getWidth()/2-copyrightLabel.getWidth()/2, this.getHeight()-100, copyrightLabel.getWidth(),copyrightLabel.getHeight());
        }
        return copyrightLabel;
    }

    public JLabel getHuffmanLogo(){
        if (huffmanLogo==null){
            huffmanLogo = new JLabel();
            huffmanLogo.setBounds(0,0, getWidth(), 150);
            ImageIcon ico=new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Huffman Code.png")));
            ImageIcon img=new ImageIcon(ico.getImage().getScaledInstance(huffmanLogo.getWidth(), huffmanLogo.getHeight(), Image.SCALE_SMOOTH));
            huffmanLogo.setIcon(img);
        }
        return huffmanLogo;
    }



}
