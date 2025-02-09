package school.project.shengoapp0.ui.others;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import school.project.shengoapp0.R;

public class FeedbackActivity extends AppCompatActivity {

    private EditText nameEditText, feedbackEditText;
    private Spinner categorySpinner;
    private Button submitButton;
    private String selectedCategory;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        feedbackEditText = findViewById(R.id.feedbackEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        submitButton = findViewById(R.id.submitButton);

        try {
            // Set up Spinner adapter with custom layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.feedback_categories,// Ensure this array exists in res/values/strings.xml
                    R.layout.spinner_item_feedback);// Updated to correct file name
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_feedback);// Fixed layout reference
            categorySpinner.setAdapter(adapter);

            // Set listener for Spinner selection
            categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedCategory = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    selectedCategory = "";
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Error loading categories: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        // Get user input
        String name = nameEditText.getText().toString().trim();
        String feedback = feedbackEditText.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || feedback.isEmpty() || selectedCategory.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Display a confirmation message
        String message = "Thank you, " + name + "! Your feedback has been submitted under '" + selectedCategory + "'.";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Clear the form
        nameEditText.setText("");
        feedbackEditText.setText("");
        categorySpinner.setSelection(0);
    }
}

