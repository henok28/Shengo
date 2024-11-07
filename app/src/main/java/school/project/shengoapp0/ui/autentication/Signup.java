package school.project.shengoapp0.ui.autentication;

import android.os.Bundle;

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


public class Signup extends Fragment {
    private EditText fullName;
    private EditText emailInput;
    private EditText passwordInput;
    private Button signupButton;
    private Button loginText;

    @Nullable

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fullName = view.findViewById(R.id.fullName);
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        signupButton = view.findViewById(R.id.signupButton);
        loginText = view.findViewById(R.id.loginText);

        signupButton.setOnClickListener(v -> {
            // Implement sign-up logic here (for now, just a Toast)
            Toast.makeText(getActivity(), "Sign Up Successful!", Toast.LENGTH_SHORT).show();
        });
        loginText.setOnClickListener(v -> {
            // Navigate back to the Login fragment
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).loadLoginFragment();
            }
        });
    }

    }

