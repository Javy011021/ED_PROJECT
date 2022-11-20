package logic;

import tree.BinaryTree;
import tree.BinaryTreeNode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class Huffman {
    private BinaryTree<HuffmanNode>tree;
    private HashMap<Character,String>codeMap;
    private String message;
    private StringBuilder decode;

    public PriorityQueue<BinaryTreeNode<HuffmanNode>> processString (){
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c: message.toCharArray()){
            frequency.put(c,frequency.getOrDefault(c,0)+1);
        }
        PriorityQueue<BinaryTreeNode<HuffmanNode>>result = new PriorityQueue<>(Comparator.comparingInt(l -> l.getInfo().getFrequency()));
        for(Map.Entry<Character, Integer> aux : frequency.entrySet()){
            HuffmanLeaf aux1 = new HuffmanLeaf(aux.getValue(), aux.getKey());
            result.offer(new BinaryTreeNode<>(aux1));
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
    public void createCodeMap(BinaryTreeNode<HuffmanNode>root, String code){
        if(root==null)
            return;
        if(root.getInfo() instanceof HuffmanLeaf){
            codeMap.put(((HuffmanLeaf) root.getInfo()).getCharacter(), code.length() > 0 ? code:"1");
        }
        createCodeMap(root.getLeft(),code + "0");
        createCodeMap(root.getRight(),code + "1");
    }
    public StringBuilder getHuffmanCode(){
        StringBuilder code = new StringBuilder();
        for(char c : message.toCharArray()){
            code.append(codeMap.get(c));
        }
        return code;
    }
    public void huffmanDecoding(BinaryTreeNode<HuffmanNode>root, StringBuilder code){
        if(root.getInfo() instanceof HuffmanLeaf){
            decode.append(((HuffmanLeaf) root.getInfo()).getCharacter());
            root = (BinaryTreeNode<HuffmanNode>) tree.getRoot();
        }
        if(code.length()==0)
            return;
        huffmanDecoding((code.charAt(0)=='0')?root.getLeft():root.getRight(),code.deleteCharAt(0));
    }
}
