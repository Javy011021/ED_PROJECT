package gui.views.draw;

import java.awt.*;
import java.util.Map;

public class AllCharacters extends DrawComponent{
    Map<Character,Integer> map;

    public AllCharacters(Color color, Dimension size, Point location, Map<Character, Integer> map) {
        super(color, size, location);
        this.map = map;
    }

    public Map<Character, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Character, Integer> map) {
        this.map = map;
    }
}
