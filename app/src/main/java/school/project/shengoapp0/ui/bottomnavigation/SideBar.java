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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.model.ProfileModal;
import school.project.shengoapp0.repositories.ProfileRepo;
import school.project.shengoapp0.ui.autentication.Signup;
import school.project.shengoapp0.viewmodels.AuthViewModel;
import school.project.shengoapp0.viewmodels.ProfileViewModel;

public class SideBar extends Fragment {
    TextView headerUserName, headerEmail, verificationStatus, subscriptionStatus, userName, userPhoneNum;
    ImageView profileImageView, sidebarProgressIcon, navigateToSettings;

    Button logoutBtn;
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
        profileImageView = view.findViewById(R.id.user_profile_pic);
        sidebarProgressIcon = view.findViewById(R.id.sidebar_icon);
        navigateToSettings = view.findViewById(R.id.navigate_to_settings);

        logoutBtn = view.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).logout();
            }
        });

        navigateToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).swapFragment(new Settings());
            }
        });

        verificationStatus = view.findViewById(R.id.verification_status);
        subscriptionStatus = view.findViewById(R.id.subsciption_status);

        userName = view.findViewById(R.id.profile_full_name);
        userPhoneNum = view.findViewById(R.id.profile_phone_number);


        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getProfile();

        profileViewModel.getProfileObservable().observe(getViewLifecycleOwner(), new Observer<ProfileModal>() {
            @Override
            public void onChanged(ProfileModal profileModal) {
//                Log.d("Value of Profile: ", profileModal.getProfilePicture());


                // Update UI with profile data
                if (!profileModal.getVerificationStatus().equals("verified")){
                    sidebarProgressIcon.setVisibility(View.GONE);
                }

                if (profileModal.getSubscriptionStatus().equals("unsubscribed")){
                    sidebarProgressIcon.setVisibility(View.GONE);
                }
                headerUserName.setText(profileModal.getFirstName().toUpperCase()+" "+profileModal.getMiddleName().toUpperCase());
                headerEmail.setText(profileModal.getEmail());


                verificationStatus.setText(profileModal.getVerificationStatus().toUpperCase());

                subscriptionStatus.setText(profileModal.getSubscriptionStatus().toUpperCase());
                userName.setText(profileModal.getEmail());
                userPhoneNum.setText(profileModal.getPhoneNumber());

                // Load the profile image
                loadProfileImage(profileModal.getProfilePicture());
            }
        });




    }


    private void loadProfileImage(String profilePicturePath) {
        if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
            String BASE_URL = "http://192.168.1.2:8000/storage/";
            String imageUrl =  BASE_URL+ profilePicturePath; // Construct the full URL
            Log.d("client profile", imageUrl);

            Glide.with(this) // or `getContext()` if `this` is not an Activity
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Optional: Placeholder image
                    .error(R.drawable.pending_user)       // Optional: Error image
                    .apply(RequestOptions.circleCropTransform()) // Apply transformations
                    .into(profileImageView);


        } else {
            // If no profile picture URL, set a default image
            profileImageView.setImageResource(R.drawable.ic_launcher_background); // Replace with your default image
        }
    }
}
