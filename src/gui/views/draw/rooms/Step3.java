package gui.views.draw.rooms;

import gui.views.TreeAnimate;
import gui.views.draw.components.DrawComponent;
import gui.views.draw.components.Node;
import gui.views.draw.components.Text;
import logic.Huffman;
import logic.HuffmanLeaf;
import logic.HuffmanNode;
import logic.utils.TimerInterval;
import org.jdesktop.animation.timing.Animator;
import tree.BinaryTreeNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicReference;

public class Step3 extends JPanel implements Step {
    private Animator animator;
    private Point mouse;
    private List<DrawComponent> nodes;
    private Text text;
    private Text subText;
    private PriorityQueue<Node> queueNodes;
    private String message;
    private TreeAnimate treePanel;
    private int timeAnimation;
    private int nodeSize;
    public Step3(String message, TreeAnimate treePanel){
        this.message=message;
        this.treePanel=treePanel;
        this.queueNodes = new PriorityQueue<>(Comparator.comparingInt(Node::getFrequency));
    }
    @Override
    public void start() {
        PriorityQueue<BinaryTreeNode<HuffmanNode>> queue = Huffman.processString(message);
        nodeSize = (int)(50-queue.size()*1.2);
        timeAnimation = 1000-queue.size()*30;
        if (nodeSize<10){
            nodeSize=10;
        }
        if (timeAnimation<100){
            timeAnimation=100;
        }
        int currentX = (int)(getBounds().getWidth()/2)-queue.size()*nodeSize;
        for (BinaryTreeNode<HuffmanNode> btn: queue){
            HuffmanLeaf node = (HuffmanLeaf) btn.getInfo();
            queueNodes.offer(new Node(new Color(0,150,0,0),new Dimension(nodeSize,nodeSize),null,String.valueOf(node.getCharacter()), node.getFrequency()));

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
    public void init() {
        text = new Text(new Color(0,0,0,0),new Dimension(50,0),new Point(325,80),"Step 3");
        subText = new Text(new Color(0,0,0,0),new Dimension(20,0),new Point(310,120),"Generate binary tree");
        nodes = new ArrayList<>();
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

        //draw arrows
        for (DrawComponent draw: nodes){
            Node node = (Node) draw;
            g2.setColor(node.getColor());
            Node left = node.getLeft();
            Node right = node.getRight();
            if (left!=null){
                g2.drawLine(
                        (int)(node.getLocation().getX()+nodeSize/2),
                        (int)(node.getLocation().getY()+nodeSize/2),
                        (int)(left.getLocation().getX()+nodeSize/2),
                        (int)(left.getLocation().getY()+nodeSize/2)
                );
            }
            if (right!=null){
                g2.drawLine(
                        (int)(node.getLocation().getX()+nodeSize/2),
                        (int)(node.getLocation().getY()+nodeSize/2),
                        (int)(right.getLocation().getX()+nodeSize/2),
                        (int)(right.getLocation().getY()+nodeSize/2)
                );
            }
        }

        //draw nodes
        for (DrawComponent draw: nodes){
            Node node = (Node) draw;
            g2.setColor(node.getColor());
            g2.fillOval((int)node.getLocation().getX(),(int)node.getLocation().getY(),nodeSize,nodeSize);
            g2.setColor(node.getColor().darker());
            g2.drawOval((int)node.getLocation().getX(),(int)node.getLocation().getY(),nodeSize,nodeSize);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Tahoma", Font.BOLD, nodeSize/4+3));
            g2.drawString(node.getCharacter(),((int)node.getLocation().getX()+nodeSize/2-4),(int)node.getLocation().getY()+nodeSize/2);
            g2.setFont(new Font("Tahoma", Font.BOLD, nodeSize/4));
            g2.drawString(String.valueOf(node.getFrequency()),((int)node.getLocation().getX()+nodeSize/2-4),(int)node.getLocation().getY()+nodeSize/2+nodeSize/4+4);
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
        if ((queueNodes.size()>1)) {
            Node left = queueNodes.poll();
            Node right = queueNodes.poll();
            assert right != null && left != null;
            int amount = left.getFrequency() + right.getFrequency();
            Node upNode = countLevels(left) > countLevels(right) ? left : right;
            Node downNode = countLevels(left) > countLevels(right) ? right : left;
            int size = (int) upNode.getSize().getWidth();
            int distance = (int) (Math.abs(upNode.getLocation().getX() - getLastLeft(upNode).getLocation().getX())) + size + 5;
            Node newNode = new Node(new Color(0, 120, 255, 0), new Dimension(nodeSize, nodeSize), new Point((int) upNode.getLocation().getX() - distance, (int) upNode.getLocation().getY() - (size * 2)), "", amount);
            newNode.setLeft(downNode);
            newNode.setRight(upNode);
            nodes.add(newNode);
            TimerInterval.fade(newNode, false, this);
            TimerInterval.setTimeout(e -> {
                //move down node
                AtomicReference<Point> oldPos = new AtomicReference<>(downNode.getLocation());
                AtomicReference<Point> newPos = new AtomicReference<>(new Point((int) upNode.getLocation().getX() - distance * 2, (int) upNode.getLocation().getY()));
                AtomicReference<Point> vector = new AtomicReference<>(new Point((int) (newPos.get().getX() - oldPos.get().getX()), (int) (newPos.get().getY() - oldPos.get().getY())));
                TimerInterval.move(downNode, newPos.get(), this);
                TimerInterval.move(getChilds(downNode), vector.get(), this);
                TimerInterval.setTimeout(e3 -> {
                    //move new node
                    newPos.set(new Point((int) (oldPos.get().getX() + upNode.getLocation().getX()) / 2, (int) newNode.getLocation().getY()));
                    oldPos.set(newNode.getLocation());
                    vector.set(new Point((int) (newPos.get().getX() - oldPos.get().getX()), (int) (newPos.get().getY() - oldPos.get().getY())));
                    TimerInterval.move(newNode, newPos.get(), this);
                    TimerInterval.move(getChilds(newNode), vector.get(), this);
                    queueNodes.offer(newNode);
                    //base case
                    if (queueNodes.size() > 1) {
                        TimerInterval.setTimeout(e2 -> nextNode(), timeAnimation);
                    } else {
                        TimerInterval.setTimeout(e2 -> {
                            addMouseListener(new MouseAdapter() {
                                @Override
                                public void mousePressed(MouseEvent e) {
                                    super.mousePressed(e);
                                    mouse = e.getPoint();
                                }
                            });

                            addMouseMotionListener(new MouseAdapter() {
                                @Override
                                public void mouseDragged(MouseEvent e) {
                                    super.mouseDragged(e);
                                    Point vector = new Point((int) (e.getX() - mouse.getX()), (int) (e.getY() - mouse.getY()));
                                    mouse = e.getPoint();
                                    for (DrawComponent draw : nodes) {
                                        draw.setLocation(new Point((int) (draw.getLocation().getX() + vector.getX()), (int) (draw.getLocation().getY() + vector.getY())));
                                    }
                                    repaint();
                                }

                            });

                            addMouseWheelListener(new MouseAdapter() {
                                @Override
                                public void mouseWheelMoved(MouseWheelEvent e) {
                                    super.mouseWheelMoved(e);
                                    double oldSize = nodeSize;
                                    nodeSize -= e.getWheelRotation();
                                    if (nodeSize<10) {
                                        nodeSize=10;
                                    }
//                                    int leftX = (int)nodes.get(0).getLocation().getX();
//                                    int upY = (int)nodes.get(0).getLocation().getY();
//                                    int rightX = (int)(nodes.get(0).getLocation().getX()+nodes.get(0).getSize().getWidth());
//                                    int downY = (int)(nodes.get(0).getLocation().getY()+nodes.get(0).getSize().getHeight());
//
//                                    for (int i=1; i<nodes.size(); i++){
//                                        int leftXAux = (int)nodes.get(0).getLocation().getX();
//                                        int upYAux = (int)nodes.get(0).getLocation().getY();
//                                        int rightXAux = (int)(nodes.get(0).getLocation().getX()+nodes.get(0).getSize().getWidth());
//                                        int downYAux = (int)(nodes.get(0).getLocation().getY()+nodes.get(0).getSize().getHeight());
//                                        leftX = Math.min(leftXAux, leftX);
//                                        upY = Math.min(upYAux, upY);
//                                        rightX = Math.max(rightXAux, rightX);
//                                        downY = Math.max(downYAux, downY);
//                                    }
//                                    System.out.println(rightX+" "+leftX+" "+downY+" "+upY);
//                                    Point center = new Point(rightX-leftX,downY-upY);

                                    double ratio = nodeSize/oldSize;
                                    for (DrawComponent node: nodes){

                                        node.setLocation(new Point((int)Math.round(node.getLocation().getX()*ratio),(int)Math.round(node.getLocation().getY()*ratio)));
                                    }
                                    repaint();
                                }
                            });


                        }, 1000);
                    }
                }, timeAnimation);
            }, timeAnimation);
        }
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
