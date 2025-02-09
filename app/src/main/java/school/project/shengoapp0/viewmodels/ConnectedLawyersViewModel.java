package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import school.project.shengoapp0.repositories.ConnectedLawyersRepo;

public class ConnectedLawyersViewModel extends AndroidViewModel {
    ConnectedLawyersRepo connectedLawyersRepo;

    public ConnectedLawyersViewModel(@NonNull Application application) {
        super(application);
        connectedLawyersRepo = new ConnectedLawyersRepo(application);
    }

    public void fetchConnectedLawyers(){
        connectedLawyersRepo.fetchConnectedLawyers();
    }
}
