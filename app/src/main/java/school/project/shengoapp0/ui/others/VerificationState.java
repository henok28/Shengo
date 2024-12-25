package school.project.shengoapp0.ui.others;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.utilities.FormUtils;
import school.project.shengoapp0.utilities.VerificationStatus;
public class VerificationState extends Fragment {
    private ImageView statusIcon;
    private TextView statusTitle;
    private TextView statusDescription;
    private MaterialButton actionButton;
    private VerificationStatus currentState = VerificationStatus.PENDING;


    public VerificationState() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification_state, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        FormUtils formUtils = new FormUtils(requireContext());
        if (formUtils.getFormState() !=null && formUtils.getFormState() == "APPROVED" ||formUtils.getFormState() == "REJECTED"){
            if (formUtils.getFormState() == "APPROVED"){
                currentState = VerificationStatus.APPROVED;
            }else{
                currentState = VerificationStatus.REJECTED;
            }
        }
        updateUi(currentState);


    }

    private void initViews(View view){
        statusIcon = view.findViewById(R.id.statusIcon);
        statusTitle = view.findViewById(R.id.statusTitle);
        statusDescription = view.findViewById(R.id.statusDescription);
        actionButton = view.findViewById(R.id.actionButton);
    }

    private void updateUi(VerificationStatus currentState){
        switch (currentState){
            case PENDING:
                setupPendingState();
                break;
            case APPROVED:
                setupApprovedState();
                break;
            case REJECTED:
                setupRejectedState();
                break;
        }
    }

    private void setupPendingState() {
        statusIcon.setImageResource(R.drawable.pending_user); // Add these drawables
        statusTitle.setText("Verification Pending");
        statusDescription.setText("Your verification is being reviewed by our team.");
        actionButton.setText("View Form");
        actionButton.setOnClickListener(v -> showSubmittedInfo());
    }

    private void setupApprovedState() {
        statusIcon.setImageResource(R.drawable.verified_user);
        statusTitle.setText("Verification Approved");
        statusDescription.setText("Congratulations! Your verification has been approved.");
        actionButton.setText("Subscription");
        actionButton.setOnClickListener(v -> navigateToSubscription());
    }

    private void setupRejectedState() {
        statusIcon.setImageResource(R.drawable.rejected_user);
        statusTitle.setText("Verification Rejected");
        statusDescription.setText("Unfortunately, your verification was not approved.");
        actionButton.setText("Verify");
        actionButton.setOnClickListener(v -> resubmitForm());
    }

    private void showSubmittedInfo() {
        // Navigate to a fragment showing submitted information
        // You can pass the verification data through Bundle
    }

    private void navigateToSubscription() {
        ((MainActivity)requireActivity()).swapFragments(new Subscription(), "Subscription");
    }

    private void resubmitForm() {
        // Navigate back to verification form
    }
}