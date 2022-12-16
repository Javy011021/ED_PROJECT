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
                int tam = raf.readInt();
                byte[] huffmanByte = new byte[tam];
                raf.read(huffmanByte);
                HuffmanLeaf huffNode = (HuffmanLeaf) Convert.toObject(huffmanByte);
                queue.offer(new BinaryTreeNode<>(huffNode));
            }
            Huffman.createHuffman(queue);

            raf.close();
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
                byte[] huffmanObj = Convert.toBytes(it.next().getInfo());
                out.writeInt(huffmanObj.length);
                out.write(huffmanObj);
            }
            out.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
