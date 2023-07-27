package framework.utilities.feedXMLUtil;

public class GettingBookUtil {
    private static XMLUtil xmlUtil;

    public static synchronized void setXmlUtil(XMLUtil xml){
        xmlUtil = xml;
    }

    public static synchronized void printDistributorsInfo(){
        xmlUtil.getDistributorsInfo();
    }

    public static synchronized String getRandomBook(String availabilityType, String bookType, String distributor){
        return xmlUtil.getRandomBook(availabilityType, bookType, distributor);
    }

    public static synchronized String getRandomPdf(){
        return xmlUtil.getRandomPdf();
    }
}
