package school.project.shengoapp0.ui.bottomnavigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import school.project.shengoapp0.R;
import school.project.shengoapp0.adapters.lawyersadpter.ConnectedLawyersAdapter;
import school.project.shengoapp0.adapters.lawyersadpter.LawyerAdapterForFindLawyers;
import school.project.shengoapp0.adapters.lawyersadpter.PendingLawyersAdapter;
import school.project.shengoapp0.model.LawyerModal;
import school.project.shengoapp0.model.PendingConnecedLawyerModal;
import school.project.shengoapp0.viewmodels.ConnectedLawyersViewModel;
import school.project.shengoapp0.viewmodels.LawyersViewModel;
import school.project.shengoapp0.viewmodels.PendingLawyersViewModel;
import school.project.shengoapp0.viewmodels.SendRequestViewModel;

public class FindLawyer extends Fragment {
    private PopupWindow popupWindow;
    private View dimView;
    LawyersViewModel lawyersViewModel;

    SendRequestViewModel sendRequestViewModel;

    PendingLawyersViewModel pendingLawyersViewModel;
    ConnectedLawyersViewModel connectedLawyersViewModel;

    ImageView lawyersImage;
    private static final String PREF_NAME = "MySharedPref";
    private static final String KEY_USER_ID = "user_id";

    Button lawyerBtn, requestBtn, connectedBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_lawyer, container, false);
        // Step 1: Prepare the data
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.lawyerRecyclerView);

        lawyerBtn = view.findViewById(R.id.lawyers_button);
        requestBtn = view.findViewById(R.id.requested_button);
        connectedBtn = view.findViewById(R.id.connected_button);


        // Step 3: Set up the RecyclerView layout manager

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        LawyerAdapterForFindLawyers adapter = new LawyerAdapterForFindLawyers(getContext(), new ArrayList<>(), (lawyerModal, position) -> {
            showCustomPopup(lawyerModal);
        });
        PendingLawyersAdapter adapter2 = new PendingLawyersAdapter(getContext(), new ArrayList<>());
        ConnectedLawyersAdapter adapter3 = new ConnectedLawyersAdapter(getContext(), new ArrayList<>());
//        recyclerView.setAdapter(adapter);


        pendingLawyersViewModel = new ViewModelProvider(this).get(PendingLawyersViewModel.class);

//        pendingLawyersViewModel.fetchPendingLawyers();

        lawyersViewModel = new ViewModelProvider(this).get(LawyersViewModel.class);
        connectedLawyersViewModel = new ViewModelProvider(this).get(ConnectedLawyersViewModel.class);
        setSelectedButton(lawyerBtn);
        lawyersViewModel.getLawyerData();
        lawyersViewModel.getLawyer().observe(getViewLifecycleOwner(), new Observer<List<LawyerModal>>() {
            @Override
            public void onChanged(List<LawyerModal> lawyerModals) {
                adapter.setLawyers(lawyerModals);
                recyclerView.setAdapter(adapter);
            }
        });

        lawyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedButton(lawyerBtn);
                lawyersViewModel.getLawyerData();
                lawyersViewModel.getLawyer().observe(getViewLifecycleOwner(), new Observer<List<LawyerModal>>() {
                    @Override
                    public void onChanged(List<LawyerModal> lawyerModals) {
                        if (lawyerModals != null) {
                            adapter.setLawyers(lawyerModals);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });

                lawyersViewModel.getLawyerData();
            }
        });

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedButton(requestBtn);
                pendingLawyersViewModel.fetchPendingLawyers();
                pendingLawyersViewModel.getPendingMutableLawyer().observe(getViewLifecycleOwner(), new Observer<List<PendingConnecedLawyerModal>>() {
                    @Override
                    public void onChanged(List<PendingConnecedLawyerModal> pendingLawyers) {
                        adapter2.setPendingLawyers(pendingLawyers);
                        recyclerView.setAdapter(adapter2);
                    }
                });


            }
        });

        connectedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedButton(connectedBtn);
                connectedLawyersViewModel.fetchConnectedLawyers();
                connectedLawyersViewModel.getMutableConnectedData().observe(getViewLifecycleOwner(), new Observer<List<PendingConnecedLawyerModal>>() {
                    @Override
                    public void onChanged(List<PendingConnecedLawyerModal> connectedLawyers) {
                        adapter3.setConnectedLawyerResponseModals(connectedLawyers);
                        recyclerView.setAdapter(adapter3);
                    }
                });
            }
        });
    }


    private void setSelectedButton(Button selectedButton) {
        // Reset all buttons to the default state
        resetButton(lawyerBtn);
        resetButton(requestBtn);
        resetButton(connectedBtn);

        // Set the selected button to the highlighted state
        selectedButton.setBackgroundColor(Color.BLACK);
        selectedButton.setTextColor(Color.WHITE);
    }

    private void resetButton(Button button) {
        button.setBackgroundColor(Color.TRANSPARENT); // Or your default background color
        button.setTextColor(Color.BLACK); // Or your default text color
    }

    public void setUserId(String userId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, userId);
        editor.apply(); // Use apply() for asynchronous saving.  Or commit() if you must.
    }

    // Method to get the User ID
    public String getUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    private void showCustomPopup(LawyerModal lawyer) {
        // Get the root view of the fragment
        final ViewGroup root = (ViewGroup) requireActivity().getWindow().getDecorView().findViewById(android.R.id.content);

        // Initialize and configure the dimming view
        dimView = new View(requireContext());
        dimView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dimView.setBackgroundColor(Color.parseColor("#80000000")); // Semi-transparent black (adjust alpha as needed)
        dimView.setAlpha(0f); // Start invisible

        // Add the dimming view to the root
        root.addView(dimView);

        // Animate the dimming view in
        dimView.animate()
                .alpha(1f)
                .setDuration(200)
                .setListener(null);

        // Inflate the custom layout
        View popupView = LayoutInflater.from(requireActivity()).inflate(R.layout.custom_popup_layout, null);

        // Find views inside the popup
        TextView popupTitle = popupView.findViewById(R.id.popupTitle);
        TextView lawyerName = popupView.findViewById(R.id.popUpLawyerName);
        TextView lawyerSpeciality = popupView.findViewById(R.id.speciality);
        TextView lawyerEmail = popupView.findViewById(R.id.lawyerEmail);
        TextView lawyerPhone = popupView.findViewById(R.id.lawyerPhone);
        TextView lawyerCity = popupView.findViewById(R.id.lawyerCity);
        TextView lawyerAddress = popupView.findViewById(R.id.lawyerAddress);
        TextView lawyersYearsOfExp = popupView.findViewById(R.id.lawyerExperiance);
        lawyersImage = popupView.findViewById(R.id.profileImage);

        lawyerName.setText("Lawyer Detail");
        lawyerName.setText(lawyer.getName());
        lawyerSpeciality.setText(lawyer.getSpecialty());
        lawyerEmail.setText(lawyer.getEmail());
        lawyerPhone.setText(lawyer.getPhone());
        lawyerCity.setText(lawyer.getCity());
        lawyerAddress.setText(lawyer.getAddress());
        lawyersYearsOfExp.setText("Experience: " + lawyer.getYearsOfExperiance());
        Log.d("Find Lawyer!!", lawyer.getName() + " " + lawyer.getUser_id());
        setUserId(lawyer.getUser_id());
        loadProfileImage(lawyer.getProfilePictureUrl());


        EditText popupDescription = popupView.findViewById(R.id.popupDescription);
        Button cancelButton = popupView.findViewById(R.id.cancelButton);
        Button sendButton = popupView.findViewById(R.id.send_request_to_lawyer);

        sendRequestViewModel = new ViewModelProvider(this).get(SendRequestViewModel.class);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text, trim it, and store it in the description variable *inside* the onClickListener
                String description = popupDescription.getText().toString().trim();  // Get the description *here* inside the onClickListener

                // Check if the description is empty after trimming.
                if (!description.isEmpty()) {  // Corrected check: check if the *trimmed* string is *not* empty
                    sendRequestViewModel.sendRequestToLawyer(lawyer.getUser_id(), description);
                    Log.d("WTF", description);
                    popupWindow.dismiss(); // Dismiss popup after sending request (assuming 'popupWindow' is defined)
                } else {
                    Toast.makeText(requireActivity(), "Description can't be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Set popup content dynamically based on the clicked lawyer

        // Create a PopupWindow
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );


        // Make the background of the PopupWindow transparent to see the dim effect
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true); // Allow dismissing by touching outside

        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);

        // **Set a dismiss listener for the PopupWindow**
        popupWindow.setOnDismissListener(() -> removeDimView());

        // Set up click listeners
        cancelButton.setOnClickListener(v -> dismissPopup());


        // Show the popup
        if (getActivity() != null) {
            popupWindow.showAtLocation(
                    getActivity().getWindow().getDecorView().getRootView(),
                    Gravity.CENTER,
                    0,
                    0
            );
        }

        // Set up animation for the popup
        popupView.setAlpha(0f);
        popupView.setScaleX(0.9f);
        popupView.setScaleY(0.9f);
        popupView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(200)
                .setListener(null);

        dimView.setOnTouchListener((v, event) -> {
            dismissPopup();
            return true;
        });

    }

    // We still need this to allow clicking on the dim view to dismiss


    private void dismissPopup() {
        if (popupWindow != null && popupWindow.isShowing()) {
            View popupView = popupWindow.getContentView();

            // Animate dismissal
            popupView.animate()
                    .alpha(0f)
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            popupWindow.dismiss();
                            // removeDimView() is now handled by setOnDismissListener
                        }
                    });
        }
    }


    private void removeDimView() {
        if (dimView != null && dimView.getParent() != null) {
            final ViewGroup root = (ViewGroup) requireActivity().getWindow().getDecorView().findViewById(android.R.id.content);
            dimView.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            root.removeView(dimView);
                            dimView = null;
                        }
                    });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        removeDimView();
    }

    private void loadProfileImage(String profilePicturePath) {
        if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
            String baseUrl = getString(R.string.base_url);
            String imageUrl = baseUrl + "/storage/" + profilePicturePath;
//            String imageUrl =  BASE_URL+ profilePicturePath; // Construct the full URL
            Log.d("Image", imageUrl);
            Glide.with(this) // or `getContext()` if `this` is not an Activity
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Optional: Placeholder image
                    .error(R.drawable.image_failed)       // Optional: Error image
                    .apply(RequestOptions.circleCropTransform()) // Apply transformations
                    .into(lawyersImage);


        } else {
            // If no profile picture URL, set a default image
            lawyersImage.setImageResource(R.drawable.ic_launcher_background); // Replace with your default image
        }
    }

}


