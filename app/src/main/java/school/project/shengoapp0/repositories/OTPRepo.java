package school.project.shengoapp0.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import school.project.shengoapp0.model.OTPResponseModal;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.ui.others.OTPModal;

public class OTPRepo {
    ShengoApiInterface shengoApiInterface;
    private Context context;

    MutableLiveData<String> otpStatusMessage  = new MutableLiveData<>();

    public MutableLiveData<String> getOtpStatusMessage() {
        return otpStatusMessage;
    }

    public OTPRepo(Context context) {
        this.context = context.getApplicationContext();
        this.shengoApiInterface = RetrofitInstance.getService(this.context);

    }

    public void sendOtp(String email, String otpCode){
        OTPModal otpModal = new OTPModal(email, otpCode);
        Call<OTPResponseModal> call = shengoApiInterface.sendOtp(otpModal);

        call.enqueue(new Callback<OTPResponseModal>() {
            @Override
            public void onResponse(Call<OTPResponseModal> call, Response<OTPResponseModal> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    OTPResponseModal otpResponseModal = response.body();
                    String verificatoinStatus = otpResponseModal.getMessage();
                    otpStatusMessage.setValue(verificatoinStatus);

                }
            }

            @Override
            public void onFailure(Call<OTPResponseModal> call, Throwable throwable) {
                Log.d("OTP related Error",  throwable.getMessage());
            }
        });
    }
}
