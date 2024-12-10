package school.project.shengoapp0;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import school.project.shengoapp0.ui.autentication.BaseAuthenticationActivity;
import school.project.shengoapp0.ui.autentication.Login;
import school.project.shengoapp0.ui.autentication.Signup;
import school.project.shengoapp0.ui.bottomnavigation.BaseDashboaredActivity;
import school.project.shengoapp0.ui.bottomnavigation.Home;
import school.project.shengoapp0.ui.dashboared.BaseDashboared;
import school.project.shengoapp0.ui.onboaredpage.BaseFragment;
import school.project.shengoapp0.ui.onboaredpage.OnboardingBaseActivity;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        View decor = window.getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);

        SharedPreferences onBoardingPreferences = getSharedPreferences("myAppOnboardState", MODE_PRIVATE);
        boolean hasSeenOnBoarding = onBoardingPreferences.getBoolean("hasSeenOnboarding", false);

        SharedPreferences loginSharedPreference = getSharedPreferences("myAppLoginStatus", Context.MODE_PRIVATE);
        boolean hasSeenOnLogin = loginSharedPreference.getBoolean("hasSeenLogin", false);

        SharedPreferences signupStat = getSharedPreferences("SignupStatus", Context.MODE_PRIVATE);
        boolean hasSeenSignup = signupStat.getBoolean("Signup", false);


        if (hasSeenOnBoarding && hasSeenOnLogin){
            Log.d("preference vaue of onboared and loign", "onCreate: "+hasSeenOnBoarding+" "+hasSeenOnLogin);
            swapFragments(new BaseDashboared());
        }else if(hasSeenOnBoarding && !hasSeenOnLogin){
            swapFragments(new Signup());
        }else if(hasSeenOnBoarding && !hasSeenOnLogin && hasSeenSignup){
            swapFragments(new Login());
        }else{
            swapFragments(new BaseFragment());
        }

    }

    public void swapFragments(Fragment fragment){
        Log.d("MainActivity", "Swapping fragments...");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        // Only add to back stack if it's not the dashboard or base fragment
        transaction.commit();
        Log.d("MainActivity", "Fragment swapped.");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        if (currentFragment instanceof BaseDashboared) {
            Fragment childFragment = ((BaseDashboared) currentFragment).getChildFragmentManager()
                    .findFragmentById(R.id.framedashboared);

            if (childFragment instanceof Home) {
                new androidx.appcompat.app.AlertDialog.Builder(this)
                        .setTitle("Exit App")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", (dialog, which) -> finish())
                        .setNegativeButton("No", null)
                        .show();
            } else {
//                ((BaseFrag) currentFragment).getChildFragmentManager().popBackStack(); // Pop the child fragment back stack
                bottomNavigation.setSelectedItemId(R.id.navigation_home);
            }
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack(); // Pop the main fragment back stack
            } else {
                super.onBackPressed();
            }
        }
    }
}