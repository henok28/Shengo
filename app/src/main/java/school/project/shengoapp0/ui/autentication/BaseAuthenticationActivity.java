package school.project.shengoapp0.ui.autentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import school.project.shengoapp0.R;
import school.project.shengoapp0.ui.bottomnavigation.BaseDashboaredActivity;

public class BaseAuthenticationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);

        if (savedInstanceState == null){
            switchFragment(new Login());
        }
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(R.id.authFrame, fragment)
                .commit();
    }

    public void navigateToDashboared(){
        Intent intent = new Intent(this, BaseDashboaredActivity.class);
        startActivity(intent);
    }
}