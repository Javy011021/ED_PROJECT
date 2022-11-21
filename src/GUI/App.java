package GUI;

import GUI.personal_components.BarOption;
import GUI.personal_components.BarOptions;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame{
    private JPanel panel;
    private JPanel sidePanel;
    private JPanel bodyPanel;
    private JLabel copyrightLabel;
    private BarOptions options;

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                App frame = new App();
                frame.setVisible(true);
            }
        });
    }
    public App(){
        setTitle("Huffman");
        setBounds(0, 0, 1080, 640);
        getContentPane().setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(getOwner());

        panel=new JPanel();
        panel.setLayout(null);
        setContentPane(panel);
        panel.add(getSidePanel());
        panel.add(getBodyPanel());


    }

    public JPanel getSidePanel(){
        if (sidePanel==null){
            sidePanel = new JPanel();
            sidePanel.setLayout(null);
            sidePanel.setBounds(0,0,240, this.getHeight());
            sidePanel.setBackground(Color.WHITE);
            sidePanel.add(getCopyrightLabel());
            sidePanel.add(getOptions());
        }
        return sidePanel;
    }

    public JPanel getBodyPanel(){
        if (bodyPanel==null){
            bodyPanel = new JPanel();
            bodyPanel.setLayout(null);
            bodyPanel.setBounds(sidePanel.getWidth()+6,0, this.getWidth()-bodyPanel.getX(), this.getHeight());
            bodyPanel.setBackground(Color.WHITE);
        }
        return bodyPanel;
    }

    public JLabel getCopyrightLabel(){
        if (copyrightLabel==null){
            copyrightLabel = new JLabel("©Richard, Javier");
            copyrightLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
            copyrightLabel.setBounds(0, 0, 90,100);
            copyrightLabel.setBounds(sidePanel.getWidth()/2-copyrightLabel.getWidth()/2, sidePanel.getHeight()-100, copyrightLabel.getWidth(),copyrightLabel.getHeight());
        }
        return copyrightLabel;
    }

    public BarOptions getOptions(){
        if (options==null){
            options = new BarOptions(60);
            options.setBackground(Color.WHITE);
            options.setBounds(10,10,sidePanel.getWidth()-10,500);
            options.addOption(new BarOption("Code"));
            options.addOption(new BarOption("Decode"));
            options.addOption(new BarOption("Help"));

        }
        return options;
    }


}

