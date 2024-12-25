package school.project.shengoapp0.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import school.project.shengoapp0.repositories.SubscriptionRepo;

public class SubscriptionViewModel extends AndroidViewModel {
    SubscriptionRepo subscriptionRepo;
    public SubscriptionViewModel(@NonNull Application application) {
        super(application);
        subscriptionRepo = new SubscriptionRepo(application);

    }

    public MutableLiveData<String> getMutableLink(){
        return subscriptionRepo.getLink();
//        Log.d("Link Form ViewModel", ": "+subscriptionRepo.getLink());
    }

    public void subscribe(int amount , String plan){
        subscriptionRepo.sendSubscriptionRequest(amount, plan);
    }

    public void checkSubscriptoinStatus(){
        subscriptionRepo.checkSubscriptoinStatus();
    }
    public MutableLiveData<String> getSubscriptionStatus(){
        return subscriptionRepo.getSubscriptionStatus();
    }

}
