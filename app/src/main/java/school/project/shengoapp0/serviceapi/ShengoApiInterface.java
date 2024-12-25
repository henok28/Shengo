package school.project.shengoapp0.serviceapi;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import school.project.shengoapp0.model.AuthCustomResponseModal;
import school.project.shengoapp0.model.ResourceModal;
import school.project.shengoapp0.model.SubscriptionRequestObj;
import school.project.shengoapp0.model.UsersAutModal;
import school.project.shengoapp0.model.VerificationFormModal;
import school.project.shengoapp0.model.modelforsubscription.SubscriptionResponse;

public interface ShengoApiInterface {
    @Headers("Accept: application/json")
    @POST("api/auth/register")
    Call<AuthCustomResponseModal> SignUp(@Body UsersAutModal usersAutModalSignup);



    @Headers("Accept: application/json")
    @POST("api/auth/login")
    Call<AuthCustomResponseModal> Login(@Body UsersAutModal usersAutModalSignup);


    @Headers("Accept: application/json")
    @GET("api/client/resources")
    Call<List<ResourceModal>> getResource(@Header("Authorization")String token);



    @Headers("Accept: application/json")
    @POST("api/client/verification")
    @Multipart
    Call<VerificationFormModal> submitFrom(
            @Header("Authorization") String token,
            @Part("full_name") RequestBody fullName,
            @Part("phone_number") RequestBody phoneNumber,
            @Part("date_of_birth") RequestBody dateOfBirth,
            @Part("gender") RequestBody gender,
            @Part("residential_address") RequestBody residentialAddress,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part MultipartBody.Part profilePicture,
            @Part MultipartBody.Part idPhotoFront,
            @Part MultipartBody.Part idPhotoBack
    );

    @Headers("Accept: application/json")
    @POST("api/initializeSubscription")
    Call<SubscriptionResponse> subscribe(@Header("Authorization")String token, @Body SubscriptionRequestObj subscriptionRequestObj);


    @Headers("Accept: application/json")
    @GET("api/client/statusreturn")
    Call<AuthCustomResponseModal> getSubscriptionStatus(@Header("Authorization")String token);

}
