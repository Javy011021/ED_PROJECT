package GUI.personal_components;

import javax.swing.*;
import java.awt.*;

public class BarOption {
    private JLabel option;
    public BarOption(String text){
        option = new JLabel(text);
        option.setFont(new Font("Tahoma", Font.BOLD, 13));
    }

    public void setBounds(int x, int y, int width, int height){
        option.setBounds(x,y,width,height);
    }

    public Rectangle getBounds(){
        return option.getBounds();
    }
}
