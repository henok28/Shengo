package school.project.shengoapp0.adapters.dashboaredadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import school.project.shengoapp0.R;
import school.project.shengoapp0.model.LawyersData;

public class LawyerSliderAdapter extends RecyclerView.Adapter<LawyerSliderAdapter.MyViewHolder>{


    private List<LawyersData> lawyersDataList;
    public LawyerSliderAdapter(List<LawyersData> lawyersDataList) {
        this.lawyersDataList = lawyersDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.lawyers_slider_card,
                parent,
                false
        );
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LawyersData lawyersData = lawyersDataList.get(position);
        holder.lawyerName.setText(lawyersData.getFullName());
        holder.caseWon.setText(lawyersData.getCaseWon());
        holder.lawyerImage.setImageResource(lawyersData.getLawyerImg());

    }

    @Override
    public int getItemCount() {
        return lawyersDataList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView lawyerName;
        TextView caseWon;
        ImageView lawyerImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            lawyerName = itemView.findViewById(R.id.lawyername);
            caseWon = itemView.findViewById(R.id.casewon);
            lawyerImage = itemView.findViewById(R.id.lawyerimage);
        }
    }
}
