package screens.epub;

import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.Screen;
import constants.appattributes.IosAttributes;
import framework.utilities.ActionProcessorUtils;
import framework.utilities.KeyboardUtils;
import framework.utilities.LocatorUtils;
import io.appium.java_client.android.nativekey.AndroidKey;
import models.AndroidLocator;
import models.IosLocator;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class SearchEpubScreen extends Screen {

    private final ITextBox txbSearchField = getElementFactory().getTextBox(LocatorUtils.getLocator(
            new AndroidLocator(By.id("search_src_text")),
            new IosLocator(By.xpath("//XCUIElementTypeTextField"))), "Search text box");
    private final IButton btnDeleteKeyIos = getElementFactory().getButton(By.xpath("//XCUIElementTypeKey[@name=\"delete\"]"), "Delete button");
    private final IButton btnKIos = getElementFactory().getButton(By.xpath("//XCUIElementTypeKey[@name=\"k\"]"), "K button");
    private final IButton btnSearchIos = getElementFactory().getButton(By.xpath(""), "Search button");

    private final IButton btnDeleteAndroid = getElementFactory().getButton(By.id("search_close_btn"), "Delete button");

    private static final String FOUND_TEXT_LOCATOR_IOS = "";

    private static final String FOUND_TEXT_LOCATOR_ANDROID = "//androidx.recyclerview.widget.RecyclerView/android.widget.TextView";

    public SearchEpubScreen () {
        super(LocatorUtils.getLocator(
                new AndroidLocator(By.id("search_src_text")),
                new IosLocator(By.xpath("//XCUIElementTypeTextField"))), "Search epub screen");
    }

    public boolean isSearchScreenOpened() {
        return txbSearchField.state().waitForDisplayed();
    }

    public void enterText(String text) {
        txbSearchField.sendKeys(text);
    }

    public String getTextFromSearchTxb() {
        return txbSearchField.getText();
    }

    public void deleteSomeData() {
        ActionProcessorUtils.doForAndroid(() -> KeyboardUtils.pressKey(AndroidKey.DEL));
        ActionProcessorUtils.doForIos(btnDeleteKeyIos::click);
    }

    public void inputCharacterK() {
        ActionProcessorUtils.doForAndroid(() -> KeyboardUtils.pressKey(AndroidKey.K));
        ActionProcessorUtils.doForIos(btnKIos::click);
    }

    public void applySearch() {
        ActionProcessorUtils.doForAndroid(() -> KeyboardUtils.pressKey(AndroidKey.ENTER));
        ActionProcessorUtils.doForIos(btnSearchIos::click);
    }

    public List<String> getListOfFoundTexts() {
        List<ILabel> foundTextLabels = getFoundTexts();
        List<String> resultList = new ArrayList<>();
        ActionProcessorUtils.doForAndroid(() -> {
            foundTextLabels.forEach(lblText -> resultList.add(lblText.getText()));
        });

        if(resultList.isEmpty()){
            ActionProcessorUtils.doForIos(() ->{
                foundTextLabels.forEach(lblText -> resultList.add(lblText.getAttribute(IosAttributes.NAME)));
            });
        }

        return resultList;
    }

    public void deleteText() {
        ActionProcessorUtils.doForAndroid(btnDeleteAndroid::click);
    }

    private List<ILabel> getFoundTexts() {
        return getElementFactory().findElements(LocatorUtils.getLocator(
                new AndroidLocator(By.xpath(FOUND_TEXT_LOCATOR_ANDROID)),
                new IosLocator(By.xpath(""))), ElementType.LABEL);
    }
}
