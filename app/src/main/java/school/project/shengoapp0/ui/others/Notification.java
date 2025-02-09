package school.project.shengoapp0.ui.others;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import school.project.shengoapp0.R;

public class Notification extends AppCompatActivity {

    private ListView notificationsListView;
    private List<NotificationData> notificationDataList;
    private NotificationAdapter notificationAdapter;

    private static final String CHANNEL_ID = "my_channel_id";
    private static final AtomicInteger notificationIdCounter = new AtomicInteger(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification);

        notificationsListView = findViewById(R.id.notifications_list);
        notificationDataList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(this, notificationDataList);
        notificationsListView.setAdapter(notificationAdapter);

        createNotificationChannel();

        // Sample notifications
        addNotification("Gabriel Cruz clapped for Thanks Gabriel", "Jan 12, 2024", NotificationType.INFO);
        addNotification("Your request for verification is under review.", "Mar 12, 2024", NotificationType.SUCCESS);
        addNotification("Your subscription is about to expire. Please renew.", "May 12, 2024", NotificationType.WARNING);
        addNotification("Get 50% off on your next purchase. Limited time!", "June 1, 2024", NotificationType.ALERT);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "Channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification(String message, String date, NotificationType type) {
        int notificationId = generateNotificationId();
        notificationDataList.add(new NotificationData(message, date, type, notificationId));
        notificationAdapter.notifyDataSetChanged();
        showSystemNotification(message, notificationId);
    }

    private void showSystemNotification(String message, int notificationId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("New Notification")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }

    private int generateNotificationId() {
        return notificationIdCounter.incrementAndGet();
    }

    // Custom Adapter
    private class NotificationAdapter extends ArrayAdapter<NotificationData> {
        public NotificationAdapter(Context context, List<NotificationData> notifications) {
            super(context, 0, notifications);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notification, parent, false);
            }

            NotificationData notification = getItem(position);
            ImageView icon = convertView.findViewById(R.id.notification_icon);
            TextView message = convertView.findViewById(R.id.notification_message);
            TextView date = convertView.findViewById(R.id.notification_date);

            message.setText(notification.getMessage());
            date.setText(notification.getDate());

            // Set icon based on type
            switch (notification.getType()) {
                case INFO:
                    icon.setImageResource(R.drawable.ic_info);
                    break;
                case SUCCESS:
                    icon.setImageResource(R.drawable.ic_check);
                    break;
                case WARNING:
                    icon.setImageResource(R.drawable.ic_warning);
                    break;
                case ALERT:
                    icon.setImageResource(R.drawable.ic_alert);
                    break;
            }

            return convertView;
        }
    }

    // Enum for notification types
    private enum NotificationType {
        INFO, SUCCESS, WARNING, ALERT
    }

    // Notification Data Model
    private class NotificationData {
        private String message;
        private String date;
        private NotificationType type;
        private int notificationId;

        public NotificationData(String message, String date, NotificationType type, int notificationId) {
            this.message = message;
            this.date = date;
            this.type = type;
            this.notificationId = notificationId;
        }

        public String getMessage() { return message; }
        public String getDate() { return date; }
        public NotificationType getType() { return type; }
        public int getNotificationId() { return notificationId; }
    }
}