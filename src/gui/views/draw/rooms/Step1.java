package gui.views.draw.rooms;

import gui.views.draw.components.AllCharacters;
import gui.views.draw.components.Arrow;
import gui.views.draw.components.Node;
import gui.views.draw.components.Text;
import logic.Huffman;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class Step1 extends JPanel implements Step {
    String message;
    Animator animator;
    ArrayList<Node> nodes;
    ArrayList<Arrow> arrows;
    Text text;
    Text subText;
    AllCharacters charactersMap;


    public Step1(String message){
        this.message=message;
        nodes = new ArrayList<>();
        nodes.add(new Node(Color.BLUE,new Dimension(20,20),new Point(20,20),"a",20));
        text = new Text(new Color(0,0,0,0),new Dimension(50,0),new Point(325,80),"Step 1");
        subText = new Text(new Color(0,0,0,0),new Dimension(20,0),new Point(200,120),"Separate characters with their frequencies");
        charactersMap = new AllCharacters(new Color(0,0,0,0),new Dimension(300,0),new Point(250,200), Huffman.getFrequency(message));

    }

    public void start(){
        anim();
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Text step 1
        g2.setFont(new Font("Tahoma",Font.BOLD,(int)text.getSize().getWidth()));
        g2.setColor(text.getColor());
        g2.drawString(text.getPhrase(),(int)text.getLocation().getX(), (int)text.getLocation().getY());

        //Sub Text
        g2.setFont(new Font("Tahoma",Font.BOLD,(int)subText.getSize().getWidth()));
        g2.setColor(subText.getColor());
        g2.drawString(subText.getPhrase(),(int)subText.getLocation().getX(), (int)subText.getLocation().getY());

        //Characters with frequency
        g2.setColor(charactersMap.getColor());
        int currentY = (int)charactersMap.getLocation().getY();
        int currentX = (int)charactersMap.getLocation().getX();
        int size = (int)charactersMap.getSize().getWidth();
        g2.drawLine(currentX,currentY,currentX+size, currentY);
        g2.setFont(new Font("Tahoma",Font.PLAIN,22));
        drawRow(g2,"Character","Frequency",currentX,currentY,size);
        currentY+=20;
        for (Map.Entry<Character, Integer> entry: charactersMap.getMap().entrySet()){
            drawRow(g2,entry.getKey().toString(),entry.getValue().toString(),currentX,currentY,size);
            currentY+=20;
        }

        Node node=nodes.get(0);
        g2.setColor(node.getColor());
        g2.fillOval((int)node.getLocation().getX(),(int)node.getLocation().getY(),(int)node.getSize().getWidth(),(int)node.getSize().getHeight());
        g2.setColor(Color.black);
        g2.drawString(node.getCharacter(),(int)node.getLocation().getX(),(int)node.getLocation().getY());
        g2.dispose();


    }

    private void anim(){
        if (animator != null && animator.isRunning()) {
            animator.stop();
        }
        animator = PropertySetter.createAnimator(1000,text,"color",text.getColor(),new Color(0,0,0,255));
        setTimeout( e -> {
            animator = PropertySetter.createAnimator(1000,subText,"color",subText.getColor(), new Color(0,0,0,255));
            animator.addTarget(new TimingTargetAdapter(){
                @Override
                public void timingEvent(float fraction){
                    repaint();
                }
            });
            animator.start();
            setTimeout( e2 -> {
                animator = PropertySetter.createAnimator(1000,charactersMap,"color",charactersMap.getColor(), new Color(0,0,0,255));
                animator.addTarget(new TimingTargetAdapter(){
                    @Override
                    public void timingEvent(float fraction){
                        repaint();
                    }
                });
                animator.start();
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

    private void drawRow(Graphics g, String character, String frequency, int x, int y, int width){
        g.drawLine(x,y,x,y+20);
        g.drawLine(x+width,y,x+width,y+20);
        g.drawLine(x+width/2,y,x+width/2,y+20);
        g.drawLine(x,y+20,x+width,y+20);
        g.drawString(character,x+4,y+20);
        g.drawString(frequency,(x+width/2)+3,y+20);
    }

    private void setTimeout(ActionListener action, int time){
        Timer t = new Timer(time,action);
        t.setRepeats(false);
        t.start();
    }
}
