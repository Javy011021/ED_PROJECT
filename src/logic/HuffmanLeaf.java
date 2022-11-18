package logic;

public class HuffmanLeaf extends HuffmanNode{
    private char character;

    public HuffmanLeaf(int frequency, char character) {
        super(frequency);
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }
}
