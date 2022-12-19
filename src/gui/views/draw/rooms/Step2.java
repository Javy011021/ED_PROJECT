package gui.views.draw.rooms;

import gui.views.TreeAnimate;
import gui.views.draw.components.OrderedCharacters;
import gui.views.draw.components.Text;
import logic.Huffman;
import logic.HuffmanLeaf;
import logic.HuffmanNode;
import logic.TimerInterval;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import tree.BinaryTreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Step2 extends JPanel implements Step {
    private String message;
    private Animator animator;
    private Text text;
    private Text subText;
    private TreeAnimate treePanel;
    private OrderedCharacters characters;
    public Step2(String message, TreeAnimate treePanel){
        this.message=message;
        this.treePanel=treePanel;
        text = new Text(new Color(0,0,0,0),new Dimension(50,0),new Point(325,80),"Step 2");
        subText = new Text(new Color(0,0,0,0),new Dimension(20,0),new Point(310,120),"Order by frequency");
        characters = new OrderedCharacters(new Color(0,0,0,0), new Dimension(40,15), new Point(10,180), Huffman.processString(message));
    }
    @Override
    public void start() {
        anim();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Text step 1
        g2.setFont(new Font("Tahoma", Font.BOLD, (int) text.getSize().getWidth()));
        g2.setColor(text.getColor());
        g2.drawString(text.getPhrase(), (int) text.getLocation().getX(), (int) text.getLocation().getY());

        //Sub Text
        g2.setFont(new Font("Tahoma", Font.BOLD, (int) subText.getSize().getWidth()));
        g2.setColor(subText.getColor());
        g2.drawString(subText.getPhrase(), (int) subText.getLocation().getX(), (int) subText.getLocation().getY());

        //Characteres and frequency
        g2.setFont(new Font("Tahoma", Font.PLAIN, (int) characters.getSize().getHeight()));
        g2.setColor(characters.getColor());
        int currentX = 20;
        int currentY = 300;
        int width = (int)characters.getSize().getWidth();
        g2.drawLine(currentX,currentY,currentX,currentY+width);
        for (HuffmanLeaf leaf: characters.getCharacters()){
            drawCeld(g2, String.valueOf(leaf.getCharacter()), String.valueOf(leaf.getFrequency()),currentX,currentY,width);
            currentX+=width;
        }
        g2.dispose();
    }

    private void anim(){
        if (animator != null && animator.isRunning()) {
            animator.stop();
        }
        animator = PropertySetter.createAnimator(1000,text,"color",text.getColor(),new Color(0,0,0,255));
        TimerInterval.setTimeout(e -> {
            animator = PropertySetter.createAnimator(1000,subText,"color",subText.getColor(), new Color(0,0,0,255));
            animator.addTarget(new TimingTargetAdapter(){
                @Override
                public void timingEvent(float fraction){
                    repaint();
                }
            });
            animator.start();
            TimerInterval.setTimeout( e2 -> {
                animator = PropertySetter.createAnimator(1000,characters,"color",characters.getColor(), new Color(0,0,0,255));
                animator.addTarget(new TimingTargetAdapter(){
                    @Override
                    public void timingEvent(float fraction){
                        repaint();
                    }
                });
                animator.start();
                TimerInterval.setTimeout( e3 -> addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        treePanel.start(3);
                        removeMouseListener(this);
                    }
                }),2000);
            },2000);
        },1000);

        animator.addTarget(new TimingTargetAdapter(){
            @Override
            public void timingEvent(float fraction){
                repaint();
            }
        });
        animator.start();
    }

    private void drawCeld(Graphics g, String character, String frequency, int x, int y, int width){
        g.drawLine(x,y,x+width,y);
        g.drawLine(x,y+width,x+width,y+width);
        g.drawLine(x+width,y,x+width,y+width);
        g.drawString(character+": ",x+4,(y+width/2)+8);
        g.setFont(new Font("Tahoma", Font.PLAIN, (int) (characters.getSize().getHeight())-((frequency.length()-1)*2)));
        g.drawString(frequency,(x)+20,(y+width/2)+8);
        g.setFont(new Font("Tahoma", Font.PLAIN, (int) characters.getSize().getHeight()));
    }

}
