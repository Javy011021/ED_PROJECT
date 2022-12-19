package gui.views.draw.components;

import java.awt.*;

public class Node extends DrawComponent{
    String character;
    int frequency;
    Node left;
    Node right;

    public Node(Color color, Dimension size, Point location, String character, int frequency) {
        super(color, size, location);
        this.character = character;
        this.frequency = frequency;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
