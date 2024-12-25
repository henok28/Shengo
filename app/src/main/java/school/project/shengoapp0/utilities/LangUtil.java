package school.project.shengoapp0.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LangUtil {
    public static void setLocale(Context context, String langCode){
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        configuration.setLocale(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        context.getSharedPreferences("LanguagePreference", Context.MODE_PRIVATE)
                .edit()
                .putString("SelectedLanguage", langCode)
                .apply();
    }

    public static String getSavedLanguage(Context context){
        return context.getSharedPreferences("LanguagePreference", Context.MODE_PRIVATE)
                .getString("SelectedLanguage", "en");
    }
}
