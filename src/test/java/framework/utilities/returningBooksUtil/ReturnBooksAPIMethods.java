package framework.utilities.returningBooksUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ReturnBooksAPIMethods {
    @GET("{path}")
    Call<ResponseBody> returnBooks(@Header("Authorization") String authorization, @Path("path") String path);
}
