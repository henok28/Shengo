package school.project.shengoapp0.ui.bottomnavigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import school.project.shengoapp0.R;
import school.project.shengoapp0.adapters.lawyersadpter.LawyerAdapterForFindLawyers;
import school.project.shengoapp0.model.LawyerModal;

public class FindLawyer extends Fragment {
    private PopupWindow popupWindow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_lawyer, container, false);

        // Step 1: Prepare the data
        List<LawyerModal> lawyerList = new ArrayList<>();
        lawyerList.add(new LawyerModal("Ellis Andrews", "Criminal Defense Lawyer", R.drawable.person_1));
        lawyerList.add(new LawyerModal("Christy Barnes", "Finance & Securities Lawyer", R.drawable.person_2));
        lawyerList.add(new LawyerModal("Ramon Ward", "Tax Lawyer", R.drawable.person_3));
        lawyerList.add(new LawyerModal("Irvin Johnson", "Corporate Lawyer", R.drawable.person_4));
        lawyerList.add(new LawyerModal("Henok Girma", "Criminal Defense Lawyer", R.drawable.person_5));
        lawyerList.add(new LawyerModal("Brook Abera", "Criminal Defense Lawyer", R.drawable.person_1));
        lawyerList.add(new LawyerModal("Christy Barnes", "Finance & Securities Lawyer", R.drawable.person_2));
        lawyerList.add(new LawyerModal("Ramon Ward", "Tax Lawyer", R.drawable.person_3));
        lawyerList.add(new LawyerModal("Irvin Johnson", "Corporate Lawyer", R.drawable.person_4));
        lawyerList.add(new LawyerModal("Brook Abera", "Criminal Defense Lawyer", R.drawable.person_5));

        // Step 2: Find the RecyclerView in the layout
        RecyclerView recyclerView = view.findViewById(R.id.lawyerRecyclerView);

        // Step 3: Set up the RecyclerView layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Step 4: Set the RecyclerView adapter with a click listener
        LawyerAdapterForFindLawyers adapter = new LawyerAdapterForFindLawyers(
                requireContext(),
                lawyerList,
                (lawyer, position) -> {
                    // Handle item click
                    Toast.makeText(requireContext(), "Clicked: " + lawyer.getName(), Toast.LENGTH_SHORT).show();
                    showCustomPopup(lawyer); // Show popup with lawyer details
                }
        );
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void showCustomPopup(LawyerModal lawyer) {
        // Inflate the custom layout
        View popupView = LayoutInflater.from(requireActivity()).inflate(R.layout.custom_popup_layout, null);

        // Find views inside the popup
        TextView popupTitle = popupView.findViewById(R.id.popupTitle);
        EditText popupDescription = popupView.findViewById(R.id.popupDescription);
        Button cancelButton = popupView.findViewById(R.id.cancelButton);
        Button sendButton = popupView.findViewById(R.id.sendButton);

        // Set popup content dynamically based on the clicked lawyer
        popupTitle.setText("Lawyer Detail: " + lawyer.getName());
        popupDescription.setText(lawyer.getSpecialty()); // Use the correct method to set the text

        // Create a PopupWindow
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);

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


        // Dismiss the popup when the user clicks outside the popup window.
        popupWindow.getContentView().setOnTouchListener((v, event) -> {
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
                            popupWindow = null; // Avoid memory leaks
                        }
                    });
        }
    }
}
