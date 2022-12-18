package gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BarOptions extends JPanel{
    private ArrayList<BarOption> optionList;
    private JPanel activePanel;
    private JPanel contentPanel;
    private int optionHeight;
    private int margin;





    public BarOptions(JPanel panel,int optionHeight, int margin){
        super();
        optionList = new ArrayList<>();
        this.optionHeight = optionHeight;
        this.margin = margin;
        this.contentPanel=panel;
        setLayout(null);
    }

    //default values
    public BarOptions(JPanel panel){
        this(panel, 50, 0);
    }
    public BarOptions(JPanel panel,int optionHeight){
        this(panel,optionHeight,0);
    }

    public void addOption(BarOption option, JPanel panel, boolean active){

        //Default active panel
        if (active)
            activePanel=panel;
        else
            panel.setVisible(false);

        //add to container
        contentPanel.add(panel);

        //Inherit from container
        panel.setLayout(null);
        panel.setBounds(0,0,contentPanel.getWidth(),contentPanel.getHeight());
        panel.setBackground(contentPanel.getBackground());

        //add option to options container
        add(option);
        int optionListSize = optionList.size();
        if (optionListSize>0) {
            BarOption last = optionList.get(optionListSize - 1);
            Rectangle r = last.getBounds();
            option.setBounds(r.x, r.y + r.height + margin, getWidth()-10, optionHeight);
        }else{
            option.setBounds(0, 0, getWidth()-10, optionHeight);
        }
        optionList.add(option);
        refreshSize();

        option.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                activePanel.setVisible(false);
                panel.setVisible(true);
                activePanel=panel;
            }
        });
    }

    public void addOption(BarOption option, JPanel panel){
        addOption(option, panel, false);
    }

    private void refreshSize(){
        setBounds(getX(),getY(),getWidth(),optionList.size()*(optionHeight+margin));
    }

}
