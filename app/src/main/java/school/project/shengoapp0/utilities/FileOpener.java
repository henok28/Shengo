package school.project.shengoapp0.utilities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;

public class FileOpener {

    /**
     * Combines the baseUrl with the file path from the API and starts the download.
     * Ensures that the file name includes a proper ".pdf" extension.
     *
     * @param context  the Context from which this is called.
     * @param filePath the relative file path from the API response (e.g., "resources/yourfile.pdf").
     * @param title    the title used for the file name.
     * @param baseUrl  the base URL for your server.
     */
    public static void openPdfFile(Context context, String filePath, String title, String baseUrl) {
        // Build the full URL to download the file.
        String fullFileUrl = baseUrl + "/storage/" + filePath;
        Log.d("PdfOpener", "openPdfFile URL: " + fullFileUrl);

        // Ensure the file name ends with ".pdf". If not, append it.
        String pdfFileName = title.toLowerCase().endsWith(".pdf") ? title : title + ".pdf";

        downloadAndOpenFile(context, fullFileUrl, pdfFileName);
    }

    /**
     * Downloads a file using the Android DownloadManager and opens it after completion.
     *
     * @param context  the Context to use.
     * @param fileUrl  the URL from which to download the file.
     * @param fileName the name (with extension) to save the file as.
     */
    public static void downloadAndOpenFile(final Context context, final String fileUrl, final String fileName) {
        try {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager == null) {
                Toast.makeText(context, "Download manager not available", Toast.LENGTH_SHORT).show();
                return;
            }

            Uri uri = Uri.parse(fileUrl);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(fileName);
            request.setDescription("Downloading " + fileName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            // Use the app's external files directory, in the Downloads folder.
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fileName);

            // Set the MIME type to PDF.
            request.setMimeType("application/pdf");

            // Enqueue the download and capture the downloadId.
            final long downloadId = downloadManager.enqueue(request);
            Toast.makeText(context, "Downloading file...", Toast.LENGTH_SHORT).show();

            // Register a BroadcastReceiver to listen for when the download completes.
            BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
                @Override
                public void onReceive(Context ctxt, Intent intent) {
                    long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    if (downloadId == id) {
                        // Reconstruct the file path (same directory and name as specified in the request).
                        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
                        Log.d("PdfOpener", "Download complete. File path: " + file.getAbsolutePath());

                        // Check if the file exists before trying to open it.
                        if (file.exists()) {
                            openFile(context, file);
                        } else {
                            Log.e("PdfOpener", "File does not exist: " + file.getAbsolutePath());
                            Toast.makeText(context, "File download failed", Toast.LENGTH_SHORT).show();
                        }

                        // Unregister the receiver safely.
                        try {
                            context.unregisterReceiver(this);
                        } catch (IllegalArgumentException e) {
                            // Receiver might have been already unregistered.
                        }
                    }
                }
            };

            // Register the broadcast receiver for download completion.
            context.registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(context, "Error downloading file", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Opens the given file using an external PDF viewer.
     *
     * @param context the Context to use.
     * @param file    the File to open.
     */
    public static void openFile(Context context, File file) {
        try {
            // Get a content URI for the file using the FileProvider.
            Uri uri = FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".fileprovider",
                    file);

            // Create an intent to view the file.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            // Grant temporary read permission to the target app.
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);

            // Start the activity.
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(context, "No PDF viewer installed", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("PdfOpener", "openFile Exception: " + e);
        }
    }
}
