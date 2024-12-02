package school.project.shengoapp0.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import school.project.shengoapp0.model.AuthCustomResponseModal;
import school.project.shengoapp0.model.UsersAutModal;
import school.project.shengoapp0.retrofit.AuthService;

public class AuthRepo {
    private AuthService authService;
    private Application application;
    private String BASEURL = "http://192.168.251.196:8000/";
    MutableLiveData<String> signupToken = new MutableLiveData<>();
    MutableLiveData<String> signupError = new MutableLiveData<>();


    MutableLiveData<String> loginToken = new MutableLiveData<>();
    MutableLiveData<String> loginError = new MutableLiveData<>();

    public MutableLiveData<String> getLoginToken() {
        return loginToken;
    }

    public MutableLiveData<String> getLoginError() {
        return loginError;
    }

    public void setToken(MutableLiveData<String> token) {
        this.signupToken = token;
    }

    public void setError(MutableLiveData<String> error) {
        this.signupError = error;
    }


    public MutableLiveData<String> getSignupToken() {
        return signupToken;
    }

    public MutableLiveData<String> getSignupError() {
        return signupError;
    }

    public AuthRepo() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //attaching it to okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Interceptor headerInterceptor = new Interceptor() {
            @NonNull
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request modifiedRequest = originalRequest.newBuilder()
                        .addHeader("Accept", "application/json") // Set header
                        .build();
                return chain.proceed(modifiedRequest);
            }
        };

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        application = new Application();

        authService = retrofit.create(AuthService.class);
    }


    public void SendSignupRequest(String firstname,
                                  String lastname,
                                  String email,
                                  String passWord){
        UsersAutModal usersAutModal = new UsersAutModal(firstname,
                lastname,
                email,
                passWord);

        Gson gson = new Gson();
        Log.d("SignUpRequest", "Sending request: " + gson.toJson(usersAutModal));




        Call<AuthCustomResponseModal> call = authService.SignUp(usersAutModal);

        call.enqueue(new Callback<AuthCustomResponseModal>() {
            @Override
            public void onResponse(Call<AuthCustomResponseModal> call, Response<AuthCustomResponseModal> response) {
                Log.d("HTTP Status", "Response code: " + response.code());
                Log.d("HTTP Headers", "Response headers: " + response.headers());

                if (response.body() != null) {
                    AuthCustomResponseModal authResponse = response.body();
                    if (response.isSuccessful()) {
                        String accessToken = authResponse.getAccess_token();
                        signupToken.setValue(accessToken);
                        Log.d("access_token", "onResponse: " + accessToken);
                    }

                }else if (response.errorBody() != null) {
                       Gson gson = new Gson();
                        try {
                            AuthCustomResponseModal errorResponse = gson.fromJson(response.errorBody().string(), AuthCustomResponseModal.class);
                            String mes= errorResponse.getMessage();
                            signupError.setValue(mes);
                            Log.d("Error Response", mes);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            }
            @Override
            public void onFailure(Call<AuthCustomResponseModal> call, Throwable throwable) {
//                Toast.makeText(application, "Failed", Toast.LENGTH_SHORT).show();
                Log.d("onFailure", "Error: "+throwable.getMessage());


            }
        });

    }

    public void SendLoginRequest(String email, String password){
        UsersAutModal usersAutModal = new UsersAutModal(email, password);

        Gson gson1 = new Gson();
        String jsonLog = gson1.toJson(usersAutModal);
        Call<AuthCustomResponseModal> call = authService.Login(usersAutModal);
        call.enqueue(new Callback<AuthCustomResponseModal>() {
            @Override
            public void onResponse(Call<AuthCustomResponseModal> call, Response<AuthCustomResponseModal> response) {
                if (response.body() !=null){
                    AuthCustomResponseModal loginResponse = response.body();
                    if (response.isSuccessful()){
                        String access_token = loginResponse.getAccess_token();
                        loginToken.setValue(access_token);
                        Log.d("Login token", "login token: "+access_token);
                    }
                } else if (response.errorBody() !=null) {
                    Gson gson = new Gson();
                    try {
                        AuthCustomResponseModal errorResponse = gson.fromJson(response.errorBody().string(), AuthCustomResponseModal.class);
                        String errorMessage = errorResponse.getLoginError();
                        Log.d("login error", "error: "+errorMessage);
                        loginError.setValue(errorMessage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<AuthCustomResponseModal> call, Throwable throwable) {
                Log.d("Login on Failure", "onFailure: "+throwable.getMessage());
            }
        });


    }

}
