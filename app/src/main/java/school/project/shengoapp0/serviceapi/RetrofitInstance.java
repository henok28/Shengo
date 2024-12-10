package school.project.shengoapp0.serviceapi;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import school.project.shengoapp0.utilities.NetworkUtils;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    static String BASE_URL = "http://192.168.1.5:8000/";




    public static ShengoApiInterface getService(Context context){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ShengoApiInterface.class);
    }
}
