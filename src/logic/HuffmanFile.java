package logic;

import tree.BinaryTreeNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class HuffmanFile {
    public static String load(File file){
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            byte[]password= new byte[raf.readInt()];
            raf.read(password);
            if (password[0]=='H'&&password[1]=='T') {
                //get binary code
                int codeLength = raf.readInt();
                byte[] codeByte = new byte[raf.readInt()];
                raf.read(codeByte);
                BitSet bitset = BitSet.valueOf(codeByte);
                String code = "";
                for(int i = 0; i < codeLength; i++) {
                    if(bitset.get(i)) {
                        code += "1";
                    } else {
                        code += "0";
                    }
                }
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
            }else{
                throw new Exception();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void save(String directory, String phrase, String code){
        try {
            BitSet bitSet = new BitSet(code.length());
            int bitcounter = 0;
            for(Character c : code.toCharArray()) {
                if(c.equals('1')) {
                    bitSet.set(bitcounter);
                }
                bitcounter++;
            }
            byte[] data = bitSet.toByteArray();
            byte[] password = {'H','T'};
            RandomAccessFile out = new RandomAccessFile(directory,"rw");
            out.writeInt(2);
            out.write(password);
            out.writeInt(code.length());
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
