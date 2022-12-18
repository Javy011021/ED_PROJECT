package gui.views;

import gui.views.draw.Node;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TreeAnimate extends JPanel {
    Animator animator;
    ArrayList<Node> nodes;

    public TreeAnimate(){
        nodes = new ArrayList<>();
        nodes.add(new Node(Color.BLUE,new Dimension(20,20),new Point(20,20),"a",20));
        //anim();
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Node node=nodes.get(0);
        g2.setColor(node.getColor());
        g2.fillOval((int)node.getLocation().getX(),(int)node.getLocation().getY(),(int)node.getSize().getWidth(),(int)node.getSize().getHeight());
        g2.setColor(Color.black);
        g2.drawString(node.getCharacter(),(int)node.getLocation().getX(),(int)node.getLocation().getY());
        g2.dispose();


    }

    private void anim(){
        animator = PropertySetter.createAnimator(500,nodes.get(0),"location",getLocation(),new Point(50,50));
        animator.addTarget(new PropertySetter(nodes.get(0),"color",nodes.get(0).getColor(),new Color(255,255,255,0)));
        animator.addTarget(new TimingTargetAdapter(){
            @Override
            public void timingEvent(float fraction){
                repaint();
            }
        });
        animator.start();
    }



}
