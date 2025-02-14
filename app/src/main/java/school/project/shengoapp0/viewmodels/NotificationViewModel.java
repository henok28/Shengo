package school.project.shengoapp0.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import school.project.shengoapp0.model.NotificatoinResponseModal;
import school.project.shengoapp0.repositories.NotificationRepo;

public class NotificationViewModel extends AndroidViewModel {
    NotificationRepo notificationRepo;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        notificationRepo = new NotificationRepo(application);
    }

    public MutableLiveData<List<NotificatoinResponseModal>> getMutableNotification(){
        return notificationRepo.getNotifications();
    }

    public void getNotificationData(){
        notificationRepo.getNotificationData();
    }
}
