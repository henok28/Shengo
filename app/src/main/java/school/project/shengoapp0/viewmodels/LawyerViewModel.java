package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import school.project.shengoapp0.model.LawyerModal;

public class LawyerViewModel extends AndroidViewModel {

    private final MutableLiveData<List<LawyerModal>> lawyers = new MutableLiveData<>();
    private final MutableLiveData<List<LawyerModal>> requestedLawyers = new MutableLiveData<>();
    private final MutableLiveData<List<LawyerModal>> connectedLawyers = new MutableLiveData<>();

    public LawyerViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<LawyerModal>> getLawyers() {
        return lawyers;
    }

    public LiveData<List<LawyerModal>> getRequestedLawyers() {
        return requestedLawyers;
    }

    public LiveData<List<LawyerModal>> getConnectedLawyers() {
        return connectedLawyers;
    }

    public void addRequest(LawyerModal lawyer) {
        List<LawyerModal> currentRequested = requestedLawyers.getValue();
        if (currentRequested != null) {
            currentRequested.add(lawyer);
            requestedLawyers.setValue(currentRequested);
        }
    }

    public void connectLawyer(LawyerModal lawyer) {
        List<LawyerModal> currentConnected = connectedLawyers.getValue();
        if (currentConnected != null) {
            currentConnected.add(lawyer);
            connectedLawyers.setValue(currentConnected);
        }
    }
}


