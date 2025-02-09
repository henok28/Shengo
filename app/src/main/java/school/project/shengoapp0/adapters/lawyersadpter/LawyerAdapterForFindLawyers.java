package school.project.shengoapp0.adapters.lawyersadpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import school.project.shengoapp0.R;
import school.project.shengoapp0.model.LawyerModal;

public class LawyerAdapterForFindLawyers extends RecyclerView.Adapter<LawyerAdapterForFindLawyers.LawyerViewHolder> {

    private final Context context;
    private List<LawyerModal> lawyerList;
    private final OnItemClickListener itemClickListener;


    // Listener interface to communicate clicks with fragment
    public interface OnItemClickListener {
        void onItemClick(LawyerModal lawyer, int position);
    }


    // Constructor
    public LawyerAdapterForFindLawyers(Context context, List<LawyerModal> lawyerList, OnItemClickListener itemClickListener) {
        this.context = context;
        this.lawyerList = lawyerList != null ? lawyerList : new ArrayList<>();
        this.itemClickListener = itemClickListener;
    }


    // Method to dynamically update the list
    public void setLawyers(List<LawyerModal> lawyers) {
        this.lawyerList = lawyers != null ? lawyers : new ArrayList<>();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public LawyerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lawyer, parent, false);
        return new LawyerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LawyerViewHolder holder, int position) {
        LawyerModal lawyer = lawyerList.get(position);

        holder.bind(lawyer, itemClickListener, position, this.context);
    }


    @Override
    public int getItemCount() {
        return lawyerList.size();
    }


    // ViewHolder Class
    public static class LawyerViewHolder extends RecyclerView.ViewHolder {
        //Remove the imageView because it is not being used any more.
        //private final ImageView lawyerImage;
        private final TextView lawyerName, lawyerSpeciality;
        private final ImageView profilePicture; // Add the profile picture here.


        public LawyerViewHolder(@NonNull View itemView) {
            super(itemView);
            // Remove the lawyerImage.
            //lawyerImage = itemView.findViewById(R.id.lawyerImage);
            lawyerName = itemView.findViewById(R.id.lawyerName);
            lawyerSpeciality = itemView.findViewById(R.id.lawyerSpecialty);
            profilePicture = itemView.findViewById(R.id.profilePicture); // Add this to grab profile picture.
        }


        public void bind(LawyerModal lawyer, OnItemClickListener listener, int position, Context context) {
            //Remove the view id for the arrow because it is not used any more.
            // lawyerImage.setImageResource(lawyer.getProfileImage());


            lawyerName.setText(lawyer.getName());
            lawyerSpeciality.setText(lawyer.getSpecialty());
            String baseurl = context.getString(R.string.base_url);
            String imageUrl = baseurl+"/storage/"+lawyer.getProfilePictureUrl();


            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Optional placeholder
                    .error(R.drawable.ic_launcher_background)       // Optional error image
                    .into(profilePicture);


            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    // Toast.makeText(v.getContext(), "You selected: " + lawyer.getName(), Toast.LENGTH_SHORT).show();
                    listener.onItemClick(lawyer, position);
                }
            });
        }
    }
}