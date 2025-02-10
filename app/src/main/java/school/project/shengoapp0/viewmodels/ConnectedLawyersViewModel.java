package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import school.project.shengoapp0.model.PendingConnecedLawyerModal;
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyer;
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyerModal;
import school.project.shengoapp0.repositories.ConnectedLawyersRepo;

public class ConnectedLawyersViewModel extends AndroidViewModel {
    ConnectedLawyersRepo connectedLawyersRepo;
    public MutableLiveData<List<PendingConnecedLawyerModal>> getMutableConnectedData(){
        return connectedLawyersRepo.getConnectedLawyerResponse();
    }

    public ConnectedLawyersViewModel(@NonNull Application application) {
        super(application);
        connectedLawyersRepo = new ConnectedLawyersRepo(application);
    }

    public void fetchConnectedLawyers(){
        connectedLawyersRepo.fetchConnectedLawyers();
    }
}
