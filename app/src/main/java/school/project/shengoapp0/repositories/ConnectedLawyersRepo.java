package school.project.shengoapp0.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.PendingConnecedLawyerModal;
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyerModal;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.TokenUtil;

public class ConnectedLawyersRepo {
    private Context context;
    private ShengoApiInterface shengoApiInterface;
    MutableLiveData<List<PendingConnecedLawyerModal>> connectedLawyerResponse = new MutableLiveData<>();

    public MutableLiveData<List<PendingConnecedLawyerModal>> getConnectedLawyerResponse() {
        return connectedLawyerResponse;
    }

    private TokenUtil tokenUtil;

    public ConnectedLawyersRepo(Context context) {
        this.context = context;
        this.shengoApiInterface = RetrofitInstance.getService(context);
        this.tokenUtil = new TokenUtil(context.getApplicationContext());
    }

    public void fetchConnectedLawyers(){
        Call<List<PendingConnecedLawyerModal>> call = shengoApiInterface.fetchConnectedLawyers(tokenUtil.getToken());
        call.enqueue(new Callback<List<PendingConnecedLawyerModal>>() {
            @Override
            public void onResponse(Call<List<PendingConnecedLawyerModal>> call, Response<List<PendingConnecedLawyerModal>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    List<PendingConnecedLawyerModal> connectedLawyerModal = response.body();
                    connectedLawyerResponse.setValue(connectedLawyerModal);
                    Log.d("ConnectedLawyer", connectedLawyerModal.get(0).getLawyer_profile().getFirst_name());

                }
            }

            @Override
            public void onFailure(Call<List<PendingConnecedLawyerModal>> call, Throwable throwable) {

            }
        });


    }
}
