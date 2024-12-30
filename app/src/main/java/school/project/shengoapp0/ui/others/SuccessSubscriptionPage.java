package school.project.shengoapp0.ui.others;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import school.project.shengoapp0.R;

public class SuccessSubscriptionPage extends Fragment {
    public SuccessSubscriptionPage() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success_subscription_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences1 = getContext().getSharedPreferences("SeenPage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences1.edit();
        editor.putBoolean("seen", true);
        editor.apply();


    }
}