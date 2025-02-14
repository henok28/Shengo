package school.project.shengoapp0.ui.bottomnavigation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.ui.autentication.Login;
import school.project.shengoapp0.ui.autentication.VerificationForm;
import school.project.shengoapp0.ui.others.FeedbackActivity;
import school.project.shengoapp0.ui.others.MapActivity;
import school.project.shengoapp0.ui.others.SuccessSubscriptionPage;
import school.project.shengoapp0.ui.others.VerificationState;
import school.project.shengoapp0.utilities.AuthStatUtil;


public class Settings extends Fragment {
    private AuthStatUtil authStatUtil;
    private TextView selectedLanguage;
    private ImageView englishCheck, amharicCheck;
    RelativeLayout logoutBtn;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button upgradeBtn = view.findViewById(R.id.upgrade);

        RelativeLayout feedbackLayout = view.findViewById(R.id.feedback);
        feedbackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), FeedbackActivity.class);
//                // Start the activity
//                startActivity(intent);
            }
        });



        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("FormStatus", Context.MODE_PRIVATE);
        boolean hasSubmittedForm = sharedPreferences.getBoolean("hasSubmittedForm", false);


        SharedPreferences sharedPreferences1 = requireActivity().getSharedPreferences("SubscriptionStatus", Context.MODE_PRIVATE);
        boolean hasSubscribed = sharedPreferences1.getBoolean("hasSubscribed", false);


        authStatUtil = new AuthStatUtil(requireActivity());

        upgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Value form setting Form", " " + hasSubmittedForm + " " + authStatUtil.getVerificationStatus());
                Log.d("Value form setting Subscription", " " + hasSubscribed + " " + authStatUtil.getSubscriptionStatus());
                if (getActivity() != null)
                    if (authStatUtil.getVerificationStatus() || hasSubmittedForm) {
                        if (hasSubscribed || authStatUtil.getSubscriptionStatus()) {
                            ((MainActivity) requireActivity()).swapFragments(new SuccessSubscriptionPage());
                        } else {
                            ((MainActivity) requireActivity()).swapFragments(new VerificationState(), "VerificationState");
                        }
                    } else {
                        ((MainActivity) requireActivity()).swapFragments(new VerificationForm(), "VerificationForm");
                    }
            }
        });


        RelativeLayout languageOption = view.findViewById(R.id.thirdcontainter);
        selectedLanguage = view.findViewById(R.id.textview_language);

        englishCheck = view.findViewById(R.id.englishCheck);
        amharicCheck = view.findViewById(R.id.amharicCheck);

        logoutBtn = view.findViewById(R.id.logout_btn);

        preferences = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        editor = preferences.edit();


        languageOption.setOnClickListener(v -> showLanguageDialog());

        logoutBtn.setOnClickListener(v -> logout());
        updateLanguageDisplay();
    }

    private void logout() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("FormStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("hasSubmittedForm", false);
        editor.apply();
        SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("SubscriptionStatus", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor2.putBoolean("hasSubscribed", false);
        editor2.commit();
        school.project.shengoapp0.utilities.TokenUtil tokenUtil = new school.project.shengoapp0.utilities.TokenUtil(requireContext());
        tokenUtil.clearToken();
        ((MainActivity) requireActivity()).swapFragments(new Login());
    }

    private void showLanguageDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_language_selection);

        LinearLayout englishOption = dialog.findViewById(R.id.englishOption);
        LinearLayout amharicOption = dialog.findViewById(R.id.amharicOption);
        ImageView englishCheck = dialog.findViewById(R.id.englishCheck);
        ImageView amharicCheck = dialog.findViewById(R.id.amharicCheck);

        String currentLang = preferences.getString("selectedLanguage", "en");


        if (currentLang.equals("am")) {
            selectedLanguage.setText("አማርኛ");
            resetImageView(englishCheck, amharicCheck);
            amharicCheck.setVisibility(View.VISIBLE);
            amharicCheck.setImageResource(R.drawable.checked);
        } else {
            selectedLanguage.setText("English");
            resetImageView(englishCheck, amharicCheck);
            englishCheck.setVisibility(View.VISIBLE);
            englishCheck.setImageResource(R.drawable.checked);
        }



        englishOption.setOnClickListener(v -> {
            editor.putString("selectedLanguage", "en");
            editor.apply();
            resetImageView(englishCheck,amharicCheck);
            englishCheck.setImageResource(R.drawable.checked);
            englishCheck.setVisibility(View.VISIBLE);
            updateLanguageDisplay();
            restartApp();
            dialog.dismiss();
        });

        amharicOption.setOnClickListener(v -> {
            editor.putString("selectedLanguage", "am");
            editor.apply();
            resetImageView(englishCheck,amharicCheck);
            amharicCheck.setImageResource(R.drawable.checked);
            amharicCheck.setVisibility(View.VISIBLE);
            updateLanguageDisplay();
            restartApp();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void restartApp() {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void updateLanguageDisplay() {
        String language = preferences.getString("selectedLanguage", "en");
        if (language.equals("am")) {
            selectedLanguage.setText("አማርኛ");
        } else {
            selectedLanguage.setText("English");
        }
    }


    public void resetImageView(ImageView englishCheck, ImageView amharicCheck) {
        englishCheck.setVisibility(View.INVISIBLE);
        amharicCheck.setVisibility(View.INVISIBLE);
    }
}