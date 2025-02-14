package school.project.shengoapp0.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.AuthCustomResponseModal;
import school.project.shengoapp0.model.UsersAutModal;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.AuthStatUtil;
import school.project.shengoapp0.utilities.TokenUtil;

public class AuthRepo {
    private int maxRetries = 2;
    private int retryDelayMillis = 2000;
    ShengoApiInterface shengoApiInterface;
    private Context context;
    MutableLiveData<String> signUpSuccessStr = new MutableLiveData<>();
    MutableLiveData<String> signUpError = new MutableLiveData<>();


    MutableLiveData<String> loginToken = new MutableLiveData<>();
    MutableLiveData<String> loginError = new MutableLiveData<>();

    public MutableLiveData<String> getLoginToken() {
        return loginToken;
    }

    public MutableLiveData<String> getLoginError() {
        return loginError;
    }

    public void setToken(MutableLiveData<String> token) {
        this.signUpSuccessStr = token;
    }

    public void setError(MutableLiveData<String> error) {
        this.signUpError = error;
    }


    public MutableLiveData<String> getSignUpSuccessStr() {
        return signUpSuccessStr;
    }

    public MutableLiveData<String> getSignUpError() {
        return signUpError;
    }
    AuthStatUtil authStatUtil;

    public AuthRepo(Context context) {
        this.context = context.getApplicationContext();
        this.shengoApiInterface = RetrofitInstance.getService(this.context);
        this.authStatUtil = new AuthStatUtil(this.context);
    }

    public void SendSignupRequest(String firstname,
                                  String middleName,
                                  String lastname,
                                  String email,
                                  String passWord
                                  ) {
        UsersAutModal usersAutModal = new UsersAutModal(firstname,middleName,
                lastname,
                email,
                passWord
                );

        Gson gson = new Gson();
        Log.d("SignUpRequest", "Sending request: " + gson.toJson(usersAutModal));
        makeSignupApiCall(usersAutModal, 0);
    }


    private void makeSignupApiCall(UsersAutModal usersAutModal, int retryCount) {
        Call<AuthCustomResponseModal> call = shengoApiInterface.SignUp(usersAutModal);
        call.enqueue(new Callback<AuthCustomResponseModal>() {
            @Override
            public void onResponse(Call<AuthCustomResponseModal> call, Response<AuthCustomResponseModal> response) {
                Log.d("HTTP Status", "Response code: " + response.code());
                Log.d("HTTP Headers", "Response headers: " + response.headers());

                if (response.body() != null) {
                    AuthCustomResponseModal authResponse = response.body();
                    if (response.isSuccessful()) {
                        String successMsg = authResponse.getMessage();
                        signUpSuccessStr.setValue(successMsg);
                        Log.d("access_token", "onResponse: " + successMsg);
                    }

                } else if (response.errorBody() != null) {
                    Gson gson = new Gson();
                    try {
                        AuthCustomResponseModal errorResponse = gson.fromJson(response.errorBody().string(), AuthCustomResponseModal.class);
                        String mes = errorResponse.getLoginError();
                        signUpError.setValue(mes);
                        Log.d("Error Response", mes);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Log.e("Response is null", "Response body and response error body is null:");
                }
            }

            @Override
            public void onFailure(Call<AuthCustomResponseModal> call, Throwable throwable) {
                Log.d("onFailure", "Error: " + throwable.getMessage());
                handleSignupFailure(call, usersAutModal, throwable, retryCount);
            }
        });

    }

    private void handleSignupFailure(Call<AuthCustomResponseModal> call, UsersAutModal usersAutModal,
                                     Throwable throwable, int retryCount) {
        if (throwable instanceof SocketTimeoutException) {
            if (retryCount < maxRetries) {
                Log.d("retry calling signup", ": "+retryCount+1);
                try {
                    Thread.sleep(retryDelayMillis);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                makeSignupApiCall(usersAutModal, retryCount+1);
            }else{
                signUpError.setValue("Request timed out please try again");
                Log.d("Signup time out: " ,"Signup time out and surpasses the m");
            }
        }else{
            signUpError.setValue("An error occurred while making call to the server");
        }
    }

    public void SendLoginRequest(String email, String password) {
        UsersAutModal usersAutModal = new UsersAutModal(email, password);

        Gson gson1 = new Gson();
        String jsonLog = gson1.toJson(usersAutModal);
        makeLoginApiCall(usersAutModal, 0);

    }


    public void makeLoginApiCall(UsersAutModal usersAutModal, int retryCount) {
        Call<AuthCustomResponseModal> call = shengoApiInterface.Login(usersAutModal);
        call.enqueue(new Callback<AuthCustomResponseModal>() {
            @Override
            public void onResponse(Call<AuthCustomResponseModal> call, Response<AuthCustomResponseModal> response) {
                if (response.body() != null) {
                    AuthCustomResponseModal loginResponse = response.body();
                    if (response.isSuccessful()) {
                        String access_token = loginResponse.getAccess_token();
                        String userId = loginResponse.getUser().getId();
                        SharedPreferences ID = context.getSharedPreferences("signuEmail", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = ID.edit();
                        editor.putString("userid", userId.trim());
                        editor.apply();
                        Log.d("userId", userId);
                        authStatUtil.setVerificationStatus(loginResponse.getVerificationStatus());
                        authStatUtil.setSubscriptionStatus(loginResponse.getSubscriptionStatus());
                        TokenUtil tokenUtil = new TokenUtil(context);
                        tokenUtil.setToken(access_token);
                        loginToken.setValue(access_token);
                        Log.d("Login Stats", ": "+loginResponse.getSubscriptionStatus()+" "+loginResponse.getVerificationStatus());
                        Log.d("Login Statsss", ": "+authStatUtil.getVerificationStatus());
                        authStatUtil.setVerificationStatusString(loginResponse.getVerificationStatus());
                        authStatUtil.setSubscriptionStatusString(loginResponse.getSubscriptionStatus());
                        Log.d("Login Statsss form authutil", ": "+authStatUtil.getVerificationStatusString());


                    }
                } else if (response.errorBody() != null) {
                    Gson gson = new Gson();
                    try {
                        AuthCustomResponseModal errorResponse = gson.fromJson(response.errorBody().string(), AuthCustomResponseModal.class);
                        String errorMessage = errorResponse.getLoginError();
                        Log.d("login error", "error: " + errorMessage);
                        loginError.setValue(errorMessage);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    loginError.setValue("An error occurred please try again. ");
                }
            }

            @Override
            public void onFailure(Call<AuthCustomResponseModal> call, Throwable throwable) {
                Log.d("Login on Failure", "onFailure: " + throwable.getMessage());
                handleLoginFailure(call, throwable, usersAutModal, retryCount);
            }
        });

    }

    private void handleLoginFailure(Call<AuthCustomResponseModal> call, Throwable throwable,
                                    UsersAutModal usersAutModal, int retryCount) {
        if (throwable instanceof SocketTimeoutException) {
            if (retryCount < maxRetries) {
                Log.d("Retry calling", "try again: " + retryCount + 1);
                try {
                    Thread.sleep(retryDelayMillis);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                makeLoginApiCall(usersAutModal, retryCount + 1);
            } else {
                loginError.setValue("Request timed out please try again");
                Log.d("SocketTimeOut", "Time out error: " + throwable);
            }
        } else {
            Log.e("onFailure", "Error: " + throwable.getMessage(), throwable);
            loginError.setValue("An error occurred while making a request");
        }

    }

}
