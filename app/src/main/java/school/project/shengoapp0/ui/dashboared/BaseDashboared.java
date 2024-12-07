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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //i created this so that for the first time when the base gets populated to the
        //main the Home fragment would be displayed
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framedashboared, new Home());
        fragmentTransaction.commit();


//        SlidingPaneLayout slidingPaneLayout = view.findViewById(R.id.sliding_pane_layout);
//        ImageView profilePic = view.findViewById(R.id.profile_pic);
//        BottomNavigationView bottom_navigation = view.findViewById(R.id.bottom_navigation);


//        profilePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (slidingPaneLayout.isOpen()) {
//                    slidingPaneLayout.closePane();
//                } else {
//                    slidingPaneLayout.openPane();
//                }
//            }
//        });



        BottomNavigationView bottom_navigation = view.findViewById(R.id.bottom_navigation);

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.navigation_home) {
                    switchFragment(new Home());
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_lawyer) {
                    switchFragment(new FindLawyer());
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_resource) {
                    switchFragment(new Resources());
                    return true;
                } else if (menuItem.getItemId() == R.id.navigation_message) {
                    switchFragment(new Message());
                    return true;
                }else if (menuItem.getItemId() == R.id.settings) {
                    switchFragment(new Settings());
                    return true;
                }
                return false;
            }

        });


//        recyclerView = view.findViewById(R.id.lawyersrecycler);
//        LawyersData l1 = new LawyersData("Abel\nMulugeta","60", R.drawable.meronaaaaa);
//        LawyersData l2 = new LawyersData("Meron\nAlemayehu","23", R.drawable.meronaaaaa);
//        LawyersData l3 = new LawyersData("Fitsum\nHailemariam","76", R.drawable.meronaaaaa);
//        LawyersData l4 = new LawyersData("Selam\nMulugeta","88", R.drawable.meronaaaaa);
//        LawyersData l5 = new LawyersData("Kebede\nAsfaw","100", R.drawable.meronaaaaa);
//        lawyersDataList.add(l1);
//        lawyersDataList.add(l2);
//        lawyersDataList.add(l3);
//        lawyersDataList.add(l4);
//        lawyersDataList.add(l5);
//        LawyerSliderAdapter adapter = new LawyerSliderAdapter(lawyersDataList);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//        recyclerView.setAdapter(adapter);


    }


    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framedashboared, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}