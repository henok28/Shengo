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
import retrofit2.http.Path;
import school.project.shengoapp0.model.AuthCustomResponseModal;
import school.project.shengoapp0.model.LawyerResponseModal;
import school.project.shengoapp0.model.LawyerToClientRequestModal;
import school.project.shengoapp0.model.NotificatoinResponseModal;
import school.project.shengoapp0.model.OTPResponseModal;
import school.project.shengoapp0.model.PendingConnecedLawyerModal;
import school.project.shengoapp0.model.ProfileModal;
import school.project.shengoapp0.model.ResourceModal;
import school.project.shengoapp0.model.SubscriptionRequestObj;
import school.project.shengoapp0.model.UsersAutModal;
import school.project.shengoapp0.model.VerificationFormModal;
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyerModal;
import school.project.shengoapp0.model.modelforsubscription.SubscriptionResponse;
import school.project.shengoapp0.ui.others.OTPModal;

public interface ShengoApiInterface {
    @Headers("Accept: application/json")
    @POST("api/auth/register")
    Call<AuthCustomResponseModal> SignUp(@Body UsersAutModal usersAutModalSignup);



    @Headers("Accept: application/json")
    @POST("api/auth/login")
    Call<AuthCustomResponseModal> Login(@Body UsersAutModal usersAutModalSignup);


    @Headers("Accept: application/json")
    @GET("api/client/resources")
    Call<List<ResourceModal>>
    getResource(
            @Header("Authorization")
            String token);


    @Headers("Accept: application/json")
    @GET("api/client/profile")
    Call<ProfileModal>
    getProfile(
            @Header("Authorization")
            String token);



    @Headers("Accept: application/json")
    @POST("api/client/verification")
    @Multipart
    Call<VerificationFormModal> submitFrom(
            @Header("Authorization") String token,
            @Part("first_name") RequestBody fullName,
            @Part("middle_name") RequestBody middleName,
            @Part("last_name") RequestBody lastName,
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
    @GET("api/client/getSubscriptionStatus")
    Call<AuthCustomResponseModal> getSubscriptionStatus(@Header("Authorization")String token);

    @Headers("Accept: application/json")
    @GET("api/client/find_lawyer")
    Call<List<LawyerResponseModal>> getLawyers(@Header("Authorization")String token);


    //client send requeest to lawyer
    @Headers("Accept: application/json")
    @POST("api/client/lawyer/{lawyerId}/request")
    Call<LawyerToClientRequestModal> sendLawyerRequest(
            @Header("Authorization") String authorization,
            @Path("lawyerId") String lawyerId,
            @Body LawyerToClientRequestModal lawyerRequest // Changed to @Body and LawyerRequest
    );

    @Headers("Accept: application/json")
    @GET("api/client/pending_lawyers")
    Call<List<PendingConnecedLawyerModal>> fetchPendingLawyers(
            @Header("Authorization") String authorization
    );

    @Headers("Accept: application/json")
    @GET("api/client/connected_lawyers")
    Call<List<PendingConnecedLawyerModal>> fetchConnectedLawyers(
            @Header("Authorization") String authorization
    );


    @Headers("Accept: application/json")
    @POST("api/verify_otp")
    Call<OTPResponseModal> sendOtp(@Body OTPModal otpRequest);


    @Headers("Accept: application/json")
    @GET("api/notifications/{userId}")
    Call<List<NotificatoinResponseModal>> getNotifications(
            @Header("Authorization") String authorization,
            @Path("userId") String userId
    );


}
