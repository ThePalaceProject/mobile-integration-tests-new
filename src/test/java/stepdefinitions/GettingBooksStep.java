package stepdefinitions;

import constants.util.UtilConstants;
import enums.BookType;
import framework.utilities.swipe.SwipeElementUtils;
import org.apache.commons.lang3.StringUtils;
import screens.CatalogScreen;
import screens.SearchScreen;

import java.util.List;
import java.util.Random;

public class GettingBooksStep {
    private static final CatalogScreen catalogScreen = new CatalogScreen();
    private static final SearchScreen searchScreen = new SearchScreen();

    public static String getBookFromSection(String bookType, String distributor, String availability) {
        if(bookType.equalsIgnoreCase(UtilConstants.AUDIOBOOK)){
            catalogScreen.switchToCatalogTab("Audiobooks");
            catalogScreen.state().waitForDisplayed();
        } else if (bookType.equalsIgnoreCase(UtilConstants.EBOOK)){
            catalogScreen.switchToCatalogTab("eBooks");
            catalogScreen.state().waitForDisplayed();
        }

        switch (distributor) {
            case UtilConstants.BIBLIOBOARD:
                catalogScreen.openCategory(UtilConstants.BIBLIOBOARD_CATEGORY);
                break;
            case UtilConstants.BIBLIOTHECA:
                catalogScreen.openCategory(UtilConstants.BIBLIOTHECA_CATEGORY);
                break;
            case UtilConstants.AXIS_360:
                catalogScreen.openCategory(UtilConstants.AXIS_360_CATEGORY);
                break;
            case UtilConstants.PALACE_BOOKSHELF:
                catalogScreen.openCategory(UtilConstants.PALACE_MARKETPLACE);
                break;
        }

        SwipeElementUtils.swipeDown();
        List<String> books = catalogScreen.getListOfBooksNames();
        Random random = new Random();
        int bookIndex = random.nextInt(books.size());
        String bookName = books.get(bookIndex);
        if(bookType.equalsIgnoreCase(BookType.AUDIOBOOK.getBookType())) {
            bookName = StringUtils.substringBefore(bookName, ". Audiobook.");
        }
        return bookName;
    }
}
