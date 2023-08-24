package framework.utilities.swipe;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IElement;
import framework.utilities.swipe.directions.EntireElementSwipeDirection;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public class SwipeElementUtils {
    private SwipeElementUtils() {

    }

    public static void swipeElementLeft(IElement element) {
        MobileElement mobileElement = element.getElement();
        Point upperLeft = mobileElement.getLocation();
        Dimension dimensions = mobileElement.getSize();
        element.getTouchActions().swipe(new Point(upperLeft.x, upperLeft.y + dimensions.height / 2));
    }

    public static void swipeElementDown(IElement element) {
        MobileElement mobileElement = element.getElement();
        Point upperLeft = mobileElement.getLocation();
        Dimension dimensions = mobileElement.getSize();
        Point center = mobileElement.getCenter();
        element.getTouchActions().swipe(new Point(upperLeft.x + dimensions.width / 2, center.getY() + dimensions.height / 2));
    }
//
//    public static void swipeElementUp(IElement element) {
//        MobileElement mobileElement = element.getElement();
//        Point upperLeft = mobileElement.getLocation();
//        Point center = mobileElement.getCenter();
//        element.getTouchActions().swipe(new Point(center.x, upperLeft.y));
//    }
//
//    public static void swipeThroughEntireElementUp(IElement element) {
//        MobileElement mobileElement = element.getElement();
//        Point upperLeft = mobileElement.getLocation();
//        Point center = mobileElement.getCenter();
//        Dimension dimensions = mobileElement.getSize();
//        ITouchActions touchActions = AqualityServices.getTouchActions();
//        touchActions.swipe(new Point(center.x, upperLeft.y + dimensions.height - 1), new Point(center.x, upperLeft.y - 1));
//    }
//
//    public static void swipeFromLeftToRight(IElement element) {
//        MobileElement mobileElement = element.getElement();
//        Rectangle rectangle = mobileElement.getRect();
//        element.getTouchActions().swipe(new Point(rectangle.x + rectangle.width - 1, mobileElement.getCenter().y));
//    }
//
    public static void swipeFromRightToLeft(IElement element) {
        Point point = element.getElement().getCenter();
        AqualityServices.getTouchActions().swipe(new Point(0, element.getElement().getCenter().y), point);
    }

    /**
     * The method can be applied to every element with swiping support.
     * Swipe/scroll will be performed from one edge of the element to another.
     *
     * @param element                     element to be scrolled/swiped
     * @param entireElementSwipeDirection direction of the scroll/swipe
     */
    public static void swipeThroughEntireElement(IElement element, EntireElementSwipeDirection entireElementSwipeDirection) {
        Direction direction = entireElementSwipeDirection.getSwipeDirection(element);
        AqualityServices.getTouchActions().swipe(direction.getFrom(), direction.getTo());
    }

    /*
     * The method can be applied to every element with swiping/dragging support.
     * Swipe/drag will be performed from one edge of the screen to another.
     *
     * @param element                   element to be dragged/swiped
     * @param entireScreenDragDirection direction of the drag/swipe
     */
//    public static void dragElementThroughEntireScreen(IElement element, EntireScreenDragDirection entireScreenDragDirection) {
//        Direction direction = entireScreenDragDirection.getDragDirection(element);
//        AqualityServices.getTouchActions().swipe(direction.getFrom(), direction.getTo());
//    }

    public static void swipeDown() {
        double x = AqualityServices.getApplication().getDriver().manage().window().getSize().getWidth() * 0.5;
        double fromY = AqualityServices.getApplication().getDriver().manage().window().getSize().getHeight() * 0.8;
        double toY = AqualityServices.getApplication().getDriver().manage().window().getSize().getHeight() * 0.15;
        AqualityServices.getTouchActions().swipe(new Point((int) x, (int) fromY), new Point((int) x, (int) toY));
    }

    public static void swipeUp() {
        double x = AqualityServices.getApplication().getDriver().manage().window().getSize().getWidth() * 0.5;
        double fromY = 260;
        double toY = AqualityServices.getApplication().getDriver().manage().window().getSize().getHeight() * 0.8;
        AqualityServices.getTouchActions().swipe(new Point((int) x, (int) fromY), new Point((int) x, (int) toY));
    }
}
