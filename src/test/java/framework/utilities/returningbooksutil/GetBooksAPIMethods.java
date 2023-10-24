package framework.utilities.returningbooksutil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetBooksAPIMethods {
    @GET("lyrasis-reads/loans")
    Call<APIPageXMLModel> getBooks(@Header("Authorization") String authorization);
}
