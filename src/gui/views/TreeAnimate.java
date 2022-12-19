package gui.views;

import gui.views.draw.rooms.Step;
import gui.views.draw.rooms.Step1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TreeAnimate extends JPanel {
    private int step;
    private ArrayList<JPanel> stepPanels;



    public void init(String message){
        this.stepPanels = new ArrayList<>(List.of(new Step1(message)));
        this.step = 0;
        for (JPanel stepPanel: stepPanels){
            this.add(stepPanel);
            stepPanel.setLayout(null);
            stepPanel.setBounds(0,0,this.getWidth(),this.getHeight());
            stepPanel.setBackground(this.getBackground());
        }
        start(step);
    }

    private void start(int step){
        for (JPanel panel: stepPanels){
            panel.setVisible(false);
        }
        stepPanels.get(step).setVisible(true);
        ((Step)stepPanels.get(step)).start();
    }
}
