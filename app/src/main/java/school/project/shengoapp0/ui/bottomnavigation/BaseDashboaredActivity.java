package school.project.shengoapp0.ui.bottomnavigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import school.project.shengoapp0.R;
import school.project.shengoapp0.databinding.ActivityBaseDashboaredBinding;

public class BaseDashboaredActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_base_dashboared);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        switchFragment(new Home());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.navigation_home) {
                    switchFragment(new Home());
                } else if (menuItem.getItemId() == R.id.navigation_lawyer) {
                    switchFragment(new FindLawyer());
                } else if (menuItem.getItemId() == R.id.navigation_resource) {
                    switchFragment(new Resources());
                } else if (menuItem.getItemId() == R.id.navigation_message) {
                    switchFragment(new Message());
                } else if (menuItem.getItemId() == R.id.navigation_settings) {
                    switchFragment(new Settings());
                }
                return true;
            }
        });


    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.baseDashboaredFrame);

        if (currentFragment instanceof Home) {
            // Show exit dialog when in Home
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Exit App")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", (dialog, which) -> finish())
                    .setNegativeButton("No", null)
                    .show();
        } else {
            // Go back to Home
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }


    public void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(R.id.baseDashboaredFrame, fragment)
                .commit();
    }


}