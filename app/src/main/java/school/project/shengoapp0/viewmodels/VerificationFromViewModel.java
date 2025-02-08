package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;

import okhttp3.MultipartBody;
import school.project.shengoapp0.model.VerificationFormModal;
import school.project.shengoapp0.repositories.FormRepo;

public class VerificationFromViewModel extends AndroidViewModel {
    private FormRepo formRepository;

    public VerificationFromViewModel(@NonNull Application application) {
        super(application);
        formRepository = new FormRepo(application);

    }

    public void submitForm(String firstName,String middleName,String lastName, String phoneNumber, String dateOfBirth,
                           String gender, String residentialAddress, String city,
                           String state, File profilePicFile, File idPhotoFileFront, File idPhotoFileBack) {
        formRepository.submitForm(firstName,middleName,lastName, phoneNumber, dateOfBirth, gender, residentialAddress,
                city, state, profilePicFile, idPhotoFileFront, idPhotoFileBack);
    }

    public MutableLiveData<String> getFormResponse() {
        return formRepository.getFormResponse();
    }

    public MutableLiveData<String> getFormError() {
        return formRepository.getFormError();
    }

    public void resetFormResponse() {
        MutableLiveData<String> response = new MutableLiveData<>();
        formRepository.setFormResponse(response);
    }

    public void resetFormError() {
        MutableLiveData<String> error = new MutableLiveData<>();
        formRepository.setFormError(error);
    }

}
