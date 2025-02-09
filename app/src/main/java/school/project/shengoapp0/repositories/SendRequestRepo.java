package school.project.shengoapp0.repositories;

import android.content.Context;
import android.util.Log;


import androidx.lifecycle.MutableLiveData;

import java.security.PrivateKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.LawyerToClientRequestModal;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.TokenUtil;

public class SendRequestRepo {
    ShengoApiInterface shengoApiInterface;
    private Context context;
    TokenUtil tokenUtil;

    public MutableLiveData<String> getStatus() {
        return status;
    }

    private MutableLiveData<String> status = new MutableLiveData<>();
    public SendRequestRepo(Context context) {
        this.shengoApiInterface = RetrofitInstance.getService(context);
        this.context = context.getApplicationContext();
        this.tokenUtil = new TokenUtil(context);
    }

    public void sendRequest(String lawyerId, String dis){
        LawyerToClientRequestModal lawyerToClientRequestModal = new LawyerToClientRequestModal(dis);
        Call<LawyerToClientRequestModal> call = shengoApiInterface.sendLawyerRequest(tokenUtil.getToken(),
                lawyerId,
                lawyerToClientRequestModal);


        call.enqueue(new Callback<LawyerToClientRequestModal>() {
            @Override
            public void onResponse(Call<LawyerToClientRequestModal> call, Response<LawyerToClientRequestModal> response) {
                if (response.isSuccessful() && response.body()!=null){
                    LawyerToClientRequestModal lawyerToClientRequestModal = response.body();
                    if (lawyerToClientRequestModal.getStatus()!=null){
                        status.setValue(lawyerToClientRequestModal.getStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<LawyerToClientRequestModal> call, Throwable throwable) {
                Log.d("Failed to send request to Lawyer", throwable.getMessage());
            }
        });


    }
}
