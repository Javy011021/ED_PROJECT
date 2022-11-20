package logic;

import org.w3c.dom.Node;
import tree.BinaryTree;
import tree.BinaryTreeNode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class Huffman {

    private BinaryTree<HuffmanNode>tree;
    public PriorityQueue<BinaryTreeNode<HuffmanNode>> processString (String message){
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c: message.toCharArray()){
            frequency.put(c,frequency.getOrDefault(c,0)+1);
        }
        PriorityQueue<BinaryTreeNode<HuffmanNode>>result = new PriorityQueue<>(Comparator.comparingInt(l -> l.getInfo().getFrequency()));
        for(Map.Entry<Character, Integer> aux : frequency.entrySet()){
            HuffmanLeaf aux1 = new HuffmanLeaf(aux.getValue(), aux.getKey());
            result.offer(new BinaryTreeNode<HuffmanNode>(aux1));
        }
        return result;
    }

    public void createHuffmanTree(PriorityQueue<BinaryTreeNode<HuffmanNode>>stringProcessed){
        while(stringProcessed.size()>1){
            BinaryTreeNode<HuffmanNode> left = stringProcessed.poll();
            BinaryTreeNode<HuffmanNode> right = stringProcessed.poll();
            int amount = left.getInfo().getFrequency()+right.getInfo().getFrequency();
            HuffmanNode aux = new HuffmanNode(amount);
            BinaryTreeNode<HuffmanNode>aux1=new BinaryTreeNode<>(aux);
            aux1.setLeft(left);
            aux1.setRight(right);
            stringProcessed.offer(aux1);
        }
        tree.setRoot(stringProcessed.peek());
    }

}
