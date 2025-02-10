package school.project.shengoapp0.ui.others;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import school.project.shengoapp0.R;

public class ResetPassword extends AppCompatActivity {

    private EditText newPasswordEditText, confirmPasswordEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_resetpassword); // Correct layout file

        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (newPassword.equals(confirmPassword)) {
                    // Passwords match, proceed with reset logic.  This is where you'd
                    // typically make a network call to your backend to actually
                    // reset the password.  For this example, we'll just show a toast.
                    Toast.makeText(ResetPassword.this, "Password reset successful!", Toast.LENGTH_SHORT).show();

                    //  For a real reset, you'd likely want to finish the activity
                    //  after a successful reset.
                    // finish();

                } else {
                    Toast.makeText(ResetPassword.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}