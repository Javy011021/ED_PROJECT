package logic;

public class CodeSystem {
    private static CodeSystem system;
    private Huffman huffman;

    public static CodeSystem getInstance(){
        if(system==null){
            system = new CodeSystem();
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
