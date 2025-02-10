package school.project.shengoapp0.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.PendingConnecedLawyerModal;
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyer;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.TokenUtil;

public class PendingLawyerRepo {
    private Context context;
    private ShengoApiInterface shengoApiInterface;

    MutableLiveData<List<PendingConnecedLawyerModal>> lawyerDataResponse = new MutableLiveData<>();

    public MutableLiveData<List<PendingConnecedLawyerModal>> getMutablePendingLawyer() {
        return lawyerDataResponse;
    }
    private TokenUtil tokenUtil;

    public PendingLawyerRepo(Context context) {
        this.context = context.getApplicationContext();
        this.shengoApiInterface = RetrofitInstance.getService(context);
        this.tokenUtil = new TokenUtil(context.getApplicationContext());
    }


    public void fetchPendingLawyers(){
        Call<List<PendingConnecedLawyerModal>> call = shengoApiInterface.fetchPendingLawyers(tokenUtil.getToken());
        call.enqueue(new Callback<List<PendingConnecedLawyerModal>>() {
            @Override
            public void onResponse(Call<List<PendingConnecedLawyerModal>> call, Response<List<PendingConnecedLawyerModal>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    List<PendingConnecedLawyerModal> pendingLawyers = response.body();
                    lawyerDataResponse.setValue(pendingLawyers);
                }
            }

            @Override
            public void onFailure(Call<List<PendingConnecedLawyerModal>> call, Throwable throwable) {

            }
        });
    }

}
