package framework.utilities.swipe;

import org.openqa.selenium.Point;

public class Direction {
    private Point from;
    private Point to;

    public Direction setFrom(Point from) {
        this.from = from;
        return this;
    }

    public Direction setTo(Point to) {
        this.to = to;
        return this;
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }
}
