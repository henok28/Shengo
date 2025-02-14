package school.project.shengoapp0.ui.autentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Locale;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.ui.others.OTP;
import school.project.shengoapp0.viewmodels.AuthViewModel;


public class Signup extends Fragment {
    Button sigupBtn;
    EditText FirstName, MiddleName, LastName, Email, Password, ConfirmPassword;
    TextView textBtn;
    private AuthViewModel authViewModel;
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
        FirstName = view.findViewById(R.id.firstName);
        MiddleName = view.findViewById(R.id.middleName);
        LastName = view.findViewById(R.id.lastName);

        Email = view.findViewById(R.id.email);
        Password = view.findViewById(R.id.passWord);
        ConfirmPassword = view.findViewById(R.id.confirmPassword);

        sigupBtn = view.findViewById(R.id.signupButton);

        textBtn = view.findViewById(R.id.loginText);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sigupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = FirstName.getText().toString();
                String mname = MiddleName.getText().toString();
                String lname = LastName.getText().toString();
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String confirmpassword = ConfirmPassword.getText().toString();

                if (validateFields(fname, mname, lname, email, password, confirmpassword)) {
                    SharedPreferences signUpEmail = requireActivity().getSharedPreferences("signuEmail", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = signUpEmail.edit();
                    editor.putString("Email", email.trim());
                    editor.apply();

                    authViewModel.sendSignupRequest(fname, mname, lname, email, password);


                    authViewModel.getSignupError().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            handleErrorChange(s);
                        }
                    });

                    authViewModel.getSignUpSuccessMsg().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            SharedPreferences signupStat = requireActivity().getSharedPreferences("SignupStatus", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = signupStat.edit();
                            editor.putBoolean("Signup", true);
                            editor.apply();
                            handleTokenChange(s);
                        }
                    });
                }

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


    private boolean validateFields(String firstName, String middleName, String lastName, String email, String password, String confirmPass) {
        if (firstName.isEmpty()) {
            FirstName.setError("First name is required");
            return false;
        }
        if (lastName.isEmpty()) {
            LastName.setError("Last name is required");
            return false;
        }
        if (middleName.isEmpty()) {
            MiddleName.setError("Last name is required");
            return false;
        }
        if (email.isEmpty()) {
            Email.setError("Email field is required");
        }

        if (!isValidEmail(email)) {
            Email.setError("Invalid Email Format");
            return false;
        }

        if (password.length() < 6) {
            Password.setError("Password must be at least 6 characters long");
            return false;
        }

        if (!confirmPass.equals(password)) {
            ConfirmPassword.setError("Password Don't Match");
            return false;
        }
        return true;
    }


    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void handleTokenChange(String m) {
        if (m != null && !m.isEmpty()) {
            if (m.equals("Registration successful! Please check your email for the OTP.")) {
                Toast.makeText(requireActivity(), m, Toast.LENGTH_SHORT).show();
//            Toast.makeText(requireActivity(), "", Toast.LENGTH_SHORT).show();
                Log.d("SignUp", "ur message: " + m);
                ((MainActivity) requireActivity()).swapFragments(new OTP());
                authViewModel.resetSignupTokenMutableData();
            }

        }
    }

    private void handleErrorChange(String error) {
        if (error != null && !error.isEmpty()) {
            Toast.makeText(requireActivity(), "Error: " + error, Toast.LENGTH_SHORT).show();
            authViewModel.resetSignupErrorMutableData();
        }
    }
}