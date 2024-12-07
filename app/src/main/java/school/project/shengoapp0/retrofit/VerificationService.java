package school.project.shengoapp0.retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import school.project.shengoapp0.model.AuthCustomResponseModal;
import school.project.shengoapp0.model.VerificationFormModal;

public interface VerificationService {
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
            @Part MultipartBody.Part idPhoto
    );
}
