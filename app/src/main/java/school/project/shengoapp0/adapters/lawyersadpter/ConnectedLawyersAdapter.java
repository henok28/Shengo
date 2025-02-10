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
import school.project.shengoapp0.model.PendingConnecedLawyerModal;
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyerModal;
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyerResponseModal;

public class ConnectedLawyersAdapter extends RecyclerView.Adapter<ConnectedLawyersAdapter.ConnectedLawyerViewHolder>{
    private final Context context;
    private List<PendingConnecedLawyerModal> connectedLawyerResponseModals;

    public void setConnectedLawyerResponseModals(List<PendingConnecedLawyerModal> connectedLawyerResponseModals) {
        this.connectedLawyerResponseModals = connectedLawyerResponseModals;
    }

    public ConnectedLawyersAdapter(Context context, List<PendingConnecedLawyerModal> connectedLawyerResponseModals) {
        this.context = context;
        this.connectedLawyerResponseModals = connectedLawyerResponseModals;
    }

    @NonNull
    @Override
    public ConnectedLawyerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.connected_lawyers, parent, false);
        return new ConnectedLawyerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectedLawyerViewHolder holder, int position) {
        PendingConnecedLawyerModal connectedLawyers = connectedLawyerResponseModals.get(position);
        holder.bind(connectedLawyers, context);
    }

    @Override
    public int getItemCount() {
        return connectedLawyerResponseModals.size();
    }


    public static class ConnectedLawyerViewHolder extends RecyclerView.ViewHolder{
        private final TextView connectedLawyerName;
        private final ImageView connectedLawyerProfile;

        public ConnectedLawyerViewHolder(@NonNull View itemView) {
            super(itemView);
            connectedLawyerName = itemView.findViewById(R.id.connected_lawyer_name);
            connectedLawyerProfile = itemView.findViewById(R.id.connected_lawyer_profile);
        }

        public void bind(PendingConnecedLawyerModal connectedLawyerModal, Context context){
            connectedLawyerName.setText(connectedLawyerModal.getLawyer_profile().getFirst_name()+" "+connectedLawyerModal.getLawyer_profile().getMiddle_name());
            String baseurl = context.getString(R.string.base_url);
            String imageUrl = baseurl+"/storage/"+connectedLawyerModal.getLawyer_profile().getProfile_picture();

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Optional placeholder
                    .error(R.drawable.ic_launcher_background)       // Optional error image
                    .into(connectedLawyerProfile);
        }
    }
}
