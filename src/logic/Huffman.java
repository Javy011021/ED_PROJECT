package logic;

import tree.BinaryTree;
import tree.BinaryTreeNode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class Huffman {
    private static BinaryTree<HuffmanNode>tree;


    public static String createHuffman(String message){
        PriorityQueue<BinaryTreeNode<HuffmanNode>> queue = processString(message);
        while (!queue.isEmpty()){
            System.out.println(((HuffmanLeaf)queue.poll().getInfo()).getCharacter());
        }
        createHuffmanTree(processString(message));
        return getHuffmanCode(message).toString();
    }

    public static void createHuffman(PriorityQueue<BinaryTreeNode<HuffmanNode>> queue){

        createHuffmanTree(queue);

    }



    public static PriorityQueue<BinaryTreeNode<HuffmanNode>> processString (String message){
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

    public static void createHuffmanTree(PriorityQueue<BinaryTreeNode<HuffmanNode>>stringProcessed){
        tree = new BinaryTree<>();
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
    public static void createCodeMap(BinaryTreeNode<HuffmanNode>node, String code, HashMap<Character,String> map){

        if(node==null)
            return;
        if(node.getInfo() instanceof HuffmanLeaf){
            map.put(((HuffmanLeaf) node.getInfo()).getCharacter(), code.length() > 0 ? code:"1");
        }
        createCodeMap(node.getLeft(),code + "0",map);
        createCodeMap(node.getRight(),code + "1",map);
    }
    public static StringBuilder getHuffmanCode(String message){
        HashMap<Character,String>codeMap = new HashMap<>();
        createCodeMap((BinaryTreeNode<HuffmanNode>) tree.getRoot(),"", codeMap);
        StringBuilder code = new StringBuilder();
        for(char c : message.toCharArray()){
            code.append(codeMap.get(c));
        }
        return code;
    }

    public static String huffmanDecoding(String code){
        StringBuilder result = new StringBuilder();
        StringBuilder codeAux = new StringBuilder(code);
        huffmanDecodingAux((BinaryTreeNode<HuffmanNode>) tree.getRoot(),codeAux,result);
        return result.toString();
    }
    public static void huffmanDecodingAux(BinaryTreeNode<HuffmanNode>node, StringBuilder code, StringBuilder decode){
        if(node.getInfo() instanceof HuffmanLeaf){
            decode.append(((HuffmanLeaf) node.getInfo()).getCharacter());
            node = (BinaryTreeNode<HuffmanNode>) tree.getRoot();
        }
        if(code.length()==0)
            return;
        if(!(node.getInfo()instanceof HuffmanLeaf))
            huffmanDecodingAux((code.charAt(0)=='0')?node.getLeft():node.getRight(),code.deleteCharAt(0),decode);
        else
            huffmanDecodingAux(node,code.deleteCharAt(0),decode);
    }
}
