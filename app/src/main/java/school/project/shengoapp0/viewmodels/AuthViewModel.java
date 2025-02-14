package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import school.project.shengoapp0.repositories.AuthRepo;

public class AuthViewModel extends AndroidViewModel {
    private AuthRepo authRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepo(application);
    }

    public void sendSignupRequest(String firstname, String middleName,String lastname, String email, String password){
        authRepository.SendSignupRequest(firstname, middleName ,lastname, email, password);
    }

    public void sendLoginRequest(String email, String password){
        authRepository.SendLoginRequest(email, password);
    }
    public MutableLiveData<String> getSignUpSuccessMsg(){
        return authRepository.getSignUpSuccessStr();
    }
   public MutableLiveData<String> getSignupError(){
        return authRepository.getSignUpError();
   }

    public MutableLiveData<String> getLoginToken(){
        return authRepository.getLoginToken();
    }
    public MutableLiveData<String> getLoginError(){
        return authRepository.getLoginError();
    }
    public void resetSignupTokenMutableData(){
        MutableLiveData<String> token = new MutableLiveData<>();
        authRepository.setToken(token);

    }
    public void resetSignupErrorMutableData(){
        MutableLiveData<String> error = new MutableLiveData<>();
        authRepository.setError(error);

    }
}
