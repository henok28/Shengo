package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import school.project.shengoapp0.repositories.SendRequestRepo;

public class SendRequestViewModel extends AndroidViewModel {
    private SendRequestRepo sendRequestRepo;


    public SendRequestViewModel(@NonNull Application application) {
        super(application);
        sendRequestRepo = new SendRequestRepo(application);
    }

    public void sendRequestToLawyer(String lawyerId, String description){
        sendRequestRepo.sendRequest(lawyerId, description);
    }

    public MutableLiveData<String> getStatus(){
        return sendRequestRepo.getStatus();
    }
}
