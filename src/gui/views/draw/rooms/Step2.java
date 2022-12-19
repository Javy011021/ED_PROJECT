package gui.views.draw.rooms;

import gui.views.TreeAnimate;
import tree.Tree;

import javax.swing.*;

public class Step2 extends JPanel implements Step {
    private String message;
    private TreeAnimate treePanel;
    public Step2(String message, TreeAnimate treePanel){
        this.message=message;
        this.treePanel=treePanel;
    }
    @Override
    public void start() {

    }
}
