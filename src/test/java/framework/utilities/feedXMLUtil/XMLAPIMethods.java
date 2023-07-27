package framework.utilities.feedXMLUtil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface XMLAPIMethods {
    @GET
    Call<FeedModel> getFeed(@Url String path);
}
