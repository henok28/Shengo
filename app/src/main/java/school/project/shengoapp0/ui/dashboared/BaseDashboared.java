package school.project.shengoapp0.ui.dashboared;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import school.project.shengoapp0.R;
import school.project.shengoapp0.adapters.dashboaredadapter.LawyerSliderAdapter;
import school.project.shengoapp0.model.LawyersData;

public class BaseDashboared extends Fragment {
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
        return inflater.inflate(R.layout.fragment_base_dashboared, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.lawyersrecycler);
        LawyersData l1 = new LawyersData("Abel\nMulugeta","60", R.drawable.trialcolor);
        LawyersData l2 = new LawyersData("Meron\nAlemayehu","23", R.drawable.trialcolor);
        LawyersData l3 = new LawyersData("Fitsum\nHailemariam","76", R.drawable.firstcream);
        LawyersData l4 = new LawyersData("Selam\nMulugeta","88", R.drawable.firstcream);
        LawyersData l5 = new LawyersData("Kebede\nAsfaw","100", R.drawable.firstcream);
        lawyersDataList.add(l1);
        lawyersDataList.add(l2);
        lawyersDataList.add(l3);
        lawyersDataList.add(l4);
        lawyersDataList.add(l5);
        LawyerSliderAdapter adapter = new LawyerSliderAdapter(lawyersDataList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }
}