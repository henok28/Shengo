package school.project.shengoapp0;

import android.app.ActionBar;
import android.content.Context;
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

import school.project.shengoapp0.ui.autentication.Login;
import school.project.shengoapp0.ui.autentication.Signup;
import school.project.shengoapp0.ui.dashboared.BaseDashboared;
import school.project.shengoapp0.ui.onboaredpage.BaseFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(ContextCompat.getColor(this, R.color.ivory_cream));

        View decor = window.getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        getWindow().setStatusBarColor(getResources().getColor(R.color.status_color));
        /*
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        */

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
        transaction.commit();
        Log.d("MainActivity", "Fragment swapped.");
    }
}