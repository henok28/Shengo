package school.project.shengoapp0.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import school.project.shengoapp0.model.AuthCustomResponseModal;
import school.project.shengoapp0.model.UsersAutModal;

public interface AuthService {
    @Headers("Accept: application/json")
    @POST("api/auth/register")
    Call<AuthCustomResponseModal> SignUp(@Body UsersAutModal usersAutModalSignup);

    @Headers("Accept: application/json")
    @POST("api/auth/login")
    Call<AuthCustomResponseModal> Login(@Body UsersAutModal usersAutModalSignup);
}
