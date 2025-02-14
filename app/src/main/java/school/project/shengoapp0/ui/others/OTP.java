package school.project.shengoapp0.ui.others;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.ui.autentication.Login;
import school.project.shengoapp0.ui.dashboared.BaseDashboared;
import school.project.shengoapp0.viewmodels.OTPViewModel;


public class OTP extends Fragment {
    private EditText otpBox1, otpBox2, otpBox3, otpBox4, otpBox5, otpBox6;
    private Button verifyButton;
    private TextView resendButton;

    private OTPViewModel otpViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_o_t_p, container, false);

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

        otpViewModel = new ViewModelProvider(this).get(OTPViewModel.class);

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otpBox1.getText().toString() +
                        otpBox2.getText().toString() +
                        otpBox3.getText().toString() +
                        otpBox4.getText().toString() +
                        otpBox5.getText().toString() +
                        otpBox6.getText().toString();
                if (otp.length() == 6){
                    SharedPreferences signUpEmail = requireActivity().getSharedPreferences("signuEmail", Context.MODE_PRIVATE);
                    String email = signUpEmail.getString("Email", "");
                    Log.d("OTP", "onClick: "+otp);
                    otpViewModel.sendOtpRequest(email, otp);
                    otpViewModel.getOtpStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("Email verified successfully!")){
                                Toast.makeText(requireContext(), "Email Verified Successfully", Toast.LENGTH_SHORT).show(); // Use requireContext()
                                ((MainActivity) requireActivity()).swapFragments(new Login());

                            }
                        }
                    });
                } else if (otp.length() <6 && otp.length()>6) {
                    Toast.makeText(requireContext(), "OTP Must Contain 6 Digits", Toast.LENGTH_SHORT).show(); // Use requireContext()

                }
            }
        });

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

    }
}