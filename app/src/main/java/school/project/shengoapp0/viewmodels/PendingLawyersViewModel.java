package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import school.project.shengoapp0.model.PendingLawyer;
import school.project.shengoapp0.repositories.PendingLawyerRepo;

public class PendingLawyersViewModel extends AndroidViewModel {
    PendingLawyerRepo pendingLawyerRepo;

    public PendingLawyersViewModel(@NonNull Application application) {
        super(application);
        pendingLawyerRepo = new PendingLawyerRepo(application);
    }

    public void fetchPendingLawyers(){
        pendingLawyerRepo.fetchPendingLawyers();
    }
    public MutableLiveData<List<PendingLawyer>> getPendingLawyerData(){
        return pendingLawyerRepo.getLawyerDataResponse();
    }
}
