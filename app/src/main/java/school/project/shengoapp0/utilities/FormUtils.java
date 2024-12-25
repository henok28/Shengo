package school.project.shengoapp0.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class FormUtils {
    private static final String PREF_NAME = "FORM_STATE";
    private static final String DYNAMIC_FRAG_STATE = "STATE";
    Context context;
    SharedPreferences sharedPreferences;
    public FormUtils(Context context){
        this.context = context.getApplicationContext();
        sharedPreferences = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setFormState(String state){
        if (state == null)return;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DYNAMIC_FRAG_STATE, state);
        editor.apply();
    }
    public String getFormState(){
        String state = sharedPreferences.getString(DYNAMIC_FRAG_STATE, null);
        if (state == null){
            return null;
        }
        return sharedPreferences.getString(DYNAMIC_FRAG_STATE, null);

    }

    public void clear(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
