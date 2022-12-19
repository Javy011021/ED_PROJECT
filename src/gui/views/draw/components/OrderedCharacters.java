package gui.views.draw.components;

import logic.HuffmanLeaf;
import logic.HuffmanNode;
import tree.BinaryTreeNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class OrderedCharacters extends DrawComponent{

    ArrayList<HuffmanLeaf> characters;
    public OrderedCharacters(Color color, Dimension size, Point location, PriorityQueue<BinaryTreeNode<HuffmanNode>> queue) {
        super(color, size, location);
        characters = new ArrayList<>();
        while (!queue.isEmpty()){
            characters.add((HuffmanLeaf)queue.poll().getInfo());
        }
    }

    public ArrayList<HuffmanLeaf> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<HuffmanLeaf> characters) {
        this.characters = characters;
    }
}
