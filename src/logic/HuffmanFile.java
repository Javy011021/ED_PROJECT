package logic;

import tree.BinaryTreeNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class HuffmanFile {
    public static String load(File file){
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");

            //get binary code
            byte[] codeByte = new byte[raf.readInt()];
            raf.read(codeByte);
            String code = (String)Convert.toObject(codeByte);

            //get queue
            long size = raf.length();
            PriorityQueue<BinaryTreeNode<HuffmanNode>> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getInfo().getFrequency()));
            while (raf.getFilePointer()<size){
                char character = raf.readChar();
                int frequency = raf.readInt();
                queue.offer(new BinaryTreeNode<>(new HuffmanLeaf(frequency, character)));
            }
            raf.close();
            Huffman.createHuffman(queue);
            return Huffman.huffmanDecoding(code);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void save(String directory, String phrase, String code){
        try {
            byte data[] = Convert.toBytes(code);
            RandomAccessFile out = new RandomAccessFile(directory,"rw");
            out.writeInt(data.length);
            out.write(data);
            PriorityQueue<BinaryTreeNode<HuffmanNode>> queue = Huffman.processString(phrase);
            Iterator<BinaryTreeNode<HuffmanNode>> it=queue.iterator();
            while (it.hasNext() ){
                HuffmanLeaf node = (HuffmanLeaf)it.next().getInfo();
                out.writeChar(node.getCharacter());
                out.writeInt(node.getFrequency());
            }
            out.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
