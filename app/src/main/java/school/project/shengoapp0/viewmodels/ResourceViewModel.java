package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import school.project.shengoapp0.model.Book;
import school.project.shengoapp0.repositories.ResourceRepo;

public class ResourceViewModel extends AndroidViewModel {
    private ResourceRepo resourceRepo;
    public ResourceViewModel(@NonNull Application application) {
        super(application);
        resourceRepo = new ResourceRepo();
    }
    public void getResourceData(){
        resourceRepo.getResourceData();
    }
    public MutableLiveData<List<Book>> getData(){
        return resourceRepo.getResourceResponse();
    }

}
