package gui.views.draw.rooms;

import gui.views.TreeAnimate;
import gui.views.draw.components.Arrow;
import gui.views.draw.components.DrawComponent;
import gui.views.draw.components.Node;
import gui.views.draw.components.Text;
import logic.Huffman;
import logic.HuffmanLeaf;
import logic.HuffmanNode;
import logic.TimerInterval;
import org.jdesktop.animation.timing.Animator;
import tree.BinaryTreeNode;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicReference;

public class Step3 extends JPanel implements Step {
    private Animator animator;
    private List<DrawComponent> nodes;
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
        for (BinaryTreeNode<HuffmanNode> btn: queue){
            HuffmanLeaf node = (HuffmanLeaf) btn.getInfo();
            queueNodes.offer(new Node(new Color(0,150,0,0),new Dimension(50,50),null,String.valueOf(node.getCharacter()), node.getFrequency()));

        }

        while (!queue.isEmpty()){
            HuffmanLeaf node = (HuffmanLeaf)queue.poll().getInfo();
            Node nod = findNode(String.valueOf(node.getCharacter()));
            if (nod!=null){
                nod.setLocation(new Point(currentX, 500));
                nodes.add(nod);
                currentX += nod.getSize().getWidth() * 2;
            }

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

        for (DrawComponent draw: nodes){
            Node node = (Node) draw;
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
        TimerInterval.fade(text,false,this);
        TimerInterval.setTimeout(e -> {
            TimerInterval.fade(subText,false,this);
            TimerInterval.setTimeout( e2 -> {
                TimerInterval.fade(nodes,false, this);
                TimerInterval.setTimeout( e3 -> nextNode(),2000);
            },2000);
        },1000);


    }

    private void nextNode(){
        Node left = queueNodes.poll();
        Node right = queueNodes.poll();
        assert right != null && left != null;
        int amount = left.getFrequency()+right.getFrequency();
        Node upNode = countLevels(left)>countLevels(right)?left:right;
        Node downNode = countLevels(left)>countLevels(right)?right:left;
        int size = (int)upNode.getSize().getWidth();
        int distance = (int)(Math.abs(upNode.getLocation().getX()-getLastLeft(upNode).getLocation().getX()))+size+5;
        Node newNode = new Node(new Color(0,120,255,0),new Dimension(50,50),new Point((int)upNode.getLocation().getX()-distance,(int)upNode.getLocation().getY()-(size*2)),"",amount);
        newNode.setLeft(downNode);
        newNode.setRight(upNode);
        nodes.add(newNode);
        TimerInterval.fade(newNode,false,this);
        TimerInterval.setTimeout(e -> {
            //move down node
            AtomicReference<Point> oldPos = new AtomicReference<>(downNode.getLocation());
            AtomicReference<Point> newPos = new AtomicReference<>(new Point((int) upNode.getLocation().getX() - distance * 2, (int) upNode.getLocation().getY()));
            AtomicReference<Point> vector = new AtomicReference<>(new Point((int) (newPos.get().getX() - oldPos.get().getX()), (int) (newPos.get().getY() - oldPos.get().getY())));
            TimerInterval.move(downNode, newPos.get(),this);
            TimerInterval.move(getChilds(downNode),vector.get(), this);
            TimerInterval.setTimeout(e3 ->{
                //move new node
                newPos.set(new Point((int) (oldPos.get().getX() + upNode.getLocation().getX()) / 2, (int) newNode.getLocation().getY()));
                oldPos.set(newNode.getLocation());
                vector.set(new Point((int) (newPos.get().getX() - oldPos.get().getX()), (int) (newPos.get().getY() - oldPos.get().getY())));
                TimerInterval.move(newNode, newPos.get(),this);
                TimerInterval.move(getChilds(newNode),vector.get(), this);


                queueNodes.offer(newNode);
                if (queueNodes.size()>1){
                    TimerInterval.setTimeout( e2 -> nextNode(),1000);
                }
            },1000);

        },1000);


    }

    private List<DrawComponent> getChilds(Node node){
        ArrayList<DrawComponent> result = new ArrayList<>();
        Node left=node.getLeft();
        Node right=node.getRight();
        if (left!=null){
            result.add(left);
            result.addAll(getChilds(left));
        }
        if (right!=null){
            result.add(right);
            result.addAll(getChilds(right));
        }
        return result;
    }

    private Node getLastLeft(Node node){
        if (node.getLeft()==null)
            return node;
        return getLastLeft(node.getLeft());
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
