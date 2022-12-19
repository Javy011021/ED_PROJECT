package logic;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TimerInterval {
    public static void setTimeout(ActionListener action, int time){
        Timer t = new Timer(time,action);
        t.setRepeats(false);
        t.start();
    }
}
