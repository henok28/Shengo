package school.project.shengoapp0.adapters.lawyersadpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import school.project.shengoapp0.R;
import school.project.shengoapp0.model.LawyerToClientRequestModal;
import school.project.shengoapp0.model.PendingConnecedLawyerModal;

public class PendingLawyersAdapter extends RecyclerView.Adapter<PendingLawyersAdapter.PendingLawyerViewHolder> {


    private final Context context;
    private List<PendingConnecedLawyerModal> pendingLawyers;

    public void setPendingLawyers(List<PendingConnecedLawyerModal> pendingLawyers) {
        this.pendingLawyers = pendingLawyers;
    }

    public PendingLawyersAdapter(Context context, List<PendingConnecedLawyerModal> pendingLawyers) {
        this.context = context;
        this.pendingLawyers = pendingLawyers;
    }

    @NonNull
    @Override
    public PendingLawyerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_lawyer, parent, false);
        return new PendingLawyerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingLawyerViewHolder holder, int position) {
        PendingConnecedLawyerModal pendingLawyer = pendingLawyers.get(position);
        holder.bind(pendingLawyer, context);
    }

    @Override
    public int getItemCount() {
        return pendingLawyers.size();
    }


    public static class PendingLawyerViewHolder extends RecyclerView.ViewHolder {
        private final TextView pendingLawyerName;
        private final ImageView pendingLawyerProfile;


        public PendingLawyerViewHolder(@NonNull View itemView) {
            super(itemView);
            pendingLawyerName = itemView.findViewById(R.id.pending_lawyer_name);
            pendingLawyerProfile = itemView.findViewById(R.id.pending_lawyer_profilePicture);

        }

        public void bind(PendingConnecedLawyerModal pendingLawyer, Context context) {
            pendingLawyerName.setText(pendingLawyer.getLawyer_profile().getFirst_name() + " "+pendingLawyer.getLawyer_profile().getMiddle_name());
            String baseurl = context.getString(R.string.base_url);
            String imageUrl = baseurl + "/storage/" + pendingLawyer.getLawyer_profile().getProfile_picture();

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Optional placeholder
                    .error(R.drawable.ic_launcher_background)       // Optional error image
                    .into(pendingLawyerProfile);
        }

    }
}