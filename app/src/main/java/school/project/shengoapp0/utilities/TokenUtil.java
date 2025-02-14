package school.project.shengoapp0.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class TokenUtil {
    private static final String TAG = "TokenUtil";
    private static final String PREF_NAME = "AppPrefs";
    private static final String TOKEN_KEY = "TOKEN";
    private static final String TIMESTAMP_KEY = "TOKEN_TIMESTAMP";
    private static final long EXPIRATION_TIME = 2 * 60 * 60 * 1000;

    private SharedPreferences sharedPreferences;
    private Context context;

    public TokenUtil(Context context){
        this.context = context.getApplicationContext();
        sharedPreferences = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setToken(String token){
        if (token == null || token.isEmpty()) {
            Log.e(TAG, "Attempted to save null or empty token");
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long currentTime = System.currentTimeMillis();
        editor.putString(TOKEN_KEY, token);
        editor.putLong(TIMESTAMP_KEY, currentTime);
        editor.apply();

        Log.d(TAG, "Token saved with timestamp: " + currentTime);
    }

    public String getToken(){
        String token = sharedPreferences.getString(TOKEN_KEY, null);
        if (token == null) {
            Log.d(TAG, "No token found");
            return null;
        }

        long savedTimeStamp = sharedPreferences.getLong(TIMESTAMP_KEY, 0);
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - savedTimeStamp;
        if ( timeDifference > EXPIRATION_TIME){
            clearToken();
            updateLoginStatus(false);
            return null;
        }
        Log.d(TAG, "Valid token found. Remaining time: " + (EXPIRATION_TIME - timeDifference) / 1000 + "s");
        return "Bearer "+sharedPreferences.getString(TOKEN_KEY, null);

    }


    public void clearToken(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        editor.remove(TIMESTAMP_KEY);
        editor.apply();
        updateLoginStatus(false);
        Log.d(TAG, "Token cleared");
    }
    public boolean isTokenValid() {
        return getToken() != null;
    }

    private void updateLoginStatus(boolean isLoggedIn) {
        SharedPreferences loginPrefs = context.getSharedPreferences("myAppLoginStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPrefs.edit();
        editor.putBoolean("hasSeenLogin", !isLoggedIn); // inverse because of your logic
        editor.apply();
        Log.d(TAG, "Login status updated: hasSeenLogin = " + !isLoggedIn);
    }

}
