package school.project.shengoapp0.adapters.onboardingadapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import school.project.shengoapp0.R;
import school.project.shengoapp0.ui.onboaredpage.DataFragment;

public class OnboardingPagerAdapter extends FragmentStateAdapter {

    private final int[] images = {
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground
    };

    private final int[] headings = {
            R.string.app_name,
            R.string.hello_blank_fragment,
            R.string.app_name,
            R.string.hello_blank_fragment
    };

    private final int[] descriptions = {
            R.string.app_name,
            R.string.hello_blank_fragment,
            R.string.app_name,
            R.string.hello_blank_fragment
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
