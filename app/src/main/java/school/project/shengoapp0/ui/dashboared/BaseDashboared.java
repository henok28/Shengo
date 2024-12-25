package school.project.shengoapp0.ui.dashboared;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;


import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.adapters.dashboaredadapter.LawyerSliderAdapter;
import school.project.shengoapp0.model.LawyersData;
import school.project.shengoapp0.ui.autentication.Login;
import school.project.shengoapp0.ui.bottomnavigation.FindLawyer;
import school.project.shengoapp0.ui.bottomnavigation.Home;
import school.project.shengoapp0.ui.bottomnavigation.Message;
import school.project.shengoapp0.ui.bottomnavigation.Resources;
import school.project.shengoapp0.ui.bottomnavigation.Settings;
import school.project.shengoapp0.utilities.AuthStatUtil;

public class BaseDashboared extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_dashboared, container, false);
    }

    private FragmentManager fragmentManager;
    boolean isHomeLoaded = true;
    AuthStatUtil authStatUtil;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authStatUtil = new AuthStatUtil(requireContext());
        boolean isUserSubscribed = authStatUtil.getSubscriptionStatus();
        if (isHomeLoaded) {
            switchFragment(new Home());
        }

        BottomNavigationView bottom_navigation = view.findViewById(R.id.bottom_navigation);
        if (bottom_navigation == null) {
            return;
        }

        if (!isUserSubscribed){
            bottom_navigation.getMenu().removeItem(R.id.navigation_message);
        }
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                if (menuItem.getItemId() == R.id.navigation_home) {
                    isHomeLoaded = false;
                    fragment = new Home();
                } else if (menuItem.getItemId() == R.id.navigation_lawyer) {
                    isHomeLoaded = false;
                    fragment = new FindLawyer();
                } else if (menuItem.getItemId() == R.id.navigation_resource) {
                    isHomeLoaded = false;
                    fragment = new Resources();
                } else if (menuItem.getItemId() == R.id.navigation_message) {
                    isHomeLoaded = false;
                    fragment = new Message();
                } else if (menuItem.getItemId() == R.id.navigation_settings) {
                    isHomeLoaded = false;
                    fragment = new Settings();
                }

                if (fragment != null) {
                    switchFragment(fragment);
                }
                return true;
            }
        });

    }


    public void switchFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.framedashboared, fragment)
                .addToBackStack(null)
                .commit();
    }

}