package school.project.shengoapp0.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import kotlinx.coroutines.intrinsics.CancellableKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.AuthCustomResponseModal;
import school.project.shengoapp0.model.SubscriptionRequestObj;
import school.project.shengoapp0.model.modelforsubscription.Data;
import school.project.shengoapp0.model.modelforsubscription.Details;
import school.project.shengoapp0.model.modelforsubscription.SubscriptionResponse;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.AuthStatUtil;
import school.project.shengoapp0.utilities.TokenUtil;

public class SubscriptionRepo {
    ShengoApiInterface shengoApiInterface;
    private Context context;
    private TokenUtil tokenUtil;
    private AuthStatUtil authStatUtil;
    MutableLiveData<String> link = new MutableLiveData<>();
    MutableLiveData<String> subscriptionStatus = new MutableLiveData<>();

    public MutableLiveData<String> getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public SubscriptionRepo(Context context) {
        this.context = context.getApplicationContext();
        this.shengoApiInterface = RetrofitInstance.getService(this.context);
        this.tokenUtil = new TokenUtil(context);
        this.authStatUtil = new AuthStatUtil(context);
    }

    public MutableLiveData<String> getLink() {
        return link;
    }


    public void sendSubscriptionRequest(int amount, String plan) {
        SubscriptionRequestObj subscriptionRequestObj = new SubscriptionRequestObj(amount, plan);
        Call<SubscriptionResponse> call = shengoApiInterface.subscribe(tokenUtil.getToken(), subscriptionRequestObj);
        call.enqueue(new Callback<SubscriptionResponse>() {
            @Override
            public void onResponse(Call<SubscriptionResponse> call, Response<SubscriptionResponse> response) {
                if (response.isSuccessful() && response.body() !=null){
                    SubscriptionResponse subscriptionResponse = response.body();
                    Details details = subscriptionResponse.getDetails();
                    if (details != null){
                        Data data = details.getData();
                        if (data!=null){
                            String checkOutUrl = data.getCheckout_url();
                            if (checkOutUrl!=null){
                                Log.d("Checkout url", ": "+checkOutUrl);
                                link.postValue(checkOutUrl);
                            }
                        }
                    }
                }
                if (response.errorBody() != null){
                    Log.d("Subscription Error", "onResponse: error occurred");
                }
            }

            @Override
            public void onFailure(Call<SubscriptionResponse> call, Throwable throwable) {
                link.postValue("Error occurred: " + throwable.getMessage());
            }
        });

    }
    public void checkSubscriptoinStatus(){
        Call<AuthCustomResponseModal> call = shengoApiInterface.getSubscriptionStatus(tokenUtil.getToken());
        call.enqueue(new Callback<AuthCustomResponseModal>() {
            @Override
            public void onResponse(Call<AuthCustomResponseModal> call, Response<AuthCustomResponseModal> response) {
                if (response.body() != null){
                    AuthCustomResponseModal subscription = response.body();
                    if (response.isSuccessful()){
                        if (subscription.getStatus()!=null){
                            String status = subscription.getStatus();
                            if (status.equals("subscribed")){
                                authStatUtil.setSubscriptionStatus(status);
                                subscriptionStatus.setValue(status);

                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<AuthCustomResponseModal> call, Throwable throwable) {

            }
        });
    }
}
