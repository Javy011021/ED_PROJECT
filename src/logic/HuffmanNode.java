package logic;

import java.io.Serializable;

public class HuffmanNode implements Serializable {
    protected int frequency;

    public HuffmanNode(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
