package school.project.shengoapp0.ui.autentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.ui.dashboared.BaseDashboared;
import school.project.shengoapp0.viewmodels.AuthViewModel;

public class Login extends Fragment {
    private AuthViewModel authViewModel;
    Button loginBtn, navigateToSignUp;
    EditText Email, Password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Email = view.findViewById(R.id.emailLog);
        Password = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.loginButton);

        SharedPreferences loginSharedPreference = requireActivity().getSharedPreferences("myAppLoginStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSharedPreference.edit();


        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();

                if (validateCredentials(email, password)){

                    authViewModel.sendLoginRequest(email, password);
                    authViewModel.getLoginToken().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            handleToken(s);
                            editor.putBoolean("hasSeenLogin", true);
                            editor.apply();
                        }
                    });

                    authViewModel.getLoginError().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            handleError(s);
                        }
                    });
                }





            }
        });

        navigateToSignUp = view.findViewById(R.id.signupText);
        navigateToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).swapFragments(new Signup());
            }
        });
    }

    private boolean validateCredentials(String email, String password) {
        if(email.isEmpty()){
            Email.setError("Email Field is required");
            return false;
        }
        if(password.length() < 6){
            Password.setError("Password must have at least 6 characters");
            return false;
        }

        if (!validateEmail(email)){
            Email.setError("Email Format is invalid!");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void handleToken(String token){
        if (token!=null && !token.isEmpty()){
            ((MainActivity) requireActivity()).swapFragments(new BaseDashboared());
            Toast.makeText(requireActivity(), "Welcome", Toast.LENGTH_SHORT).show();
            authViewModel.resetSignupTokenMutableData();

        }
    }
    private void handleError(String message){
        if (message != null && !message.isEmpty()){
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
            authViewModel.resetSignupErrorMutableData();
        }
    }
}