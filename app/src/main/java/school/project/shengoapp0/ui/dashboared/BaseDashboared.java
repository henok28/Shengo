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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        if (isHomeLoaded){
            switchFragment(new Home());
        }

        BottomNavigationView bottom_navigation = view.findViewById(R.id.bottom_navigation);;
        if (bottom_navigation == null){return;}
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

//        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                if (isAdded()){
//                    Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.framedashboared);
//                    if (currentFragment != null){
//                        syncBottomNavWithFragment(currentFragment);
//                    }
//                }
//            }
//        });

    }

//    private void syncBottomNavWithFragment(Fragment fragment) {
//        try {
//            int selectedItemId = R.id.navigation_home;
//
//            if (fragment instanceof Home) {
//                selectedItemId = R.id.navigation_home;
//            } else if (fragment instanceof FindLawyer) {
//                selectedItemId = R.id.navigation_lawyer;
//            } else if (fragment instanceof Resources) {
//                selectedItemId = R.id.navigation_resource;
//            } else if (fragment instanceof Message) {
//                selectedItemId = R.id.navigation_message;
//            } else if (fragment instanceof Settings) {
//                selectedItemId = R.id.navigation_settings;
//            }
//
//            if (bottom_navigation.getSelectedItemId() != selectedItemId) {
//                bottom_navigation.setSelectedItemId(selectedItemId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void clearBackStack(){
        if (fragmentManager!=null){
            for (int i = 0; i<fragmentManager.getBackStackEntryCount();i++){
                fragmentManager.popBackStackImmediate();
            }
        }
    }


//    public void switchFragment(Fragment fragment, boolean addToBackStack) {
//        try {
//            if (!isAdded() && fragmentManager!=null){
//                FragmentManager fragmentManager = getChildFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.framedashboared, fragment);
//                if (addToBackStack){
//                    fragmentTransaction.addToBackStack(null);
//                }
//                fragmentTransaction.commit();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


    public void switchFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.framedashboared, fragment)
                .addToBackStack(null)
                .commit();
    }

//    public void handleBackPress() {
//        FragmentManager fm = getChildFragmentManager();
//        if (fm.getBackStackEntryCount() > 0) {
//            fm.popBackStack();
//            // Update bottom nav to show Home
//            bottom_navigation.setSelectedItemId(R.id.navigation_home);
//        }
//    }

}