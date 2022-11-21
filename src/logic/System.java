package logic;

public class System {
    private static System system;
    private Huffman huffman;

    public static System getInstance(){
        if(system==null){
            system = new System();
        }
        return system;
    }
    public String createHuffman(String message){
        huffman = new Huffman(message);
        return huffman.getHuffmanCode(message).toString();
    }
    public String getHuffmanCoding(String code){
        if(huffman!=null)
            return huffman.huffmanDecoding(code);
        return null;
    }
}
