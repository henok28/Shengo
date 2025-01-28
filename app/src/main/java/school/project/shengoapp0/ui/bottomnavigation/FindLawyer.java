package school.project.shengoapp0.ui.bottomnavigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import java.util.ArrayList;
import java.util.List;
import school.project.shengoapp0.R;
import school.project.shengoapp0.adapters.lawyersadpter.LawyerAdapterForFindLawyers;
import school.project.shengoapp0.model.LawyerModal;
import school.project.shengoapp0.viewmodels.LawyersViewModel;

public class FindLawyer extends Fragment {
    private PopupWindow popupWindow;
    private View dimView;
    LawyersViewModel lawyersViewModel;

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
//        List<LawyerModal> lawyerList = new ArrayList<>();
//        lawyerList.add(new LawyerModal("Ellis Andrews", "Criminal Defense Lawyer", R.drawable.person_1));
//        lawyerList.add(new LawyerModal("Christy Barnes", "Finance & Securities Lawyer", R.drawable.person_2));
//        lawyerList.add(new LawyerModal("Ramon Ward", "Tax Lawyer", R.drawable.person_3));
//        lawyerList.add(new LawyerModal("Irvin Johnson", "Corporate Lawyer", R.drawable.person_4));
//        lawyerList.add(new LawyerModal("Henok Girma", "Criminal Defense Lawyer", R.drawable.person_5));
//        lawyerList.add(new LawyerModal("Brook Abera", "Criminal Defense Lawyer", R.drawable.person_1));
//        lawyerList.add(new LawyerModal("Christy Barnes", "Finance & Securities Lawyer", R.drawable.person_2));
//        lawyerList.add(new LawyerModal("Ramon Ward", "Tax Lawyer", R.drawable.person_3));
//        lawyerList.add(new LawyerModal("Irvin Johnson", "Corporate Lawyer", R.drawable.person_4));
//        lawyerList.add(new LawyerModal("Brook Abera", "Criminal Defense Lawyer", R.drawable.person_5));


        // Step 3: Set up the RecyclerView layout manager

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        LawyerAdapterForFindLawyers adapter = new LawyerAdapterForFindLawyers(getContext(), new ArrayList<>(), (lawyerModal, position) -> {
            // Handle item click - you can implement this later or leave it empty for now
            Toast.makeText(requireContext(), "Clicked: " + lawyerModal.getName(), Toast.LENGTH_SHORT).show();
            showCustomPopup(lawyerModal);
        });
        recyclerView.setAdapter(adapter);


        lawyersViewModel = new ViewModelProvider(this).get(LawyersViewModel.class);

        lawyersViewModel.getLawyerData();

        lawyersViewModel.getLawyer().observe(getViewLifecycleOwner(), new Observer<List<LawyerModal>>() {
            @Override
            public void onChanged(List<LawyerModal> lawyerModals) {
                if (lawyerModals!=null){
                    adapter.setLawyers(lawyerModals);
                    recyclerView.setAdapter(adapter);
                }


            }
        });
        // Step 4: Set the RecyclerView adapter with a click listener
//        LawyerAdapterForFindLawyers adapter = new LawyerAdapterForFindLawyers(
//                requireContext(),
//                lawyerList,
//                (lawyer, position) -> {
//                    // Handle item click
//                    Toast.makeText(requireContext(), "Clicked: " + lawyer.getName(), Toast.LENGTH_SHORT).show();
//                    showCustomPopup(lawyer); // Show popup with lawyer details
//                }
//        );
//        recyclerView.setAdapter(adapter);

        lawyersViewModel.getLawyerData();

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
        ImageView lawyersImage = popupView.findViewById(R.id.profileImage);


        lawyerName.setText("Lawyer Detail");
        lawyerName.setText(lawyer.getName());
        lawyerSpeciality.setText(lawyer.getSpecialty());
        lawyerEmail.setText(lawyer.getEmail());
        lawyerPhone.setText(lawyer.getPhone());
        lawyerCity.setText(lawyer.getCity());
        lawyerAddress.setText(lawyer.getAddress());
        lawyersYearsOfExp.setText("Experience: "+lawyer.getYearsOfExperiance());



        EditText popupDescription = popupView.findViewById(R.id.popupDescription);
        Button cancelButton = popupView.findViewById(R.id.cancelButton);
        Button sendButton = popupView.findViewById(R.id.sendButton);



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
        sendButton.setOnClickListener(v -> {
            // Handle send button action, dismiss popup
            Toast.makeText(requireContext(), "Details sent for " + lawyer.getName(), Toast.LENGTH_SHORT).show();
            dismissPopup();
        });

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

        // We no longer need this as setOnDismissListener handles outside touches
        // popupWindow.getContentView().setOnTouchListener((v, event) -> {
        //     return false;
        // });

        // We still need this to allow clicking on the dim view to dismiss
        dimView.setOnTouchListener((v, event) -> {
            dismissPopup();
            return true;
        });
    }

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
}
