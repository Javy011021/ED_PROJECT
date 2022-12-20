package logic;

import gui.views.draw.components.DrawComponent;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TimerInterval {
    private static ArrayList<Animator> animators = new ArrayList<>();
    private static ArrayList<Timer> timers = new ArrayList<>();
    public static void setTimeout(ActionListener action, int time){
        Timer t = new Timer(time,action);
        t.setRepeats(false);
        t.start();
    }

    public static void fade(DrawComponent component, boolean fadeOut, JPanel panel){
        Color color = component.getColor();
        Animator animator = PropertySetter.createAnimator(1000,component,"color",color, new Color(color.getRed(),color.getGreen(),color.getBlue(),fadeOut?0:255));
        animator.addTarget(new TimingTargetAdapter(){
            @Override
            public void timingEvent(float fraction){
                panel.repaint();
            }
        });
        animator.start();
    }

    public static void fade(List<DrawComponent> components, boolean fadeOut, JPanel panel){
        if (!components.isEmpty()){
            Color color = components.get(0).getColor();
            Animator animator = PropertySetter.createAnimator(1000,components.get(0),"color",color, new Color(color.getRed(),color.getGreen(),color.getBlue(),fadeOut?0:255));
            for (int i=1; i<components.size(); i++){
                DrawComponent component=components.get(i);
                color = component.getColor();
                animator.addTarget(new PropertySetter(component,"color",color, new Color(color.getRed(),color.getGreen(),color.getBlue(),fadeOut?0:255)));
                animator.addTarget(new TimingTargetAdapter(){
                    @Override
                    public void timingEvent(float fraction){
                        panel.repaint();
                    }
                });
            }
            animator.start();
        }
    }
}
