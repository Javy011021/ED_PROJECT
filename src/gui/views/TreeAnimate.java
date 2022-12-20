package gui.views;

import gui.views.draw.rooms.Step;
import gui.views.draw.rooms.Step1;
import gui.views.draw.rooms.Step2;
import gui.views.draw.rooms.Step3;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TreeAnimate extends JPanel {
    private int step;
    private ArrayList<JPanel> stepPanels;



    public void init(String message){
        this.stepPanels = new ArrayList<>(List.of(
                new Step1(message,this),
                new Step2(message,this),
                new Step3(message,this)
        ));
        this.step = 3;
        for (JPanel stepPanel: stepPanels){
            this.add(stepPanel);
            stepPanel.setLayout(null);
            stepPanel.setBounds(0,0,this.getWidth(),this.getHeight());
            stepPanel.setBackground(this.getBackground());

        }
        this.setVisible(true);
        start(step);
    }

    public void start(int step){
        for (JPanel panel: stepPanels){
            panel.setVisible(false);
        }
        step--;
        stepPanels.get(step).setVisible(true);
        ((Step)stepPanels.get(step)).start();
    }
}
