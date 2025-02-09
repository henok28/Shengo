package school.project.shengoapp0.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.PendingLawyer;
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyerResponseModal;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.TokenUtil;

public class ConnectedLawyersRepo {
    private Context context;
    private ShengoApiInterface shengoApiInterface;
    MutableLiveData<List<PendingLawyer>> connectedLawyerResponse = new MutableLiveData<>();

    public MutableLiveData<List<PendingLawyer>> getConnectedLawyerResponse() {
        return connectedLawyerResponse;
    }

    private TokenUtil tokenUtil;

    public ConnectedLawyersRepo(Context context) {
        this.context = context;
        this.shengoApiInterface = RetrofitInstance.getService(context);
        this.tokenUtil = new TokenUtil(context.getApplicationContext());
    }

    public void fetchConnectedLawyers(){
        Call<List<PendingLawyer>> call = shengoApiInterface.fetchConnectedLawyers(tokenUtil.getToken());

        call.enqueue(new Callback<List<PendingLawyer>>() {
            @Override
            public void onResponse(Call<List<PendingLawyer>> call, Response<List<PendingLawyer>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    List<PendingLawyer> connectedLawyers = response.body();

                    Log.d("ConnectedLawyers", connectedLawyers.toString());
                    connectedLawyerResponse.setValue(connectedLawyers);
                }
            }

            @Override
            public void onFailure(Call<List<PendingLawyer>> call, Throwable throwable) {

            }
        });
    }
}
