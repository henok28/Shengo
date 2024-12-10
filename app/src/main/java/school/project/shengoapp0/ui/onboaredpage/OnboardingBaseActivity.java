package school.project.shengoapp0.ui.onboaredpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import school.project.shengoapp0.R;
import school.project.shengoapp0.databinding.ActivityOnboardingBaseBinding;
import school.project.shengoapp0.ui.autentication.BaseAuthenticationActivity;

public class OnboardingBaseActivity extends AppCompatActivity {
    private ActivityOnboardingBaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.onboardActivity, new BaseFragment());
        transaction.commit();
    }
    public void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.onboardActivity, fragment);
        transaction.commit();

    }
    public void navigateToAuth(){
        Intent intent = new Intent(this, BaseAuthenticationActivity.class);
        startActivity(intent);
        finish();
    }
}