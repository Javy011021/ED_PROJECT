package logic.utils;

import gui.views.draw.components.DrawComponent;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
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
        timers.add(t);
        t.start();
    }

    public static void fade(DrawComponent component, boolean fadeOut, JPanel panel){
        Color color = component.getColor();
        Animator animator = PropertySetter.createAnimator(1000,component,"color",color, new Color(color.getRed(),color.getGreen(),color.getBlue(),fadeOut?0:255));
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                panel.repaint();
            }
        });
        animators.add(animator);
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

            }
            animator.addTarget(new TimingTargetAdapter() {
                @Override
                public void timingEvent(float fraction) {
                    panel.repaint();
                }
            });
            animators.add(animator);
            animator.start();
        }
    }

    public static void move(DrawComponent component, Point destiny, JPanel panel){
        Animator animator = PropertySetter.createAnimator(500, component, "location", component.getLocation(), destiny);
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                panel.repaint();
            }
        });
        animators.add(animator);
        animator.start();

    }

    public static void move(List<DrawComponent> components, Point vector, JPanel panel){
        if (!components.isEmpty()) {
            Animator animator = PropertySetter.createAnimator(500, components.get(0), "location", components.get(0).getLocation(), new Point((int)(components.get(0).getLocation().getX()+vector.getX()),(int)(components.get(0).getLocation().getY()+vector.getY())));
            for (int i=1; i<components.size(); i++){
                DrawComponent component=components.get(i);
                animator.addTarget(new PropertySetter(component, "location", component.getLocation(), new Point((int)(component.getLocation().getX()+vector.getX()),(int)(component.getLocation().getY()+vector.getY()))));
            }
            animator.addTarget(new TimingTargetAdapter() {
                @Override
                public void timingEvent(float fraction) {
                    panel.repaint();
                }
            });
            animators.add(animator);
            animator.start();
        }
    }

    public static void fadeLoop(DrawComponent component, boolean fadeOut, JPanel panel){
        Color color = component.getColor();
        TimingTarget tt = new PropertySetter(component,"color",color, new Color(color.getRed(),color.getGreen(),color.getBlue(),fadeOut?0:255),color);
        Animator animator = new Animator(1500 , Double.POSITIVE_INFINITY, Animator.RepeatBehavior.LOOP, tt);
        animator.setDeceleration(0);
        animator.setAcceleration(1);



        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                panel.repaint();
            }
        });
        animators.add(animator);
        animator.start();
    }

    public static void clearIntervals(){
        for (Animator animator: animators){
            if (animator.isRunning()){
                animator.stop();
            }
        }
        animators.clear();
        for (Timer timer: timers){
            if (timer.isRunning())
                timer.stop();
        }
        timers.clear();
    }
}
