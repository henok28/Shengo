package school.project.shengoapp0.ui.others;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import school.project.shengoapp0.R;

public class OTP extends Fragment {
    private EditText otpBox1, otpBox2, otpBox3, otpBox4, otpBox5, otpBox6;
    private Button verifyButton;
    private TextView resendButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_otp, container, false);

        otpBox1 = view.findViewById(R.id.otpBox1);
        otpBox2 = view.findViewById(R.id.otpBox2);
        otpBox3 = view.findViewById(R.id.otpBox3);
        otpBox4 = view.findViewById(R.id.otpBox4);
        otpBox5 = view.findViewById(R.id.otpBox5);
        otpBox6 = view.findViewById(R.id.otpBox6);
        verifyButton = view.findViewById(R.id.verifyButton);
        resendButton = view.findViewById(R.id.resendButton);

        // Setup OTP input logic
        setupTextWatcher(otpBox1, otpBox2, null);
        setupTextWatcher(otpBox2, otpBox3, otpBox1);
        setupTextWatcher(otpBox3, otpBox4, otpBox2);
        setupTextWatcher(otpBox4, otpBox5, otpBox3);
        setupTextWatcher(otpBox5, otpBox6, otpBox4);
        setupTextWatcher(otpBox6, null, otpBox5);

        verifyButton.setOnClickListener(v -> verifyOTP());

        resendButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Resending OTP...", Toast.LENGTH_SHORT).show(); // Use requireContext()
        });

        return view;
    }

    private void setupTextWatcher(EditText currentBox, EditText nextBox, EditText previousBox) {
        currentBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1 && nextBox != null) {
                    nextBox.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        currentBox.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (currentBox.getText().toString().isEmpty() && previousBox != null) {
                    previousBox.requestFocus();
                }
            }
            return false;
        });
    }

    private void verifyOTP() {
        String otp = otpBox1.getText().toString() +
                otpBox2.getText().toString() +
                otpBox3.getText().toString() +
                otpBox4.getText().toString() +
                otpBox5.getText().toString() +
                otpBox6.getText().toString();

        if (otp.length() == 6) {
            Toast.makeText(requireContext(), "OTP Verified: " + otp, Toast.LENGTH_SHORT).show(); // Use requireContext()
            // Here you would typically make a network request to verify the OTP with your backend.
        } else {
            Toast.makeText(requireContext(), "Please enter all 6 digits of the OTP", Toast.LENGTH_SHORT).show(); // Use requireContext()
        }
    }
}