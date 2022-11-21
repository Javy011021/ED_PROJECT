package logic;

import tree.BinaryTree;
import tree.BinaryTreeNode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class Huffman {
    private BinaryTree<HuffmanNode>tree;
    private String message;


    public PriorityQueue<BinaryTreeNode<HuffmanNode>> processString (){
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c: message.toCharArray()){
            frequency.put(c,frequency.getOrDefault(c,0)+1);
        }
        PriorityQueue<BinaryTreeNode<HuffmanNode>>result = new PriorityQueue<>(Comparator.comparingInt(node -> node.getInfo().getFrequency()));
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
    public void createCodeMap(BinaryTreeNode<HuffmanNode>node, String code, HashMap<Character,String> map){

        if(node==null)
            return;
        if(node.getInfo() instanceof HuffmanLeaf){
            map.put(((HuffmanLeaf) node.getInfo()).getCharacter(), code.length() > 0 ? code:"1");
        }
        createCodeMap(node.getLeft(),code + "0",map);
        createCodeMap(node.getRight(),code + "1",map);
    }
    public StringBuilder getHuffmanCode(){
        HashMap<Character,String>codeMap = new HashMap<>();
        createCodeMap((BinaryTreeNode<HuffmanNode>) tree.getRoot(),"", codeMap);
        StringBuilder code = new StringBuilder();
        for(char c : message.toCharArray()){
            code.append(codeMap.get(c));
        }
        return code;
    }

    public String huffmanDecoding(String code){
        StringBuilder result = new StringBuilder();
        StringBuilder codeAux = new StringBuilder(code);
        huffmanDecodingAux((BinaryTreeNode<HuffmanNode>) tree.getRoot(),codeAux,result);
        return result.toString();
    }
    public void huffmanDecodingAux(BinaryTreeNode<HuffmanNode>node, StringBuilder code, StringBuilder decode){
        if(node.getInfo() instanceof HuffmanLeaf){
            decode.append(((HuffmanLeaf) node.getInfo()).getCharacter());
            node = (BinaryTreeNode<HuffmanNode>) tree.getRoot();
        }
        if(code.length()==0)
            return;
        huffmanDecodingAux((code.charAt(0)=='0')?node.getLeft():node.getRight(),code.deleteCharAt(0),decode);
    }
}
