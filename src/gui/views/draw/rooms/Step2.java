package gui.views.draw.rooms;

import gui.views.TreeAnimate;
import gui.views.draw.components.OrderedCharacters;
import gui.views.draw.components.Text;
import logic.Huffman;
import logic.HuffmanLeaf;
import logic.utils.TimerInterval;
import org.jdesktop.animation.timing.Animator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Step2 extends JPanel implements Step {
    private String message;
    private Animator animator;
    private Text text;
    private Text subText;
    private TreeAnimate treePanel;
    private OrderedCharacters characters;
    private Text clickToContinue;
    public Step2(String message, TreeAnimate treePanel){
        this.message=message;
        this.treePanel=treePanel;
        text = new Text(new Color(0,0,0,0),new Dimension(50,0),new Point(325,80),"Step 2");
        subText = new Text(new Color(0,0,0,0),new Dimension(20,0),new Point(310,120),"Order by frequency");
        characters = new OrderedCharacters(new Color(0,0,0,0), new Dimension(40,15), new Point(0,300), Huffman.processString(message));

    }
    @Override
    public void start() {
        anim();
    }

    @Override
    public void init() {
        text = new Text(new Color(0,0,0,0),new Dimension(50,0),new Point(325,80),"Step 2");
        subText = new Text(new Color(0,0,0,0),new Dimension(20,0),new Point(310,120),"Order by frequency");
        characters = new OrderedCharacters(new Color(0,0,0,0), new Dimension(40,15), new Point(0,300), Huffman.processString(message));
        clickToContinue = new Text(new Color(0,0,0,0),new Dimension(20,0),new Point(20, this.getHeight()-20),"Click to continue");
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
        int width = (int)characters.getSize().getWidth();

        List<List<HuffmanLeaf>> lists = new ArrayList<>();
        lists.add(new ArrayList<>());
        int count=0;
        int currentList=0;
        for (int i=0; i<characters.getCharacters().size(); i++){

            if(count++==18){
                count=1;
                currentList++;
                lists.add(new ArrayList<>());
            }
            lists.get(currentList).add(characters.getCharacters().get(i));
        }
        for (int i=0; i<lists.size();i++){
            int currentX = ((this.getWidth()/2)-(lists.get(i).size()*width/2));
            int currentY = (int)(characters.getLocation().getY() + (width+10)*i);
            g2.drawLine(currentX,currentY,currentX,currentY+width);
            for (HuffmanLeaf leaf: lists.get(i)){
                drawCeld(g2, String.valueOf(leaf.getCharacter()), String.valueOf(leaf.getFrequency()),currentX,currentY,width);
                currentX+=width;
            }
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
        TimerInterval.setTimeout(e -> {
            TimerInterval.fade(subText,false,this);
            TimerInterval.setTimeout( e2 -> {
                TimerInterval.fade(characters,false,this);
                TimerInterval.setTimeout( e3 -> {
                    TimerInterval.fadeLoop(clickToContinue,false,this);
                    addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            treePanel.start(3);
                            removeMouseListener(this);
                        }
                    });
                },2000);
            },2000);
        },1000);
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
