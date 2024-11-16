package school.project.shengoapp0.ui.autentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import java.util.Locale;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.ui.dashboared.BaseDashboared;
import school.project.shengoapp0.ui.onboaredpage.DataFragment;


public class Signup extends Fragment {
    Button sigupBtn, textBtn;
    private ToggleButton languageToggleButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sigupBtn = view.findViewById(R.id.signupButton);
        textBtn = view.findViewById(R.id.loginText);
        sigupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).swapFragments(new BaseDashboared());

            }
        });

        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).swapFragments(new Login());
            }
        });

        languageToggleButton = view.findViewById(R.id.languageToggleButton);

        SharedPreferences prefs = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String currentLang = prefs.getString("My_Lang", "en");
        languageToggleButton.setChecked(currentLang.equals("am"));

        languageToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setLocale("am"); // Set to Amharic

            } else {
                setLocale("en"); // Set to English
            }
        });



    }


    private void setLocale(String langCode) {
        // Get the current language from SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String currentLang = prefs.getString("My_Lang", "en");

        // Only proceed if the selected language is different from the current language
        if (!currentLang.equals(langCode)) {
            Locale locale = new Locale(langCode);
            Locale.setDefault(locale);
            Configuration config = getResources().getConfiguration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());

            // Save the new language preference
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("My_Lang", langCode);
            editor.apply();

            // Refresh the activity to apply language change
            getActivity().recreate();
        }
    }


    public void loadLocale() {
        SharedPreferences prefs = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en"); // Default to English if no preference
        setLocale(language);
    }
}