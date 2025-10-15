package framework.utilities.swipe.directions;

import aquality.appium.mobile.elements.interfaces.IElement;
import framework.utilities.swipe.Direction;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import java.util.function.BiFunction;

public enum EntireElementSwipeDirection {
    UP((point, dimension) -> new Direction()
            .setFrom(new Point(point.x + dimension.width / 2, point.y))
            .setTo(new Point(point.x + dimension.width / 2, point.y + dimension.height))
    ),

    DOWN((point, dimension) -> new Direction()
            .setFrom(new Point(point.x + dimension.width / 2, point.y + dimension.height))
            .setTo(new Point(point.x + dimension.width / 2, point.y))
    ),

    LEFT((point, dimension) -> new Direction()
            .setFrom(new Point(point.x + dimension.width - dimension.width / 4, point.y + dimension.height / 2))
            .setTo(new Point(point.x + dimension.width / 4, point.y + dimension.height / 2))
    ),

    RIGHT((point, dimension) -> new Direction()
            .setFrom(new Point(point.x + dimension.width / 4, point.y + dimension.height / 2))
            .setTo(new Point(point.x + dimension.width - dimension.width / 4, point.y + dimension.height / 2))
    );

    private final BiFunction<Point, Dimension, Direction> pointFunction;


    EntireElementSwipeDirection(BiFunction<Point, Dimension, Direction> pointFunction) {
        this.pointFunction = pointFunction;
    }

    public Direction getSwipeDirection(IElement element) {
        Point upperLeft = element.getElement().getLocation();
        Dimension dimensions = element.getElement().getSize();
        return this.pointFunction.apply(upperLeft, dimensions);
    }
}