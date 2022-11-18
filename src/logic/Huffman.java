package logic;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class Huffman {
    public PriorityQueue<HuffmanNode> processString (String message){
        Map<Character, Integer> frequency = new HashMap<>();
        for (char c: message.toCharArray()){
            frequency.put(c,frequency.getOrDefault(c,0)+1);
        }
        PriorityQueue<HuffmanNode>result = new PriorityQueue<>(Comparator.comparingInt(HuffmanNode::getFrequency));
        for(Map.Entry<Character, Integer> aux : frequency.entrySet()){
            result.add(new HuffmanLeaf(aux.getValue(), aux.getKey()));
        }
        return result;
    }
}
