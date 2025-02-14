package school.project.shengoapp0.ui.others;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import school.project.shengoapp0.R;
import school.project.shengoapp0.adapters.notification.NotificationAdapter;
import school.project.shengoapp0.model.NotificatoinResponseModal;
import school.project.shengoapp0.viewmodels.NotificationViewModel;

public class NotificationPage extends Fragment {
    NotificationViewModel notificationViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.notifications_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        NotificationAdapter adapter = new NotificationAdapter(getContext(), new ArrayList<>());
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        notificationViewModel.getNotificationData();
        notificationViewModel.getMutableNotification().observe(getViewLifecycleOwner(), new Observer<List<NotificatoinResponseModal>>() {
            @Override
            public void onChanged(List<NotificatoinResponseModal> notificatoinResponseModals) {
                if (notificatoinResponseModals!=null){
                    adapter.setNotificatoinResponseModals(notificatoinResponseModals);
                    recyclerView.setAdapter(adapter);
                    SharedPreferences NotificationFlag = requireActivity().getSharedPreferences("signuEmail", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = NotificationFlag.edit();
                    editor.putBoolean("notification", true);
                    editor.apply();
                }else{
                    String indicator = "NO NOTIFICATION YET";
                }

            }
        });

    }
}