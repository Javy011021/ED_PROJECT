package gui.views.draw;

import java.awt.*;

public class Node extends DrawComponent{
    String character;
    int frequency;

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
}
