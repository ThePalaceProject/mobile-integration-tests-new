package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import framework.utilities.feedXMLUtil.GettingBookUtil;
import io.cucumber.java.en.When;
import org.junit.Assert;
import screens.SearchScreen;
import screens.SubcategoryScreen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLSteps {
    private final ScenarioContext context;
    private final SubcategoryScreen subcategoryScreen;
    private final SearchScreen searchScreen;

    @Inject
    public XMLSteps(ScenarioContext context) {
        subcategoryScreen = new SubcategoryScreen();
        searchScreen = new SearchScreen();
        this.context = context;
    }

    @When("Save {string} book of distributor {string} and bookType {string} as {string}")
    public void saveBookName(String availabilityType, String distributor, String bookType, String bookNameInfoKey) {
        String bookName = getRandomBookName(availabilityType, distributor, bookType);
        AqualityServices.getLogger().info("randomBookName: " + bookName);
        context.add(bookNameInfoKey, bookName);
        System.out.println("Book: " + bookName);
    }

    @When("Search a book {string}")
    public void searchFor(String bookNameInfoKey) {
        String bookName = context.get(bookNameInfoKey);
        Assert.assertTrue("Search modal is not present!", searchScreen.state().waitForDisplayed());
        searchScreen.setSearchedText(bookName);
        searchScreen.applySearch();
        Assert.assertTrue(String.format("Search results page for value '%s' is not present!", bookName), subcategoryScreen.state().waitForDisplayed());
    }

    private String getRandomBookName(String availabilityType, String distributor, String bookType) {
        int amount = 0;
        String bookName = null;

        while (amount < 15) {
            bookName = GettingBookUtil.getRandomBook(availabilityType.toLowerCase(), bookType.toLowerCase(), distributor.toLowerCase());
            Pattern pattern = Pattern.compile("[^\\w :]");
            Matcher matcher = pattern.matcher(bookName);
            amount++;
            if (!matcher.find()) {
                break;
            }
        }
        AqualityServices.getLogger().info("Count of attempts to get random book name without bad symbols-" + amount);

        return bookName;
    }

    @When("Search {string} book of distributor {string} and bookType {string} and save as {string}")
    public void searchFor(String availabilityType, String distributor, String bookType, String bookNameInfoKey) {
        String bookName = getRandomBookNameWithoutBadSymbols(availabilityType, distributor, bookType);
        AqualityServices.getLogger().info("randomBookName: " + bookName);
        context.add(bookNameInfoKey, bookName);
        Assert.assertTrue("Search modal is not present!", searchScreen.state().waitForDisplayed());
        searchScreen.setSearchedText(bookName);
        searchScreen.applySearch();
        Assert.assertTrue(String.format("Search results page for value '%s' is not present!", bookName), subcategoryScreen.state().waitForDisplayed());
    }

    private String getRandomBookNameWithoutBadSymbols(String availabilityType, String distributor, String bookType) {
        int amount = 0;
        String bookName = null;

        while (amount < 15) {
            bookName = GettingBookUtil.getRandomBook(availabilityType.toLowerCase(), bookType.toLowerCase(), distributor.toLowerCase());
            Pattern pattern = Pattern.compile("[^\\w :]");
            Matcher matcher = pattern.matcher(bookName);
            amount++;
            if (!matcher.find()) {
                break;
            }
        }
        AqualityServices.getLogger().info("Count of attempts to get random book name without bad symbols-" + amount);

        return bookName;
    }
}
