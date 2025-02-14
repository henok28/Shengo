package school.project.shengoapp0.adapters.notification;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import school.project.shengoapp0.R;
import school.project.shengoapp0.model.LawyerToClientRequestModal;
import school.project.shengoapp0.model.NotificatoinResponseModal;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private final Context context;
    List<NotificatoinResponseModal> notificatoinResponseModals;

    public void setNotificatoinResponseModals(List<NotificatoinResponseModal> notificatoinResponseModals) {
        this.notificatoinResponseModals = notificatoinResponseModals;
    }

    public NotificationAdapter(Context context, List<NotificatoinResponseModal> notificatoinResponseModals) {
        this.context = context;
        this.notificatoinResponseModals = notificatoinResponseModals;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notificatoin_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificatoinResponseModal notificatoin = notificatoinResponseModals.get(position);
        holder.bind(notificatoin, context);;

    }

    @Override
    public int getItemCount() {
        return notificatoinResponseModals.size();
    }


    public static class NotificationViewHolder extends RecyclerView.ViewHolder{
        private final TextView notificationMsg;
        private final TextView date;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationMsg = itemView.findViewById(R.id.notification_message);
            date = itemView.findViewById(R.id.notification_date);
        }


        public void bind(NotificatoinResponseModal notification, Context context){
            notificationMsg.setText(notification.getMessage());
//            LocalDate currentDate = LocalDate.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // e.g., 2024-02-29
//            String formattedDate = currentDate.format(formatter);

        }
    }
}
