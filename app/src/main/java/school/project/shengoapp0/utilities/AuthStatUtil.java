package school.project.shengoapp0.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthStatUtil {
    private Context context;
    private SharedPreferences sharedPreferences;
    public AuthStatUtil(Context context) {
        this.context = context.getApplicationContext();
        sharedPreferences = this.context.getSharedPreferences("AuthUtilitiesPref", Context.MODE_PRIVATE);
    }


    public void setVerificationStatus(String verificationStatus){
        if (verificationStatus == null || verificationStatus.isEmpty()){
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (verificationStatus.equals("verified")){
            editor.putBoolean("IsVerified", true);
            editor.commit();
        }else{
            editor.putBoolean("IsVerified", false);
            editor.commit();
        }
    }

//    public void getVerificationFormStatus(String verificationFormStats){
//        if (verificationFormStats == null){
//            return;
//        }
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        if (verificationFormStats.equals("pending")){
//            editor.putString("verificationFormStat", "pending");
//            editor.commit();
//        } else if (verificationFormStats.equals("verified")) {
//            editor.putString("verificationFormStat", "verified");
//            editor.commit();
//        }else if (verificationFormStats.equals("rejected")){
//            editor.putString("verificationFormStat", "rejected");
//            editor.commit();
//        }
//    }

    public void setSubscriptionStatus(String subscriptionStatus){
        if (subscriptionStatus == null || subscriptionStatus.isEmpty()){
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (subscriptionStatus.equals("subscribed")){
            editor.putBoolean("subscribed", true);
            editor.commit();
        }else{
            editor.putBoolean("subscribed", false);
            editor.commit();
        }
    }

    public void setSubscriptionStatusString(String subscriptionStatusString){
        if (subscriptionStatusString == null || subscriptionStatusString.isEmpty()){
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("subscriptionStatus", subscriptionStatusString);
        editor.commit();
    }

    public void setVerificationStatusString(String verificationStatusString){
        if (verificationStatusString == null || verificationStatusString.isEmpty()){
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("verificationStatus", verificationStatusString);
        editor.commit();

    }

    public boolean getVerificationStatus(){
        return sharedPreferences.getBoolean("IsVerified", false);
    }

    public boolean getSubscriptionStatus(){
        return sharedPreferences.getBoolean("subscribed", false);
    }



    public String getVerificationStatusString(){
        return sharedPreferences.getString("verificationStatus", "empty");
    }

    public String getSubscriptionStatusString(){
        return sharedPreferences.getString("subscriptionStatus", "empty");
    }

}



