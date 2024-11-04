package school.project.shengoapp0.ui.onboaredpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import school.project.shengoapp0.R;


public class DataFragment extends Fragment {
    private static final String ARG_IMAGE = "image";
    private static final String ARG_HEADING = "heading";
    private static final String ARG_DESCRIPTION = "description";
    TextView textViewTitle, textViewDescription;
    ImageView imageView;



    public static DataFragment newInstance(int imageResId, int headingResId, int descriptionResId) {
        DataFragment fragment = new DataFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE, imageResId);
        args.putInt(ARG_HEADING, headingResId);
        args.putInt(ARG_DESCRIPTION, descriptionResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewDescription = view.findViewById(R.id.textViewDescription);
        imageView = view.findViewById(R.id.imageView);

        Bundle args = getArguments();
       if (getArguments() != null) {
           imageView.setImageResource(getArguments().getInt(ARG_IMAGE));
           textViewTitle.setText(getArguments().getInt(ARG_HEADING));
           textViewDescription.setText(getArguments().getInt(ARG_DESCRIPTION));
        }

    }
}