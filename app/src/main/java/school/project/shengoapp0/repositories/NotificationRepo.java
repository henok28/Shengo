package school.project.shengoapp0.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.NotificatoinResponseModal;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.AuthStatUtil;
import school.project.shengoapp0.utilities.TokenUtil;

public class NotificationRepo {
    MutableLiveData<List<NotificatoinResponseModal>> notifications = new MutableLiveData<>();

    public MutableLiveData<List<NotificatoinResponseModal>> getNotifications() {
        return notifications;
    }
    private Context context;
    ShengoApiInterface shengoApiInterface;
    TokenUtil tokenUtil;

    public NotificationRepo(Context context) {
        this.context = context;
        this.shengoApiInterface = RetrofitInstance.getService(this.context);
        this.tokenUtil = new TokenUtil(this.context);
    }

    public void getNotificationData(){
        SharedPreferences ID = context.getSharedPreferences("signuEmail", Context.MODE_PRIVATE);
        String userId = ID.getString("userid", "");
        Call<List<NotificatoinResponseModal>> call = shengoApiInterface.getNotifications(tokenUtil.getToken(), userId);

        call.enqueue(new Callback<List<NotificatoinResponseModal>>() {
            @Override
            public void onResponse(Call<List<NotificatoinResponseModal>> call, Response<List<NotificatoinResponseModal>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    List<NotificatoinResponseModal> notificatoinResponseModal = response.body();
                    notifications.setValue(notificatoinResponseModal);
                    Log.d("Noti", notificatoinResponseModal.get(0).getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<NotificatoinResponseModal>> call, Throwable throwable) {

            }
        });
    }
}
