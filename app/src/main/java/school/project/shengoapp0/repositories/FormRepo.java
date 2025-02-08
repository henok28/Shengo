package school.project.shengoapp0.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import school.project.shengoapp0.model.VerificationFormModal;
import school.project.shengoapp0.retrofit.VerificationService;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.TokenUtil;

public class FormRepo {
    private VerificationService formService;
    ShengoApiInterface shengoApiInterface;
    Context context;
    private String BASEURL = "http://192.168.179.196:8000/";
    private MutableLiveData<String> formResponse = new MutableLiveData<>();
    private MutableLiveData<String> formError = new MutableLiveData<>();

    public MutableLiveData<String> getFormResponse() {
        return formResponse;
    }

    public MutableLiveData<String> getFormError() {
        return formError;
    }


    public void setFormResponse(MutableLiveData<String> response) {
        this.formResponse = response;
    }

    public void setFormError(MutableLiveData<String> error) {
        this.formError = error;
    }
    private TokenUtil tokenUtil;

    public FormRepo(Context context) {
        this.context = context.getApplicationContext();
        this.shengoApiInterface = RetrofitInstance.getService(context);
        this.tokenUtil = new TokenUtil(context);
    }

    public void submitForm(String firstNameValue, String middleNameValue, String lastNameValue,String phoneNumberValue, String dateOfBirthValue,
                           String genderValue, String residentialAddressValue, String cityValue,
                           String stateValue, File profilePicFile, File idPhotoFileFront, File idPhotoFileBack) {



        // Create the verification form model
        RequestBody firstName = RequestBody.create(MediaType.parse("text/plain"), firstNameValue);
        RequestBody middleName = RequestBody.create(MediaType.parse("text/plain"), middleNameValue);
        RequestBody lastName = RequestBody.create(MediaType.parse("text/plain"), lastNameValue);

        RequestBody phoneNumber = RequestBody.create(MediaType.parse("text/plain"), phoneNumberValue);
        RequestBody dateOfBirth = RequestBody.create(MediaType.parse("text/plain"), dateOfBirthValue);
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), genderValue);
        RequestBody residentialAddress = RequestBody.create(MediaType.parse("text/plain"), residentialAddressValue);
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), cityValue);
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), stateValue);


        RequestBody profilePicRequestBody = RequestBody.create(MediaType.parse("image/*"), profilePicFile);
        MultipartBody.Part profilePicture = MultipartBody.Part.createFormData("profile_picture", profilePicFile.getName(), profilePicRequestBody);

        // Create MultipartBody.Part for ID photo Front
        RequestBody idPhotoRequestBodyFront = RequestBody.create(MediaType.parse("image/*"), idPhotoFileFront);
        MultipartBody.Part idPhotoFrontt = MultipartBody.Part.createFormData("id_photo_front", idPhotoFileFront.getName(), idPhotoRequestBodyFront);

        RequestBody idPhotoRequestBodyBack = RequestBody.create(MediaType.parse("image/*"), idPhotoFileBack);
        MultipartBody.Part idPhotoBack = MultipartBody.Part.createFormData("id_photo_back", idPhotoFileFront.getName(), idPhotoRequestBodyBack);

        // Call the service method to submit the form data
        Call<VerificationFormModal> call = shengoApiInterface.submitFrom(
                tokenUtil.getToken(),
                firstName,
                middleName,
                lastName,
                phoneNumber,
                dateOfBirth,
                gender,
                residentialAddress,
                city,
                state,
                profilePicture,
                idPhotoFrontt,
                idPhotoBack);

        call.enqueue(new Callback<VerificationFormModal>() {
            @Override
            public void onResponse(Call<VerificationFormModal> call, Response<VerificationFormModal> response) {
                if (response.body() != null) {
                    formResponse.setValue(response.body().getMessage());
                    Log.d("Form Submit", "Form submitted successfully");
                } else if (response.errorBody() != null) {
                    Gson gson = new Gson();
                    try {
                        VerificationFormModal errorBody = gson.fromJson(response.errorBody().string(), VerificationFormModal.class);
                        String errorMessage = errorBody.getError();
                        formError.setValue(errorMessage);
                        Log.d("Form Submit Error", "Error: " + errorMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<VerificationFormModal> call, Throwable throwable) {
                Log.d("Form Submit Failure", "Error: " + throwable.getMessage());
            }
        });
    }
}
