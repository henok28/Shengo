package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import school.project.shengoapp0.repositories.OTPRepo;

public class OTPViewModel extends AndroidViewModel {
    private OTPRepo otpRepo;


    public OTPViewModel(@NonNull Application application) {
        super(application);
        otpRepo = new OTPRepo(application);
    }

    public void sendOtpRequest(String email, String password){
        otpRepo.sendOtp(email, password);
    }

    public MutableLiveData<String> getOtpStatus(){
        return otpRepo.getOtpStatusMessage();
    }
}
