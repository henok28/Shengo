package school.project.shengoapp0.ui.bottomnavigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Locale;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.adapters.dashboaredadapter.LawyerSliderAdapter;
import school.project.shengoapp0.model.LawyersData;
import school.project.shengoapp0.ui.autentication.Login;

public class Home extends Fragment {
    RecyclerView recyclerView;
    List<LawyersData> lawyersDataList= new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
   public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView iv_sidebar = view.findViewById(R.id.sideNav);
        iv_sidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).swapFragments(new SideBar());
            }
        });

//        Button logout = view.findViewById(R.id.logout);
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences loginSharedPreference = requireActivity().getSharedPreferences("myAppLoginStatus", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = loginSharedPreference.edit();
//                editor.putBoolean("hasSeenLogin", false);
//                editor.apply();
//
//                SharedPreferences signupStat = requireActivity().getSharedPreferences("SignupStatus", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor1 = signupStat.edit();
//                editor1.putBoolean("Signup", true);
//                editor1.apply();
//
//
//
//                Toast.makeText(requireActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();
//                ((MainActivity)requireActivity()).swapFragments(new Login());
//            }
//        });


        loadLocale(getContext());
        recyclerView = view.findViewById(R.id.lawyersrecycler);
        LawyersData l1 = new LawyersData("Abel\nMulugeta","60", R.drawable.meronaaaaa);
        LawyersData l2 = new LawyersData("Meron\nAlemayehu","23", R.drawable.meronaaaaa);
        LawyersData l3 = new LawyersData("Fitsum\nHailemariam","76", R.drawable.meronaaaaa);
        LawyersData l4 = new LawyersData("Selam\nMulugeta","88", R.drawable.meronaaaaa);
        LawyersData l5 = new LawyersData("Kebede\nAsfaw","100", R.drawable.meronaaaaa);
        lawyersDataList.add(l1);
        lawyersDataList.add(l2);
        lawyersDataList.add(l3);
        lawyersDataList.add(l4);
        lawyersDataList.add(l5);
        LawyerSliderAdapter adapter = new LawyerSliderAdapter(lawyersDataList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);




//        BottomNavigationView bottom_navigation = view.findViewById(R.id.bottom_navigation);
//        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                if (menuItem.getItemId() == R.id.navigation_home) {
//                    switchFragment(new Home());
//                    return true;
//                } else if (menuItem.getItemId() == R.id.navigation_lawyer) {
//                    switchFragment(new FindLawyer());
//                    return true;
//                } else if (menuItem.getItemId() == R.id.navigation_resource) {
//                    switchFragment(new Resources()); // Replace with your actual fragment
//                    return true;
//                } else if (menuItem.getItemId() == R.id.navigation_message) {
//                    switchFragment(new Message()); // Replace with your actual fragment
//                    return true;
//                }else if (menuItem.getItemId() == R.id.settings) {
//                    switchFragment(new Message()); // Replace with your actual fragment
//                    return true;
//                }
//                return false;
//            }
//
//        });
    }

    public static void loadLocale(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en"); // Default to English if no preference
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framedashboared, fragment);
        fragmentTransaction.commit();
    }
}