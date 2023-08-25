package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import constants.util.UtilConstants;
import enums.BookType;
import enums.localization.sortoptions.AvailabilityKeys;
import framework.utilities.swipe.SwipeElementUtils;
import org.apache.commons.lang3.StringUtils;
import screens.CatalogBooksScreen;
import screens.CatalogScreen;
import screens.SearchScreen;
import screens.SortOptionsScreen;

import java.util.List;
import java.util.Random;

public class GettingBooksStep {
    private static final CatalogScreen catalogScreen = new CatalogScreen();
    private static final SearchScreen searchScreen = new SearchScreen();
    private static final SortOptionsScreen sortOptionsScreen = new SortOptionsScreen();
    private static final CatalogBooksScreen catalogBooksScreen = new CatalogBooksScreen();

    public static String getBookFromSection(String bookType, String distributor) {
        searchScreen.closeSearchScreen();
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

        sortOptionsScreen.openAvailability();
        sortOptionsScreen.changeAvailabilityTo(AvailabilityKeys.AVAILABLE_NOW);

        AqualityServices.getConditionalWait().waitFor(catalogBooksScreen::isFirstBookInCatalogDisplayed);

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