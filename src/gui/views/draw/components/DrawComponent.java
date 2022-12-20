package gui.views.draw.components;

import java.awt.*;

public class DrawComponent {
    Color color;
    Dimension size;
    Point location;

    public DrawComponent(Color color, Dimension size, Point location) {
        this.color = color;
        this.size = size;
        this.location = location;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
