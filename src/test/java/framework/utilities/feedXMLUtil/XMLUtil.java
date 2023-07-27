package framework.utilities.feedXMLUtil;

import aquality.appium.mobile.application.AqualityServices;
import constants.util.UtilConstants;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.RandomUtils;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import stepdefinitions.GettingBooksStep;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class XMLUtil {
    private static final String BASE_URL = "https://gorgon.tpp-qa.lyrasistechnology.org";
    private static final String partOfURL = "lyrasis-reads/crawlable";
    private HashMap<String, List<BookModel>> hashMapAvailableEbooks;
    private HashMap<String, List<BookModel>> hashMapAvailableAudiobooks;
    private HashMap<String, List<BookModel>> hashMapUnavailableEbooks;
    private HashMap<String, List<BookModel>> hashMapUnavailableAudiobooks;
    private ArrayList<BookModel> availableBooksAnyType;
    private ArrayList<BookModel> unavailableBooksAnyType;
    private ArrayList<BookModel> availablePdf;
    private final int CONNECT_TIMEOUT = 120;
    private final int READ_TIMEOUT = 120;
    private final int WRITE_TIMEOUT = 120;
    private final int THREAD_SLEEP_TIME = 3000;

    public XMLUtil() {
        setHashMapsForEBooksAndAudioBooks();
    }

    public void getDistributorsInfo(){
        AqualityServices.getLogger().info("//////////////////////////////////////////////////////////////////////////////////////");
        for (String distributor : hashMapAvailableAudiobooks.keySet()) {
            printDistributorInfo("hashMapAvailableAudiobooks", distributor, hashMapAvailableAudiobooks.get(distributor));
        }
        AqualityServices.getLogger().info("//////////////////////////////////////////////////////////////////////////////////////");
        for (String distributor : hashMapAvailableEbooks.keySet()) {
            printDistributorInfo("hashMapAvailableEbooks", distributor, hashMapAvailableEbooks.get(distributor));
        }
        AqualityServices.getLogger().info("//////////////////////////////////////////////////////////////////////////////////////");
        for (String distributor : hashMapUnavailableAudiobooks.keySet()) {
            printDistributorInfo("hashMapUnavailableAudiobooks", distributor, hashMapUnavailableAudiobooks.get(distributor));
        }
        AqualityServices.getLogger().info("//////////////////////////////////////////////////////////////////////////////////////");
        for (String distributor : hashMapUnavailableEbooks.keySet()) {
            printDistributorInfo("hashMapUnavailableEbooks", distributor, hashMapUnavailableEbooks.get(distributor));
        }
        AqualityServices.getLogger().info("//////////////////////////////////////////////////////////////////////////////////////");
    }

    private void printDistributorInfo(String text, String distributor, List<BookModel> list){
        AqualityServices.getLogger().info("+++++++++++++++++++++");
        AqualityServices.getLogger().info(text + "info\n");
        AqualityServices.getLogger().info("Distributor " + distributor + "\n");
        AqualityServices.getLogger().info("amount " + list.size());
        AqualityServices.getLogger().info("+++++++++++++++++++++");
    }

    private void setListAvailableAndUnavailableBooksAnyTypeMayBeWithRepeat() {
        String url = partOfURL;
        ArrayList<BookModel> listAvailableBooksAnyType = new ArrayList<>();
        ArrayList<BookModel> listUnavailableBooksAnyType = new ArrayList<>();
        ArrayList<BookModel> listAvailablePdf = new ArrayList<>();

        while (true) {
            FeedModel feedModel = supportMethod(url);
            boolean isNextXMLPresent = feedModel.getLinksFromFeed().stream().anyMatch(link -> link.getConditionForNextXML().equals(UtilConstants.NEXT.toLowerCase()));
            if (!isNextXMLPresent) {
                break;
            }

            for (EntryXML entry : feedModel.getEntries()) {

                if (entry.getLanguage() == null)
                    continue;

                boolean isEnLanguagePresent = entry.getLanguage().equals("en");

                if (!isEnLanguagePresent) {
                    continue;
                }

                boolean isPdfTypePresentAndPdfAvailabilityPresent = entry.getLinksFromEntry().stream().anyMatch(link -> link.getListOfIndirectAcquisition() != null && link.getAvailabilityPDF() != null);

                if (isPdfTypePresentAndPdfAvailabilityPresent) {
                    LinkFromEntry link = entry.getLinksFromEntry().stream().filter(filterLink -> filterLink.getAvailabilityPDF() != null && filterLink.getListOfIndirectAcquisition() != null).findFirst().get();
                    List<String> listApplicationTypes = link.getListOfIndirectAcquisition().stream().map(IndirectAcquisition::getType).collect(Collectors.toList());
                    String pdfAvailability = link.getAvailabilityPDF().getStatus();

                    if (listApplicationTypes.stream().anyMatch(applicationType -> applicationType.equalsIgnoreCase("application/pdf"))
                            && listApplicationTypes.stream().noneMatch(applicationType -> applicationType.equalsIgnoreCase("application/epub+zip"))
                            && pdfAvailability.equalsIgnoreCase("available")) {
                        String[] arrayBookType = entry.getBookType().split("/");
                        String bookType = arrayBookType[arrayBookType.length - 1];
                        BookModel bookModel = new BookModel(entry.getDistributor().getDistributorName().toLowerCase(), bookType.toLowerCase(), entry.getBookName(), 0);
                        listAvailablePdf.add(bookModel);
                        continue;
                    }
                }

                boolean isCopiesPresent = entry.getLinksFromEntry().stream().anyMatch(link -> link.getCopies() != null);

                if (!isCopiesPresent) {
                    continue;
                }

                boolean isPdfAndVndAdobeAdeptPresent = false;
                boolean isIndirectAcquisitionTagPresent = entry.getLinksFromEntry().stream().anyMatch(link -> link.getListOfIndirectAcquisition() != null);
                if (isIndirectAcquisitionTagPresent) {
                    LinkFromEntry linkFromEntry = entry.getLinksFromEntry().stream().filter(link -> link.getListOfIndirectAcquisition() != null).findFirst().get();

                    boolean isVndAdobeAdeptPresent = linkFromEntry.getListOfIndirectAcquisition().stream().anyMatch(indirectAcquisition -> indirectAcquisition.getType().toLowerCase().contains("vnd.adobe.adept+xml".toLowerCase()));
                    if (isVndAdobeAdeptPresent) {
                        IndirectAcquisition vndAdobeAdeptIndirectAcquisition = linkFromEntry.getListOfIndirectAcquisition().stream().filter(indirectAcquisition -> indirectAcquisition.getType().toLowerCase().contains("vnd.adobe.adept+xml".toLowerCase())).findFirst().get();
                        boolean isPdfPresent = vndAdobeAdeptIndirectAcquisition.getInternalIndirectAcquisition().getType().toLowerCase().contains("pdf".toLowerCase());
                        if (isPdfPresent) {
                            isPdfAndVndAdobeAdeptPresent = true;
                        }
                    }
                }

                boolean isLibrarySimplifiedPresent = false;
                if (isIndirectAcquisitionTagPresent) {
                    LinkFromEntry linkFromEntry = entry.getLinksFromEntry().stream().filter(link -> link.getListOfIndirectAcquisition() != null).findFirst().get();
                    isLibrarySimplifiedPresent = linkFromEntry.getListOfIndirectAcquisition().stream().anyMatch(indirectAcquisition -> indirectAcquisition.getType().toLowerCase().contains("vnd.librarysimplified.axisnow+json".toLowerCase()));
                }

                if (isPdfAndVndAdobeAdeptPresent || isLibrarySimplifiedPresent) {
                    continue;
                }

                int countAvailableCopies = entry.getLinksFromEntry().stream().filter(link -> link.getCopies() != null).findFirst().get().getCopies().getCountAvailableCopies();

                if (countAvailableCopies > 0) {
                    String[] arrayBookType = entry.getBookType().split("/");
                    String bookType = arrayBookType[arrayBookType.length - 1];
                    listAvailableBooksAnyType.add(new BookModel(entry.getDistributor().getDistributorName().toLowerCase(), bookType.toLowerCase(), entry.getBookName(), countAvailableCopies));
                } else if (countAvailableCopies == 0) {
                    String[] arrayBookType = entry.getBookType().split("/");
                    String bookType = arrayBookType[arrayBookType.length - 1];
                    listUnavailableBooksAnyType.add(new BookModel(entry.getDistributor().getDistributorName().toLowerCase(), bookType.toLowerCase(), entry.getBookName(), countAvailableCopies));
                }
            }

            String nextUrl = feedModel.getLinksFromFeed().stream().filter(link -> link.getConditionForNextXML().equals("next")).findFirst().get().getNextURLForXML();
            url = nextUrl.replace(BASE_URL + "/", "");
        }

        availableBooksAnyType = listAvailableBooksAnyType;
        unavailableBooksAnyType = listUnavailableBooksAnyType;
        availablePdf = getListAvailablePdfWithoutRepetitions(listAvailablePdf);
    }

    private ArrayList<BookModel> getListAvailablePdfWithoutRepetitions(ArrayList<BookModel> arrayList) {
        Set<BookModel> setAvailablePdf = new HashSet<>(arrayList);

        return new ArrayList<>(setAvailablePdf);
    }

    public synchronized String getRandomPdf() {
        if (availablePdf.size() == 0) {
            throw new RuntimeException("availablePdf.size() == 0, There is not available pdf");
        }

        BookModel randomBookModel = availablePdf.get(RandomUtils.nextInt(0, availablePdf.size()));
        String pdf = randomBookModel.getBookName();

        availablePdf.remove(randomBookModel);

        return pdf;
    }

    public synchronized String getRandomBook(String availabilityType, String bookType, String distributor) {
        HashMap<String, List<BookModel>> hashMap = null;
        if (availabilityType.equalsIgnoreCase(UtilConstants.AVAILABLE)) {
            if (bookType.equalsIgnoreCase(UtilConstants.EBOOK)) {
                hashMap = hashMapAvailableEbooks;
            } else if (bookType.equalsIgnoreCase(UtilConstants.AUDIOBOOK)) {
                hashMap = hashMapAvailableAudiobooks;
            }
        } else if (availabilityType.equalsIgnoreCase(UtilConstants.UNAVAILABLE)) {
            if (bookType.equalsIgnoreCase(UtilConstants.EBOOK)) {
                hashMap = hashMapUnavailableEbooks;
            } else if (bookType.equalsIgnoreCase(UtilConstants.AUDIOBOOK)) {
                hashMap = hashMapUnavailableAudiobooks;
            }
        }
        if (!Objects.requireNonNull(hashMap).containsKey(distributor.toLowerCase())) {
            AqualityServices.getLogger().info("There are not any type books in the link for distributor: " + distributor);
//            throw new RuntimeException("There are not any type books for distributor: " + distributor);
            return GettingBooksStep.getBookFromSection(bookType, distributor, availabilityType);
        }

        if (hashMap.get(distributor.toLowerCase()).size() == 0) {
            AqualityServices.getLogger().info("Count of  " + availabilityType + " books == 0 in the link for distributor: " + distributor);
            return GettingBooksStep.getBookFromSection(bookType, distributor, availabilityType);
//            throw new RuntimeException("Count of  " + availabilityType + " books == 0 for distributor: " + distributor);
        }

        String bookName = hashMap.get(distributor.toLowerCase()).get(RandomUtils.nextInt(0, hashMap.get(distributor.toLowerCase()).size())).getBookName();

        List<BookModel> list = hashMap.get(distributor.toLowerCase());
        for (int i = 0; i < list.size(); i++) {
            BookModel bookModel = list.get(i);
            if (bookModel.getBookName().equalsIgnoreCase(bookName)) {
                list.remove(bookModel);
                hashMap.put(distributor.toLowerCase(), list);
                break;
            }
        }

        return bookName;
    }

    private FeedModel supportMethod(String url) {
        FeedModel feedModel = null;
        int sch = 1;

        while (sch < 4) {
            feedModel = getFeedModel(url);
            if (feedModel == null) {
                try {
                    Thread.sleep(THREAD_SLEEP_TIME);
                } catch (InterruptedException e) {
                    AqualityServices.getLogger().error(e + e.getMessage());
                    Thread.currentThread().interrupt();
                }
            } else {
                break;
            }
            sch++;
        }

        if (sch == 3) {
            throw new RuntimeException("Bad Response, problem with server, Count of attempts: " + sch);
        }

        if (feedModel == null) {
            throw new RuntimeException("Bad Response, problem with server, feedModel == null");
        }

        return feedModel;
    }

    private void setHashMapsForEBooksAndAudioBooks() {
        setListAvailableAndUnavailableBooksAnyTypeMayBeWithRepeat();
        hashMapAvailableEbooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(availableBooksAnyType, UtilConstants.EBOOK.toLowerCase());
        hashMapAvailableAudiobooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(availableBooksAnyType, UtilConstants.AUDIOBOOK.toLowerCase());
        hashMapUnavailableEbooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(unavailableBooksAnyType, UtilConstants.EBOOK.toLowerCase());
        hashMapUnavailableAudiobooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(unavailableBooksAnyType, UtilConstants.AUDIOBOOK.toLowerCase());
    }

    private HashMap<String, List<BookModel>> getHashMapForAvailableAndUnavailableBooksWithSpecificType(ArrayList<BookModel> arrayList, String bookType) {

        Set<String> setDistributors = arrayList.stream().map(BookModel::getDistributorName).collect(Collectors.toSet());

        HashMap<String, List<BookModel>> hashMap = new HashMap<>();

        for (String distributor : setDistributors) {
            Set<BookModel> setBooks = arrayList.stream().filter(book -> book.getDistributorName().equalsIgnoreCase(distributor)).collect(Collectors.toSet());
            Set<BookModel> setBooksSpecificBookType = setBooks.stream().filter(book -> book.getBookType().equalsIgnoreCase(bookType)).collect(Collectors.toSet());

            hashMap.put(distributor.toLowerCase(), new ArrayList<>(setBooksSpecificBookType));
        }

        return hashMap;
    }

    private FeedModel getFeedModel(String url) {
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).build();
        XMLAPIMethods xmlapiMethods = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(JaxbConverterFactory.create()).
                client(client).build().create(XMLAPIMethods.class);

        Response<FeedModel> response = null;
        try {
            response = xmlapiMethods.getFeed(url).execute();
        } catch (IOException e) {
            AqualityServices.getLogger().error(e + e.getMessage());
        }

        if (response.body() == null) {
            AqualityServices.getLogger().info("XMLUtilResponseCode: " + response.code());
            AqualityServices.getLogger().info("XMLUtilResponseToString: " + response.toString());
        }

        return response.body();
    }
}
