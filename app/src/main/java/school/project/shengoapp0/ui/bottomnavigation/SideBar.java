package school.project.shengoapp0.ui.bottomnavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import school.project.shengoapp0.R;
import school.project.shengoapp0.model.ProfileModal;
import school.project.shengoapp0.repositories.ProfileRepo;
import school.project.shengoapp0.viewmodels.AuthViewModel;
import school.project.shengoapp0.viewmodels.ProfileViewModel;

public class SideBar extends Fragment {
    TextView headerUserName, headerEmail, verificationStatus, subscriptionStatus, userName, userPhoneNum;
    private ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_side_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        headerUserName = view.findViewById(R.id.header_username);
        headerEmail = view.findViewById(R.id.header_email);

        verificationStatus = view.findViewById(R.id.verification_status);
        subscriptionStatus = view.findViewById(R.id.subsciption_status);

        userName = view.findViewById(R.id.profile_full_name);
        userPhoneNum = view.findViewById(R.id.profile_phone_number);


        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getProfile();

        profileViewModel.getProfileObservable().observe(getViewLifecycleOwner(), new Observer<ProfileModal>() {
            @Override
            public void onChanged(ProfileModal profileModal) {
                Log.d("Value of Profile: ", profileModal.getFirstName());
            }
        });




    }
}