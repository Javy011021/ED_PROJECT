package gui.views.draw.rooms;

import gui.views.TreeAnimate;
import gui.views.draw.components.Arrow;
import gui.views.draw.components.Node;
import gui.views.draw.components.Text;
import logic.Huffman;
import logic.HuffmanLeaf;
import logic.HuffmanNode;
import logic.TimerInterval;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import tree.BinaryTree;
import tree.BinaryTreeNode;

import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Step3 extends JPanel implements Step {
    private Animator animator;
    private ArrayList<Node> nodes;
    private ArrayList<Arrow> arrows;
    private Text text;
    private Text subText;
    private PriorityQueue<Node> queueNodes;
    private String message;
    private TreeAnimate treePanel;
    public Step3(String message, TreeAnimate treePanel){
        this.message=message;
        this.treePanel=treePanel;
        this.queueNodes = new PriorityQueue<>(Comparator.comparingInt(Node::getFrequency));
    }
    @Override
    public void start() {
        text = new Text(new Color(0,0,0,0),new Dimension(50,0),new Point(325,80),"Step 3");
        subText = new Text(new Color(0,0,0,0),new Dimension(20,0),new Point(310,120),"Generate binary tree");
        nodes = new ArrayList<>();
        PriorityQueue<BinaryTreeNode<HuffmanNode>> queue = Huffman.processString(message);
        int currentX = (int)(getBounds().getWidth()/2)-queue.size()*100/2;
        int bounds = (int)(getBounds().getWidth());
        for (BinaryTreeNode<HuffmanNode> btn: queue){
            HuffmanLeaf node = (HuffmanLeaf) btn.getInfo();
            queueNodes.offer(new Node(new Color(0,150,0,0),new Dimension(50,50),null,String.valueOf(node.getCharacter()), node.getFrequency()));

        }

        while (!queue.isEmpty()){
            HuffmanLeaf node = (HuffmanLeaf)queue.poll().getInfo();
            System.out.println(node.getCharacter());
            Node nod = findNode(String.valueOf(node.getCharacter()));
            nod.setLocation(new Point(currentX, 500));
            nodes.add(nod);
            currentX+=nod.getSize().getWidth()*2;

        }
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

        //draw leafs

        for (Node node: nodes){

            g2.setColor(node.getColor());
            g2.fillOval((int)node.getLocation().getX(),(int)node.getLocation().getY(),(int)node.getSize().getWidth(),(int)node.getSize().getHeight());
            g2.setColor(node.getColor().darker());
            g2.drawOval((int)node.getLocation().getX(),(int)node.getLocation().getY(),(int)node.getSize().getWidth(),(int)node.getSize().getHeight());
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Tahoma", Font.BOLD, 15));
            g2.drawString(node.getCharacter(),((int)node.getLocation().getX()+(int)node.getSize().getWidth()/2-4),(int)node.getLocation().getY()+(int)node.getSize().getHeight()/2);
            g2.setFont(new Font("Tahoma", Font.BOLD, 12));
            g2.drawString(String.valueOf(node.getFrequency()),((int)node.getLocation().getX()+(int)node.getSize().getWidth()/2-4),(int)node.getLocation().getY()+(int)node.getSize().getHeight()/2+16);
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

                animator = PropertySetter.createAnimator(1000,nodes.get(0),"color",nodes.get(0).getColor(), new Color(0,150,0,255));
                for (int i=1; i<nodes.size(); i++){
                    animator.addTarget(new PropertySetter(nodes.get(i),"color",nodes.get(0).getColor(), new Color(0,150,0,255)));
                }
                animator.addTarget(new TimingTargetAdapter(){
                    @Override
                    public void timingEvent(float fraction){
                        repaint();
                    }
                });


                animator.start();
                TimerInterval.setTimeout( e3 -> {
                    nextNode();
                },2000);
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

    private void nextNode(){
        Node left = queueNodes.poll();
        Node right = queueNodes.poll();
        int amount = left.getFrequency()+right.getFrequency();
        Node upNode = countLevels(left)>countLevels(right)?left:right;
        Node downNode = countLevels(left)>countLevels(right)?right:left;
        System.out.println(countLevels(left)+" "+countLevels(right));
        int size = (int)upNode.getSize().getWidth();
        int distance = size*countLevels(upNode)+2;
        Node newNode = new Node(Color.BLUE,new Dimension(50,50),new Point((int)upNode.getLocation().getX()-distance,(int)upNode.getLocation().getY()-(size*2)),"",amount);
        newNode.setLeft(downNode);
        newNode.setRight(upNode);
        downNode.setLocation(new Point((int)upNode.getLocation().getX()-distance*2,(int)upNode.getLocation().getY()));
        moveChilds(downNode);
        nodes.add(newNode);
        queueNodes.offer(newNode);
        repaint();
        if (queueNodes.size()>1){
            TimerInterval.setTimeout( e -> nextNode(),1000);
        }

    }

    private void moveChilds(Node node){

        Node left=node.getLeft();
        Node right=node.getRight();
        if (left!=null){
            left.setLocation(new Point((int)node.getLocation().getX()-50,(int)node.getLocation().getY()+100));
            moveChilds(left);
        }
        if (right!=null){
            right.setLocation(new Point((int)node.getLocation().getX()+50,(int)node.getLocation().getY()+100));
            moveChilds(right);
        }

    }

    private int countLevels(Node node){
        if (node==null)
            return 0;
        return Math.max(countLevels(node.getLeft()),countLevels(node.getRight()))+1;
    }

    private Node findNode(String character){
        for (Node node: queueNodes){
            if (node.getCharacter().equals(character)){
                return node;
            }
        }
        return null;
    }
}
