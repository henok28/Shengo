package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import school.project.shengoapp0.model.LawyerModal;
import school.project.shengoapp0.repositories.LawyersRepo;

public class LawyersViewModel extends AndroidViewModel {
    LawyersRepo lawyersRepo;
    public LawyersViewModel(@NonNull Application application) {
        super(application);
        lawyersRepo = new LawyersRepo(application);
    }

    public void getLawyerData(){
        lawyersRepo.getLawyers();
    }

    public MutableLiveData<List<LawyerModal>> getLawyer(){
        return lawyersRepo.getLawyerDataResponse();
    }
}
