package framework.utilities.returningBooksUtil;

import aquality.appium.mobile.application.AqualityServices;
import framework.configuration.Credentials;
import framework.utilities.PropertyUtils;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jaxb.JaxbConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class APIUtil {
    private static final PropertyUtils propertyUtils = new PropertyUtils("src/test/resources/apiConfig.properties");

    public static void returnBooks(Credentials credentials) {
        String authHeader = getAuthHeader(credentials);
        AqualityServices.getLogger().info("There are books on the account for returning: ");
        ArrayList<String> booksForReturning = getListOfBooksInAccount(authHeader);
        AqualityServices.getLogger().info("Count of books on the account for returning: " + booksForReturning.size());
        sendRequestsForReturningBooks(authHeader, booksForReturning);
    }

    public static void enterBookAfterOpeningAccount(Credentials credentials) {
        AqualityServices.getLogger().info("Count of books on the account after opening account: " + enterBooks(credentials));
    }

    public static int enterBooks(Credentials credentials) {
        String authHeader = getAuthHeader(credentials);
        AqualityServices.getLogger().info("There are books on the account: ");
        ArrayList<String> listOfBooks = getListOfBooksInAccount(authHeader);
        return listOfBooks.size();
    }

    public static void enterBooksAfterReturningBooks(Credentials credentials) {
        AqualityServices.getLogger().info("Count of books on the account after returning books: " + enterBooks(credentials));
    }

    private static void sendRequestsForReturningBooks(String authHeader, ArrayList<String> booksForReturning) {
        OkHttpClient client = makeHttpClient();
        ReturnBooksAPIMethods getBooksAPIMethods = new Retrofit
                .Builder()
                .baseUrl(propertyUtils.getProperty("base_url"))
                .client(client)
                .build()
                .create(ReturnBooksAPIMethods.class);

        if (booksForReturning.size() != 0) {
            for (String bookUrl : booksForReturning) {
                String path = bookUrl.replace(propertyUtils.getProperty("base_url"), "");
                try {
                    getBooksAPIMethods.returnBooks(authHeader, path).execute();
                } catch (IOException e) {
                    AqualityServices.getLogger().error(e + e.getMessage());
                }
            }
        }
    }

    private static String getAuthHeader(Credentials credentials) {
        String barcode = credentials.getBarcode();
        String pin = credentials.getPin();
        return "Basic " + Base64.getEncoder().encodeToString(credentials.makeBaseForAuthHeader(barcode, pin).getBytes());
    }

    private static ArrayList<String> getListOfBooksInAccount(String authHeader) {

        ArrayList<String> booksForReturning = new ArrayList<>();
        OkHttpClient client = makeHttpClient();
        GetBooksAPIMethods getBooksAPIMethods = new Retrofit
                .Builder()
                .baseUrl(propertyUtils.getProperty("base_url"))
                .addConverterFactory(JaxbConverterFactory.create())
                .client(client)
                .build()
                .create(GetBooksAPIMethods.class);
        Response<APIPageXMLModel> response = null;

        try {
            response = getBooksAPIMethods.getBooks(authHeader).execute();
        } catch (IOException e) {
            AqualityServices.getLogger().error(e + e.getMessage());
        }

        APIPageXMLModel apiPageXMLModel = response.body();

        if (response.body() == null){
            AqualityServices.getLogger().info("APIUtilResponseCode: " + response.code());
            AqualityServices.getLogger().info("APIUtilResponseToString: " + response.toString());
            throw new RuntimeException("Bad Response, problem with server");
        }

        boolean isEntryPresentInXml = response.body().getEntries() == null;

        if (!isEntryPresentInXml) {
            for (Entry entry : apiPageXMLModel.getEntries()) {
                boolean isRevokePresentInLink = entry.getLinks().stream().anyMatch(link -> link.getHref().toLowerCase().contains("revoke"));
                if(!isRevokePresentInLink){
                    AqualityServices.getLogger().info("Books without revoke in link. BookName: " + entry.getTitle() + " Distributor: " + entry.getDistributor());
                    continue;
                }
                AqualityServices.getLogger().info("bookName: " + entry.getTitle() + " Distributor: " + entry.getDistributor().getDistributorName());
                String link = entry.getLinks().stream().filter(ref -> ref.getHref().toLowerCase().contains("revoke")).findFirst().get().getHref();
                booksForReturning.add(link);
            }
        } else {
            AqualityServices.getLogger().info("There is not books on the account.");
        }

        return booksForReturning;
    }

    private static OkHttpClient makeHttpClient() {
        return new OkHttpClient()
                .newBuilder()
                .connectTimeout(Long.parseLong(propertyUtils.getProperty("connection_timeout")), TimeUnit.SECONDS)
                .readTimeout(Long.parseLong(propertyUtils.getProperty("read_timeout")), TimeUnit.SECONDS)
                .writeTimeout(Long.parseLong(propertyUtils.getProperty("write_timeout")), TimeUnit.SECONDS)
                .build();
    }
}
