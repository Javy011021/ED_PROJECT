package GUI.personal_components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BarOptions extends JPanel{
    private ArrayList<BarOption> optionList;
    private int optionHeight;
    private int margin;

    public BarOptions(int optionHeight){
        super();
        setLayout(null);
        optionList = new ArrayList<>();
        this.optionHeight = optionHeight;
        this.margin = 0;
    }

    public BarOptions(int optionHeight, int margin){
        super();
        optionList = new ArrayList<>();
        this.optionHeight = optionHeight;
        this.margin = margin;
    }

    public void addOption(BarOption option){
        int optionListSize = optionList.size();
        add(option);
        if (optionListSize>0) {
            BarOption last = optionList.get(optionListSize - 1);
            Rectangle r = last.getBounds();
            option.setBounds(r.x, r.y + r.height + margin, getWidth(), optionHeight);
        }else{
            option.setBounds(0, 0, getWidth(), optionHeight);
        }
        optionList.add(option);
        refreshSize();
    }

    private void refreshSize(){
        setBounds(getX(),getY(),getWidth(),optionList.size()*(optionHeight+margin));
    }

}
