package school.project.shengoapp0.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.LawyerModal;
import school.project.shengoapp0.model.LawyerResponseModal;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.TokenUtil;

public class LawyersRepo {
    private Context context;
    private ShengoApiInterface shengoApiInterface;

    MutableLiveData<List<LawyerModal>> lawyerDataResponse = new MutableLiveData<>();

    public MutableLiveData<List<LawyerModal>> getLawyerDataResponse() {
        return lawyerDataResponse;
    }
    private TokenUtil tokenUtil;

    public LawyersRepo(Context context){
        this.context = context.getApplicationContext();
        this.shengoApiInterface = RetrofitInstance.getService(context);
        this.tokenUtil = new TokenUtil(context.getApplicationContext());
    }

    public void getLawyers(){
        Call<List<LawyerResponseModal>> call = shengoApiInterface.getLawyers(tokenUtil.getToken());

        call.enqueue(new Callback<List<LawyerResponseModal>>() {
            @Override
            public void onResponse(Call<List<LawyerResponseModal>> call, Response<List<LawyerResponseModal>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<LawyerResponseModal> lawyerModals = response.body();
                    List<LawyerModal> lawyers = new ArrayList<>();


                    for (LawyerResponseModal lawyer:lawyerModals){
                        lawyers.add(new LawyerModal(lawyer.getFirstName()+" "+lawyer.getLastName(),
                                lawyer.getSpecialization(),
                                lawyer.getEmail(),
                                lawyer.getPhoneNumber(),
                                lawyer.getCity(),
                                lawyer.getAddress(),
                                lawyer.getYearOfExperience(),
                                lawyer.getProfilePicture()));

                    }
                    lawyerDataResponse.setValue(lawyers);
                }else {
                    Log.d("Lawyer Response", "Failed with code: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<LawyerResponseModal>> call, Throwable throwable) {
                Log.d("failed to featch lawyers", ": " + throwable.getMessage());

            }
        });
    }

}
