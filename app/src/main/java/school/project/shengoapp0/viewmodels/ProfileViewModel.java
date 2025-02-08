package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import school.project.shengoapp0.model.Book;
import school.project.shengoapp0.model.ProfileModal;
import school.project.shengoapp0.repositories.ProfileRepo;

public class ProfileViewModel extends AndroidViewModel {
    ProfileRepo profileRepo;
    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepo = new ProfileRepo(application);
    }

    public void getProfile(){
        profileRepo.getProfile();
    }

    public MutableLiveData<ProfileModal> getProfileObservable(){
        return profileRepo.getProfileResponse();
    }

}
