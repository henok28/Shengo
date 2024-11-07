package school.project.shengoapp0.ui.autentication;

import android.os.Bundle;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import school.project.shengoapp0.R;
public class Login extends Fragment {
        private EditText userName;
        private EditText passwordInput;
        private Button loginButton;
        private Button signupText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName = view.findViewById(R.id.userName);
        passwordInput = view.findViewById(R.id.passwordInput);
        loginButton = view.findViewById(R.id.loginButton);
        signupText = view.findViewById(R.id.signupText);

        loginButton.setOnClickListener(v -> {
            // Check if the username and password are correct
            if (userName.getText().toString().equals("user") && passwordInput.getText().toString().equals("1234")) {
                Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Login Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        signupText.setOnClickListener(v -> {
            // Navigate to the SignUp fragment
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).loadSignUpFragment();
            }
        });
    }

}