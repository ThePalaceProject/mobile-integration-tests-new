package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import constants.util.UtilConstants;
import enums.BookType;
import enums.localization.sortoptions.AvailabilityKeys;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.swipe.SwipeElementUtils;
import org.apache.commons.lang3.StringUtils;
import screens.*;
import screens.menubar.MenuBar;
import screens.menubar.MenuBarScreen;

import java.security.SecureRandom;
import java.util.List;

public class GettingBooksStep {
    private static final SecureRandom random = new SecureRandom();
    private static final CatalogScreen catalogScreen = new CatalogScreen();
    private static final SearchScreen searchScreen = new SearchScreen();
    private static final SortOptionsScreen sortOptionsScreen = new SortOptionsScreen();
    private static final CatalogBooksScreen catalogBooksScreen = new CatalogBooksScreen();
    private static final MenuBarScreen menuBarScreen = new MenuBarScreen();

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
            case UtilConstants.PALACE_MARKETPLACE:
                catalogScreen.openCategory(UtilConstants.PALACE_MARKETPLACE_CATEGORY);
                break;
        }

        sortOptionsScreen.openAvailability();
        sortOptionsScreen.changeAvailabilityTo(AvailabilityKeys.AVAILABLE_NOW);

        AqualityServices.getConditionalWait().waitFor(catalogBooksScreen::isFirstBookInCatalogDisplayed);

        SwipeElementUtils.swipeDown();
        String bookName = ActionProcessorUtils.doForAndroid(() -> {
            List<String> books = catalogBooksScreen.getListOfBooks();
            int bookIndex = random.nextInt(books.size());
            return books.get(bookIndex);
        });

        if(bookName == null) {
            bookName = ActionProcessorUtils.doForIos(catalogBooksScreen::getBookFromCatalogSection);
        }

        if(bookType.equalsIgnoreCase(BookType.AUDIOBOOK.getBookType())) {
            bookName = StringUtils.substringBefore(bookName, ". Audiobook.");
        }

        menuBarScreen.openBottomMenuTab(MenuBar.CATALOG);
        return bookName;
    }
}