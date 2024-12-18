package school.project.shengoapp0.ui.bottomnavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.nio.charset.MalformedInputException;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.ui.autentication.VerificationForm;


public class Settings extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button upgradeBtn = view.findViewById(R.id.upgrade);


        upgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    ((MainActivity)requireActivity()).swapFragments(new VerificationForm());
            }
        });

    }

}

//genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
//        if (checkedId == R.id.radioMale) {
//        Log.d("GenderSelection", "Male selected");
//        } else if (checkedId == R.id.radioFemale) {
//        Log.d("GenderSelection", "Female selected");
//        } else {
//        Log.d("GenderSelection", "No gender selected");
//        }
//        });