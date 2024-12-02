package school.project.shengoapp0.ui.autentication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import school.project.shengoapp0.R;

public class VerificationForm extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context context = new ContextThemeWrapper(getActivity(), R.style.CustomTextTheme);
        LayoutInflater localInflater = inflater.cloneInContext(context);
        return inflater.inflate(R.layout.fragment_verification_form, container, false);
    }
}