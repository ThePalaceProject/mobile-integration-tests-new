package framework.utilities.feedxmlutil;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import constants.util.UtilConstants;
import stepdefinitions.GettingBooksStep;

public class GettingBookUtil {
    private static XMLUtil xmlUtil;

    private GettingBookUtil() {}

    public static synchronized void setXmlUtil(XMLUtil xml){
        xmlUtil = xml;
    }

    public static synchronized void printDistributorsInfo(){
        xmlUtil.getDistributorsInfo();
    }

    public static synchronized String getRandomBook(String availabilityType, String bookType, String distributor) {
        if (xmlUtil == null) {
            if (isIosPlatform() && availabilityType.equalsIgnoreCase(UtilConstants.AVAILABLE)) {
                return GettingBooksStep.getBookFromSection(bookType, distributor);
            }
            throw new RuntimeException("Feed bootstrap is unavailable. Current book-selection flow requires feed data.");
        }
        return xmlUtil.getRandomBook(availabilityType, bookType, distributor);
    }

    public static synchronized String getRandomPdf(){
        if (xmlUtil == null) {
            throw new RuntimeException("Feed bootstrap is unavailable. PDF selection requires feed data.");
        }
        return xmlUtil.getRandomPdf();
    }

    private static boolean isIosPlatform() {
        return AqualityServices.getApplication().getPlatformName().equals(PlatformName.IOS);
    }
}
