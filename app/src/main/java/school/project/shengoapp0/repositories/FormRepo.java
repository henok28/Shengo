package school.project.shengoapp0.repositories;

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

public class FormRepo {
    private VerificationService formService;
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

    public FormRepo() {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        // Attaching the logging interceptor to OkHttpClient
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
//                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        formService = retrofit.create(VerificationService.class);
    }

    public void submitForm(String fullNameValue, String phoneNumberValue, String dateOfBirthValue,
                           String genderValue, String residentialAddressValue, String cityValue,
                           String stateValue, File profilePicFile, File idPhotoFile) {


        String token = "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTkyLjE2OC4xNzkuMTk2OjgwMDAvYXBpL2F1dGgvbG9naW4iLCJpYXQiOjE3MzM0ODIyOTgsImV4cCI6MTczMzQ4NTg5OCwibmJmIjoxNzMzNDgyMjk4LCJqdGkiOiJsbUZVbjJ1VDc0ZUZmc1hnIiwic3ViIjoiYmRlNzNjY2UtODYwZC00YzRjLWExMzAtYjJhYjMzY2I3OTRiIiwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.bSZH7WD8OeLuLrQ7ZKeSDq9TVBzwRVW9OBITr30_2qE";
        // Create the verification form model
        RequestBody fullName = RequestBody.create(MediaType.parse("text/plain"), fullNameValue);
        RequestBody phoneNumber = RequestBody.create(MediaType.parse("text/plain"), phoneNumberValue);
        RequestBody dateOfBirth = RequestBody.create(MediaType.parse("text/plain"), dateOfBirthValue);
        RequestBody gender = RequestBody.create(MediaType.parse("text/plain"), genderValue);
        RequestBody residentialAddress = RequestBody.create(MediaType.parse("text/plain"), residentialAddressValue);
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), cityValue);
        RequestBody state = RequestBody.create(MediaType.parse("text/plain"), stateValue);


        RequestBody profilePicRequestBody = RequestBody.create(MediaType.parse("image/*"), profilePicFile);
        MultipartBody.Part profilePicture = MultipartBody.Part.createFormData("profile_picture", profilePicFile.getName(), profilePicRequestBody);

        // Create MultipartBody.Part for ID photo
        RequestBody idPhotoRequestBody = RequestBody.create(MediaType.parse("image/*"), idPhotoFile);
        MultipartBody.Part idPhoto = MultipartBody.Part.createFormData("id_photo", idPhotoFile.getName(), idPhotoRequestBody);


        // Call the service method to submit the form data
        Call<VerificationFormModal> call = formService.submitFrom(
                token,
                fullName,
                phoneNumber,
                dateOfBirth,
                gender,
                residentialAddress,
                city,
                state,
                profilePicture,
                idPhoto);

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
                        String errorMessage = errorBody.getMessage();
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