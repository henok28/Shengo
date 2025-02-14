package school.project.shengoapp0;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

import school.project.shengoapp0.ui.autentication.Login;
import school.project.shengoapp0.ui.autentication.Signup;
import school.project.shengoapp0.ui.bottomnavigation.Home;
import school.project.shengoapp0.ui.bottomnavigation.Settings;
import school.project.shengoapp0.ui.dashboared.BaseDashboared;
import school.project.shengoapp0.ui.onboaredpage.BaseFragment;
import school.project.shengoapp0.ui.others.SuccessSubscriptionPage;
import school.project.shengoapp0.ui.others.VerificationState;
import school.project.shengoapp0.utilities.AuthStatUtil;
import school.project.shengoapp0.utilities.FormUtils;
import school.project.shengoapp0.utilities.LocaleHelper;
import school.project.shengoapp0.utilities.TokenUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigation;
    private TokenUtil tokenUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        applyLanguage();
        setContentView(R.layout.activity_main);

        setupWindow();


        tokenUtil = new TokenUtil(this);
        navigateToAppropriateScreen();
//        mimicDataBaseFormResponse("APPROVED");
        FormUtils formUtils = new FormUtils(this);
        formUtils.clear();
        formUtils.setFormState("PENDING");
        Log.d("value of form state", "onCreate: " + formUtils.getFormState());


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check token validity when returning to the app
        if (!tokenUtil.isTokenValid()) {
            Log.d(TAG, "Token invalid on resume, navigating to appropriate screen");
            navigateToAppropriateScreen();
        }
    }
    private void applyLanguage() {
        SharedPreferences preferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String language = preferences.getString("selectedLanguage", "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration config = new Configuration(getResources().getConfiguration());
        config.setLocale(locale);

        Context context = createConfigurationContext(config);

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void mimicDataBaseFormResponse(String statusFromDb) {
        FormUtils formUtils = new FormUtils(this);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            formUtils.setFormState(statusFromDb);
        }, 1 * 60 * 1000);


    }


    private void navigateToAppropriateScreen() {
        SharedPreferences onBoardingPrefs = getSharedPreferences("myAppOnboardState", MODE_PRIVATE);
        boolean hasSeenOnBoarding = onBoardingPrefs.getBoolean("hasSeenOnboarding", false);

        // If user hasn't seen onboarding, show it first
        if (!hasSeenOnBoarding) {
            Log.d(TAG, "Showing onboarding");
            swapFragments(new BaseFragment());
            return;
        }

        // Check if we have a valid token
        if (tokenUtil.isTokenValid()) {
            Log.d(TAG, "Valid token found, showing dashboard");
            swapFragments(new BaseDashboared());
            return;
        }

        // No valid token, show login flow
        SharedPreferences signupPrefs = getSharedPreferences("SignupStatus", Context.MODE_PRIVATE);
        boolean hasSignedUp = signupPrefs.getBoolean("Signup", false);

        if (hasSignedUp) {
            Log.d(TAG, "User has signed up, showing login");
            swapFragments(new Login());
        } else {
            Log.d(TAG, "New user, showing signup");
            swapFragments(new Signup());
        }
    }


    public void swapFragments(Fragment fragment) {
        Log.d("MainActivity", "Swapping fragments...");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        // Only add to back stack if it's not the dashboard or base fragment
        transaction.commit();
        Log.d("MainActivity", "Fragment swapped.");
    }

    public void swapFragment(Fragment fragment) {
        Log.d("MainActivity", "Swapping fragments...");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);

        // Only add to back stack if it's not the dashboard or base fragment
        transaction.commit();
        Log.d("MainActivity", "Fragment swapped.");
    }

    public void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences("FormStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("hasSubmittedForm", false);
        editor.apply();
        SharedPreferences sharedPreferences2 = getSharedPreferences("SubscriptionStatus", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor2.putBoolean("hasSubscribed", false);
        editor2.commit();
        school.project.shengoapp0.utilities.TokenUtil tokenUtil = new school.project.shengoapp0.utilities.TokenUtil(this);
        tokenUtil.clearToken();
        swapFragments(new Login());
    }

    public void swapFragments(Fragment fragment, String tag) {
        Log.d("MainActivity", "Swapping fragments...");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment, tag);
        transaction.addToBackStack(tag);
        // Only add to back stack if it's not the dashboard or base fragment
        transaction.commit();
        Log.d("MainActivity", "Fragment swapped.");
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AuthStatUtil authStatUtil = new AuthStatUtil(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        SharedPreferences sharedPreferences = getSharedPreferences("FormStatus", Context.MODE_PRIVATE);
        boolean hasSubmittedForm = sharedPreferences.getBoolean("hasSubmittedForm", false);

        SharedPreferences sharedPreferences1 = getSharedPreferences("FormFragPop", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();


        boolean hasBeenRemovedFromStack = sharedPreferences1.getBoolean("hasBeenRemovedFromStack", false);
//        Log.d("after removed stack verification", " " + hasBeenRemovedFromStack+":  "+hasSubmittedForm+"::"+authStatUtil.getVerificationStatus());
        Log.d("auth_value_&_form", ": "+authStatUtil.getVerificationStatusString().equals("unverified")+": "+hasSubmittedForm);

        Log.d("removing stack 01", ": "+hasBeenRemovedFromStack);
        if (authStatUtil.getVerificationStatusString().equals("unverified") && hasSubmittedForm == false){
            hasBeenRemovedFromStack = false;
        }
        Log.d("removing stack 02", ": "+hasBeenRemovedFromStack);

        if (hasBeenRemovedFromStack == false) {
            if (currentFragment instanceof VerificationState && (hasSubmittedForm||authStatUtil.getVerificationStatus())) {
//                fragmentManager.popBackStack("VerificationForm", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                editor.putBoolean("hasBeenRemovedFromStack  ", true);
//                editor.commit();
                swapFragment(new BaseDashboared());
                return;
            }
        }


        SharedPreferences sharedPreferences2 = getSharedPreferences("SubscriptionStatus", Context.MODE_PRIVATE);
        boolean hasSubscribed = sharedPreferences2.getBoolean("hasSubscribed", false);
        boolean hasBeenRemovedFromStackSubscription = sharedPreferences1.getBoolean("hasBeenRemovedFromStackSubscription", false);
        Log.d("subscriptionStats01", ": "+hasBeenRemovedFromStackSubscription);
        if (!authStatUtil.getSubscriptionStatus()){
            hasBeenRemovedFromStackSubscription = false;
        }
        Log.d("subscriptionStats02", ": "+hasBeenRemovedFromStackSubscription);

        if (hasBeenRemovedFromStackSubscription == false){
            if (currentFragment instanceof SuccessSubscriptionPage && (hasSubscribed || authStatUtil.getSubscriptionStatus())) {
//                fragmentManager.popBackStack("Subscription", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                fragmentManager.popBackStack("VerificationState", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                editor.putBoolean("hasBeenRemovedFromStackSubscription", true);
//                editor.commit();
                swapFragment(new BaseDashboared());

                Log.d("in", " " + hasBeenRemovedFromStackSubscription);
                return;

            }
        }


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
        } else if (currentFragment instanceof Login
                || currentFragment instanceof Signup
                || currentFragment instanceof BaseFragment) {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Exit App")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", (dialog, which) -> finish())
                    .setNegativeButton("No", null)
                    .show();

        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack(); // Pop the main fragment back stack
            } else {
                super.onBackPressed();
            }
        }
    }

    private void setupWindow() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        View decor = window.getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }
}