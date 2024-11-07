package school.project.shengoapp0.ui.onboaredpage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.adapters.onboardingadapter.OnboardingPagerAdapter;
import school.project.shengoapp0.ui.autentication.Signup;
import school.project.shengoapp0.ui.dashboared.BaseDashboared;

public class BaseFragment extends Fragment {
    private ViewPager2 viewPager;
    private LinearLayout dotIndicator;

    private TextView[] dots;

    Button nextBtn, skipBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dotIndicator = view.findViewById(R.id.indicator_layout);

        viewPager = view.findViewById(R.id.viewPager);

        // Set the adapter for the ViewPager
        OnboardingPagerAdapter adapter = new OnboardingPagerAdapter(this);
        viewPager.setAdapter(adapter);

        //here after i add the adapter it takes a bit time to inflate the views in the viewpager so that i delayed the indicator
        //so that the indicator works on the first page
        viewPager.post(() -> {
            setUpIndicator(0);
            viewPager.setCurrentItem(0, false);
        });

        nextBtn = view.findViewById(R.id.nextbutton);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if (currentItem < adapter.getItemCount()-1){
                    viewPager.setCurrentItem(currentItem+1, false);
                }else {
                    ((MainActivity) requireActivity()).swapFragments(new BaseDashboared());
                    SharedPreferences onBoardingPreferences= requireActivity().getSharedPreferences("myAppOnboardState", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor  = onBoardingPreferences.edit();
                    editor.putBoolean("hasSeenOnboarding", true);
                    editor.apply();
                }
            }
        });

        skipBtn = view.findViewById(R.id.skipbutton);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).swapFragments(new Signup());
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setUpIndicator(position);
            }
        });


    }

    public void setUpIndicator(int position){
        dots = new TextView[4];
        dotIndicator.removeAllViews();

        for(int i = 0;i<dots.length;i++){
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.black, requireContext().getTheme()));
            dotIndicator.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.white, requireContext().getTheme()));
        }


    }
}