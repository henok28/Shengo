package school.project.shengoapp0.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.LawyerModal;
import school.project.shengoapp0.model.PendingLawyer;
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyer;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.TokenUtil;

public class PendingLawyerRepo {
    private Context context;
    private ShengoApiInterface shengoApiInterface;

    MutableLiveData<List<ConnectedLawyer>> lawyerDataResponse = new MutableLiveData<>();

    public MutableLiveData<List<ConnectedLawyer>> getLawyerDataResponse() {
        return lawyerDataResponse;
    }
    private TokenUtil tokenUtil;

    public PendingLawyerRepo(Context context) {
        this.context = context.getApplicationContext();
        this.shengoApiInterface = RetrofitInstance.getService(context);
        this.tokenUtil = new TokenUtil(context.getApplicationContext());
    }


    public void fetchPendingLawyers(){
        Call<List<PendingLawyer>> call = shengoApiInterface.fetchPendingLawyers(tokenUtil.getToken());
        call.enqueue(new Callback<List<PendingLawyer>>() {
            @Override
            public void onResponse(Call<List<PendingLawyer>> call, Response<List<PendingLawyer>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    List<PendingLawyer> pendingLawyers = response.body();

                    List<ConnectedLawyer> connectedLawyers = new ArrayList<>();

                    for (PendingLawyer lawyer : pendingLawyers) { //Corrected variable name lawyer instead of pendingLawyer
                        // Defensive checks to prevent NullPointerExceptions
                        if (lawyer != null && lawyer.getLawyer_profile() != null) {
                            String profilePicture = lawyer.getLawyer_profile().getProfile_picture();
                            String firstName = lawyer.getLawyer_profile().getFirst_name();
                            String middleName = lawyer.getLawyer_profile().getMiddle_name();

                            // Create a new ConnectedLawyer object with the extracted data
                            ConnectedLawyer connectedLawyer = new ConnectedLawyer(profilePicture, firstName, middleName);
                            connectedLawyers.add(connectedLawyer);

                            Log.d("Pending Lawyer", connectedLawyer.getFirstName()+" "+connectedLawyer.getMiddleName());

                        } else {
                            // Handle the case where either the PendingLawyer or Lawyer_profile is null
                            Log.w("fetchPendingLawyers", "Skipping lawyer due to null PendingLawyer or Lawyer_profile"); //Important
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<PendingLawyer>> call, Throwable throwable) {

            }
        });
    }

}
