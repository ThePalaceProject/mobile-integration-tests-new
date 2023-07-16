package framework.utilities.swipe.directions;

import aquality.appium.mobile.elements.interfaces.IElement;
import framework.utilities.swipe.Direction;
import lombok.AllArgsConstructor;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import java.util.function.BiFunction;

@AllArgsConstructor
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
            .setFrom(new Point(point.x + dimension.width / 4, point.y))
            .setTo(new Point(point.x + dimension.width - 1, point.y))
    ),
    RIGHT((point, dimension) -> new Direction()
            .setFrom(new Point(point.x + dimension.width - dimension.width / 4, point.y + dimension.height / 4))
            .setTo(new Point(point.x, point.y + dimension.height / 4))
    );

    private final BiFunction<Point, Dimension, Direction> pointFunction;

    public Direction getSwipeDirection(IElement element) {
        Point upperLeft = element.getElement().getLocation();
        Dimension dimensions = element.getElement().getSize();
        return this.pointFunction.apply(upperLeft, dimensions);
    }
}
