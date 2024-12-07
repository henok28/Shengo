package school.project.shengoapp0.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import school.project.shengoapp0.model.AuthCustomResponseModal;
import school.project.shengoapp0.model.ResourceModal;
import school.project.shengoapp0.model.UsersAutModal;

public interface ResourceService {
    @Headers("Accept: application/json")
    @GET("api/client/resources")
    Call<List<ResourceModal>> getResource(@Header("Authorization")String token);

}
