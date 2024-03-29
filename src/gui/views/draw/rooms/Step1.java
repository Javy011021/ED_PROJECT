package gui.views.draw.rooms;

import gui.views.TreeAnimate;
import gui.views.draw.components.AllCharacters;
import gui.views.draw.components.Text;
import logic.Huffman;
import org.jdesktop.animation.timing.Animator;
import logic.utils.TimerInterval;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Step1 extends JPanel implements Step {
    private String message;
    private Animator animator;

    private Text text;
    private Text subText;
    private AllCharacters charactersMap;
    private TreeAnimate treePanel;
    private Text clickToContinue;


    public Step1(String message, TreeAnimate treePanel){
        this.treePanel=treePanel;
        this.message=message;

        text = new Text(new Color(0,0,0,0),new Dimension(50,0),new Point(325,80),"Step 1");
        subText = new Text(new Color(0,0,0,0),new Dimension(20,0),new Point(200,120),"Separate characters with their frequencies");
        charactersMap = new AllCharacters(new Color(0,0,0,0),new Dimension(200,16),new Point(250,200), Huffman.getFrequency(message));
        clickToContinue = new Text(new Color(0,0,0,255),new Dimension(20,0),new Point(20, this.getHeight()-20),"Click to continue");

    }
    @Override
    public void start(){
        anim();
    }

    @Override
    public void init() {
        text = new Text(new Color(0,0,0,0),new Dimension(50,0),new Point(325,80),"Step 1");
        subText = new Text(new Color(0,0,0,0),new Dimension(20,0),new Point(200,120),"Separate characters with their frequencies");
        charactersMap = new AllCharacters(new Color(0,0,0,0),new Dimension(200,16),new Point(250,200), Huffman.getFrequency(message));
        clickToContinue = new Text(new Color(0,0,0,0),new Dimension(20,0),new Point(20, this.getHeight()-20),"Click to continue");
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
        List<List<Map.Entry<Character,Integer>>> lists = new ArrayList<>();
        lists.add(new ArrayList<>());
        int count=0;
        int currentList=0;
        for (Map.Entry<Character, Integer> entry: charactersMap.getMap().entrySet()){

            if(count++==18){
                count=1;
                currentList++;
                lists.add(new ArrayList<>());
            }
            lists.get(currentList).add(entry);
        }

        //center
        int margen = 20;
        int size = (int)charactersMap.getSize().getWidth();
        int currentX = this.getWidth()/2-((size*lists.size())+(lists.size()-1)*margen)/2;
        int currentY = (int)charactersMap.getLocation().getY();

        g2.setColor(charactersMap.getColor());

        g2.setFont(new Font("Tahoma",Font.PLAIN,(int)charactersMap.getSize().getHeight()));

        for (List<Map.Entry<Character, Integer>> list: lists){
            g2.drawLine(currentX,currentY,currentX+size, currentY);
            drawRow(g2,"Character","Frequency",currentX,currentY,size);
            currentY+=20;
            for (Map.Entry<Character, Integer> entry: list){
                drawRow(g2,entry.getKey().toString(),entry.getValue().toString(),currentX,currentY,size);
                currentY+=20;
            }
            currentY=(int)charactersMap.getLocation().getY();
            currentX+=charactersMap.getSize().getWidth()+margen;
        }

        //click to continue

        g2.setFont(new Font("Tahoma",Font.PLAIN,(int)clickToContinue.getSize().getWidth()));
        g2.setColor(clickToContinue.getColor());
        g2.drawString(clickToContinue.getPhrase(),(int)clickToContinue.getLocation().getX(), (int)clickToContinue.getLocation().getY());

        g2.dispose();


    }

    private void anim(){
        if (animator != null && animator.isRunning()) {
            animator.stop();
        }
        TimerInterval.fade(text,false,this);
        TimerInterval.setTimeout( e -> {
            TimerInterval.fade(subText,false,this);
            TimerInterval.setTimeout( e2 -> {
                TimerInterval.fade(charactersMap,false,this);
                TimerInterval.setTimeout( e3 -> {
                    TimerInterval.fadeLoop(clickToContinue,false,this);
                    addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            treePanel.start(2);
                            removeMouseListener(this);

                        }
                    });
                },2000);
            },2000);
        },1000);
    }

    private void drawRow(Graphics g, String character, String frequency, int x, int y, int width){
        g.drawLine(x,y,x,y+20);
        g.drawLine(x+width,y,x+width,y+20);
        g.drawLine(x+width/2,y,x+width/2,y+20);
        g.drawLine(x,y+20,x+width,y+20);
        g.drawString(character,x+4,y+20);
        g.drawString(frequency,(x+width/2)+3,y+20);
    }


}
