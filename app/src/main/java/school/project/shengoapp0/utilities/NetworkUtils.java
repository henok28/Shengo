package school.project.shengoapp0.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class NetworkUtils {
    private static final String TAG = "NetworkUtils";
    private static final String DEFAULT_IP = "192.168.1.1"; // fallback IP

    public static String getLocalIpAddress(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null");
            return DEFAULT_IP;
        }

        Context applicationContext = context.getApplicationContext();
        WifiManager wifiManager = (WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager == null) {
            Log.e(TAG, "WifiManager is null");
            return DEFAULT_IP;
        }

        if (!wifiManager.isWifiEnabled()) {
            Log.e(TAG, "Wifi is not enabled");
            return DEFAULT_IP;
        }

        try {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                int ipAddress = wifiInfo.getIpAddress();
                Log.d(TAG, "Raw IP Address: " + ipAddress);

                // Convert little-endian to big-endian if needed
                String ipString = String.format(
                        "%d.%d.%d.%d",
                        (ipAddress & 0xff),
                        (ipAddress >> 8 & 0xff),
                        (ipAddress >> 16 & 0xff),
                        (ipAddress >> 24 & 0xff));

                Log.d(TAG, "Formatted IP Address: " + ipString);

                // Get subnet mask to determine network address
                String[] ipParts = ipString.split("\\.");
                if (ipParts.length == 4) {
                    // Assuming a typical Class C network (255.255.255.0)
                    String networkAddress = ipParts[0] + "." + ipParts[1] + "." + ipParts[2] + ".1";
                    Log.d(TAG, "Network Address (Gateway): " + networkAddress);
                    return networkAddress;
                }

                return ipString;
            } else {
                Log.e(TAG, "WifiInfo is null");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting IP address", e);
        }

        // Additional check for network connectivity
        ConnectivityManager connectivityManager =
                (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork != null) {
                NetworkCapabilities capabilities =
                        connectivityManager.getNetworkCapabilities(activeNetwork);
                if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.d(TAG, "Device is connected to WiFi");
                } else {
                    Log.e(TAG, "Device is not connected to WiFi");
                }
            } else {
                Log.e(TAG, "No active network");
            }
        }

        Log.e(TAG, "Returning default IP");
        return DEFAULT_IP;
    }
}
