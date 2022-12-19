package gui.views.draw.rooms;

import gui.views.TreeAnimate;

import javax.swing.*;

public class Step3 extends JPanel implements Step {
    private String message;
    private TreeAnimate treePanel;
    public Step3(String message, TreeAnimate treePanel){
        this.message=message;
        this.treePanel=treePanel;
    }
    @Override
    public void start() {

    }
}
