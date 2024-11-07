package school.project.shengoapp0.adapters.onboardingadapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import school.project.shengoapp0.R;
import school.project.shengoapp0.ui.onboaredpage.BaseFragment;
import school.project.shengoapp0.ui.onboaredpage.DataFragment;

public class OnboardingPagerAdapter extends FragmentStateAdapter {
    BaseFragment baseFragment;

    private final int[] images = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
    };

    private final int[] headings = {
            R.string.header1,
            R.string.header2,
            R.string.header3,
            R.string.header4
    };

    private final int[] descriptions = {
            R.string.des1,
            R.string.des2,
            R.string.des3,
            R.string.des4
    };


    public OnboardingPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return DataFragment.newInstance(
                images[position],
                headings[position],
                descriptions[position]
        );

    }

    @Override
    public int getItemCount() {
        return headings.length;
    }



}
