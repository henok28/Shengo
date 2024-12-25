package school.project.shengoapp0.ui.others;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import school.project.shengoapp0.MainActivity;
import school.project.shengoapp0.R;
import school.project.shengoapp0.utilities.AuthStatUtil;
import school.project.shengoapp0.viewmodels.SubscriptionViewModel;
import school.project.shengoapp0.viewmodels.VerificationFromViewModel;


public class Subscription extends Fragment {
    LinearLayout month, semiAnnual, annual;
    ImageView monthlyIV, semiAnnualIV, annualIV;
    Button subscribeBtn;
    String plan;
    int planPrice;
    SubscriptionViewModel subscriptionViewModel;
    private AuthStatUtil authStatUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscription, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscriptionViewModel = new ViewModelProvider(this).get(SubscriptionViewModel.class);

        subscriptionViewModel.checkSubscriptoinStatus();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SubscriptionStatus", Context.MODE_PRIVATE);

        boolean subscribed = sharedPreferences.getBoolean("hasSubscribed", false);
        subscriptionViewModel.getSubscriptionStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Log.d("subscription form sub", "onChanged: " + "Subsc");
                    ((MainActivity) requireActivity()).swapFragments(new SuccessSubscriptionPage());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("hasSubscribed", true);
                    editor.commit();
                }
            }
        });


    initViews(view);

    resetSelection();
        month.setOnClickListener(v ->

    {
        resetSelection();
        monthlyIV.setVisibility(View.VISIBLE);
        monthlyIV.setImageResource(R.drawable.checked);
        plan = "1month";
        planPrice = 1000;
    });

        semiAnnual.setOnClickListener(v ->

    {
        resetSelection();
        semiAnnualIV.setVisibility(View.VISIBLE);
        semiAnnualIV.setImageResource(R.drawable.checked);
        plan = "6month";
        planPrice = 2000;
    });

        annual.setOnClickListener(v ->

    {
        resetSelection();
        annualIV.setVisibility(View.VISIBLE);
        annualIV.setImageResource(R.drawable.checked);
        plan = "12month";
        planPrice = 3000;
    });


    subscribeBtn =view.findViewById(R.id.btn_subscribe);

        subscribeBtn.setOnClickListener(new View.OnClickListener()

    {

        @Override
        public void onClick (View v){

        submitForm();
    }
    });


}


    public void submitForm() {
        Log.d("value of plan", ": " + plan);
        Log.d("value of plan", ": " + planPrice);
        subscriptionViewModel = new ViewModelProvider(this).get(SubscriptionViewModel.class);
        subscriptionViewModel.subscribe(planPrice, plan);
        setUpObserver();

    }

    public void setUpSubscriptionStatusObserber() {

    }

    public void setUpObserver() {
        subscriptionViewModel.getMutableLink().observe(getViewLifecycleOwner(), url -> {
            if (url != null && !url.trim().isEmpty()) {
                try {
                    // Trim whitespace and validate URL format
                    String sanitizedUrl = url.trim();
                    if (!sanitizedUrl.startsWith("http://") && !sanitizedUrl.startsWith("https://")) {
                        sanitizedUrl = "https://" + sanitizedUrl; // Add HTTPS prefix if missing
                    }

                    Log.d("Checkout URL", sanitizedUrl);

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sanitizedUrl));
                    browserIntent.setPackage("com.android.chrome"); // Target Chrome browser
                    try {
                        startActivity(browserIntent);
                        authStatUtil.setSubscriptionStatus("subscribed");
                        Log.d("has subscribed", "setUpObserver: " + authStatUtil.getSubscriptionStatus());


                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), "Chrome is not installed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Invalid URL: " + url, Toast.LENGTH_SHORT).show();
                    Log.e("URL Error", "Error opening URL", e);
                }
            } else {
                Toast.makeText(getContext(), "Error: Invalid URL received", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initViews(View view) {
        month = view.findViewById(R.id.month);
        semiAnnual = view.findViewById(R.id.semiannual);
        annual = view.findViewById(R.id.annual);

        monthlyIV = view.findViewById(R.id.iv_monthly);
        semiAnnualIV = view.findViewById(R.id.iv_semi_annual);
        annualIV = view.findViewById(R.id.iv_annual);
    }

    private void resetSelection() {
        monthlyIV.setVisibility(View.INVISIBLE);
        semiAnnualIV.setVisibility(View.INVISIBLE);
        annualIV.setVisibility(View.INVISIBLE);
    }

}