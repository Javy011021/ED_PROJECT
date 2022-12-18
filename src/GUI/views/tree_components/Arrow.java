package GUI.views.tree_components;

import javax.xml.stream.Location;
import java.awt.*;

public class Arrow extends DrawComponent{
    int grade;

    public Arrow(Color color, Dimension size, Point location, int grade) {
        super(color, size, location);
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
