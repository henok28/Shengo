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
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyerModal;
import school.project.shengoapp0.model.connectedlawyersmodal.ConnectedLawyerResponseModal;

public class ConnectedLawyersAdapter extends RecyclerView.Adapter<ConnectedLawyersAdapter.ConnectedLawyerViewHolder>{
    private final Context context;
    private List<ConnectedLawyerResponseModal> connectedLawyerResponseModals;

    public void setConnectedLawyerResponseModals(List<ConnectedLawyerResponseModal> connectedLawyerResponseModals) {
        this.connectedLawyerResponseModals = connectedLawyerResponseModals;
    }

    public ConnectedLawyersAdapter(Context context, List<ConnectedLawyerResponseModal> connectedLawyerResponseModals) {
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
        ConnectedLawyerResponseModal connectedLawyers = connectedLawyerResponseModals.get(position);
        //holder
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

        public void bind(ConnectedLawyerModal connectedLawyerModal, int position, Context context){
            connectedLawyerName.setText(connectedLawyerModal.getClient_profile().getFirst_name()+" "+connectedLawyerModal.getClient_profile().getMiddle_name());
            String baseurl = context.getString(R.string.base_url);
            String imageUrl = baseurl+"/storage/"+connectedLawyerModal.getClient_profile().getProfile_picture();

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Optional placeholder
                    .error(R.drawable.ic_launcher_background)       // Optional error image
                    .into(connectedLawyerProfile);
        }
    }
}
