package GUI.personal_components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BarOptions extends Component{
    private ArrayList<BarOption> optionList;
    private JPanel optionsContainer;
    private int optionHeight;
    private int margin;

    public BarOptions(int optionHeight){
        optionList = new ArrayList<>();
        optionsContainer = new JPanel();
        this.optionHeight = optionHeight;
        this.margin = 0;
    }

    public BarOptions(int optionHeight, int margin){
        optionList = new ArrayList<>();
        optionsContainer = new JPanel();
        this.optionHeight = optionHeight;
        this.margin = margin;
    }
    public void setBounds(int x, int y, int width){
        optionsContainer.setBounds(x,y,width,optionList.size()*(optionHeight+margin));
    }
    public void addOption(BarOption option){
        int optionListSize = optionList.size();
        if (optionListSize>0) {
            BarOption last = optionList.get(optionListSize - 1);
            Rectangle r = last.getBounds();
            option.setBounds(r.x, r.y + r.height + margin, optionsContainer.getWidth(), optionHeight);
        }else{
            option.setBounds(0, 0, optionsContainer.getWidth(), optionHeight);
        }
        optionList.add(option);
        refreshSize();
    }

    private void refreshSize(){
        optionsContainer.setBounds(optionsContainer.getX(),optionsContainer.getY(),optionsContainer.getWidth(),optionList.size()*(optionHeight+margin));
    }

}
