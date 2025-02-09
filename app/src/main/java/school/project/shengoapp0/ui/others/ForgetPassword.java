package school.project.shengoapp0.ui.others;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import school.project.shengoapp0.R;

public class ForgetPassword extends Fragment {

    private EditText emailEditText, otpBox1, otpBox2, otpBox3, otpBox4, otpBox5, otpBox6;
    private Button sendOtpButton, verifyButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        otpBox1 = view.findViewById(R.id.otpBox1);
        otpBox2 = view.findViewById(R.id.otpBox2);
        otpBox3 = view.findViewById(R.id.otpBox3);
        otpBox4 = view.findViewById(R.id.otpBox4);
        otpBox5 = view.findViewById(R.id.otpBox5);
        otpBox6 = view.findViewById(R.id.otpBox6);
        sendOtpButton = view.findViewById(R.id.sendOtpButton);
        verifyButton = view.findViewById(R.id.verifyButton);

        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                } else {
                    sendOtp(email);
                }
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = getOtp();
                if (otp.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
                } else {
                    verifyOtp(otp);
                }
            }
        });

        return view;
    }

    private void sendOtp(String email) {
        // Simulate sending OTP
        Toast.makeText(getContext(), "OTP sent to: " + email, Toast.LENGTH_SHORT).show();
    }

    private String getOtp() {
        // Concatenate the OTP from all 6 boxes
        return otpBox1.getText().toString() +
                otpBox2.getText().toString() +
                otpBox3.getText().toString() +
                otpBox4.getText().toString() +
                otpBox5.getText().toString() +
                otpBox6.getText().toString();
    }

    private void verifyOtp(String otp) {
        // Simulate OTP verification
        if (otp.length() == 6) {
            Toast.makeText(getContext(), "OTP verified: " + otp, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Invalid OTP. Please enter a 6-digit code.", Toast.LENGTH_SHORT).show();
        }
    }
}
