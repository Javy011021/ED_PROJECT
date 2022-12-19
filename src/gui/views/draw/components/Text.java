package gui.views.draw.components;

import java.awt.*;

public class Text extends DrawComponent{
    String phrase;

    public Text(Color color, Dimension size, Point location, String text) {
        super(color, size, location);
        this.phrase = text;
    }
    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
