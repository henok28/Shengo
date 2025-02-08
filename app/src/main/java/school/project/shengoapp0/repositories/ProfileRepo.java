package school.project.shengoapp0.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.Book;
import school.project.shengoapp0.model.ProfileModal;
import school.project.shengoapp0.model.ResourceModal;
import school.project.shengoapp0.retrofit.AuthService;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.TokenUtil;

public class ProfileRepo {
    ShengoApiInterface shengoApiInterface;
    private Context context;
    TokenUtil tokenUtil;


    private MutableLiveData<ProfileModal> ProfileResponse = new MutableLiveData<>();

    public MutableLiveData<ProfileModal> getProfileResponse() {
        return ProfileResponse;
    }

    public ProfileRepo(Context context) {
        this.context = context.getApplicationContext();
        this.shengoApiInterface = RetrofitInstance.getService(context);
        this.tokenUtil = new TokenUtil(context);
    }


    public void getProfile(){
        Call<ProfileModal> call = shengoApiInterface.getProfile(tokenUtil.getToken());

        call.enqueue(new Callback<ProfileModal>() {
            @Override
            public void onResponse(Call<ProfileModal> call, Response<ProfileModal> response) {
                if (response.isSuccessful() && response.body()!=null){
                    ProfileModal Profile = response.body();
                    String firstName = Profile.getFirstName();
                    String middleName = Profile.getMiddleName();
                    String profilePic = Profile.getProfilePicture();
                    String email = Profile.getEmail();
                    String phoneNum = Profile.getPhoneNumber();
                    String verififcatoinStatus = Profile.getVerificationStatus();
                    String SubscriptionStatus = Profile.getSubscriptionStatus();

                    ProfileModal profileModal = new ProfileModal(firstName,
                            middleName,
                            phoneNum,
                            profilePic,
                            verififcatoinStatus,
                            SubscriptionStatus,
                            email);


                    Log.d("value from profile repo", firstName+" "+ middleName);

                    ProfileResponse.setValue(profileModal);


                }
            }

            @Override
            public void onFailure(Call<ProfileModal> call, Throwable throwable) {
                Log.d("Profile Feating Error", throwable.getMessage());
            }
        });

    }
}
