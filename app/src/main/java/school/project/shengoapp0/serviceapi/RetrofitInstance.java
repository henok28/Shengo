package school.project.shengoapp0.serviceapi;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    static String BASE_URL = "http://192.168.186.196:8000/";
//    static String BASE_URL = "http://192.168.225.253:8000/";

//    static String BASE_URL = "https://dummyjson.com/";
//    https://dummyjson.com/c/e86e-2b35-4f73-8554

    public static ShengoApiInterface getService(Context context){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //attaching it to okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
        Interceptor headerInterceptor = new Interceptor() {
            @NonNull
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException, IOException {
                Request originalRequest = chain.request();
                Request modifiedRequest = originalRequest.newBuilder()
                        .addHeader("Accept", "application/json") // Set header
                        .build();
                return chain.proceed(modifiedRequest);
            }
        };
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ShengoApiInterface.class);
    }

}
