package school.project.shengoapp0.repositories;

import android.content.Context;
import android.speech.RecognitionService;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import school.project.shengoapp0.model.Book;
import school.project.shengoapp0.model.ResourceModal;
import school.project.shengoapp0.retrofit.ResourceService;
import school.project.shengoapp0.serviceapi.RetrofitInstance;
import school.project.shengoapp0.serviceapi.ShengoApiInterface;
import school.project.shengoapp0.utilities.TokenUtil;

public class ResourceRepo {
    Context context;
    ShengoApiInterface shengoApiInterface;
    private String BASEURL = "http://192.168.179.196:8000";

    private MutableLiveData<List<Book>> ResourceResponse = new MutableLiveData<>();

    public MutableLiveData<List<Book>> getResourceResponse() {
        return ResourceResponse;
    }
    TokenUtil tokenUtil;

    public  ResourceRepo(Context context){
        this.context = context.getApplicationContext();
        this.shengoApiInterface = RetrofitInstance.getService(context);
        this.tokenUtil = new TokenUtil(context);
    }

    public void getResourceData(){

        Call<List<ResourceModal>> call = shengoApiInterface.getResource(tokenUtil.getToken());

        call.enqueue(new Callback<List<ResourceModal>>() {
            @Override
            public void onResponse(Call<List<ResourceModal>> call, Response<List<ResourceModal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ResourceModal> resources = response.body();
                    List<Book> books = new ArrayList<>();
                    for (ResourceModal resource : resources) {
                        String title = resource.getTitle();
                        String catagory = resource.getCategory();
                        String description = resource.getDescription();

                        books.add(new Book(title, catagory, description));

                        ResourceResponse.setValue(books);
                        Log.d("Resource Title", "Title: " + books);
                    }
                } else {
                    Log.d("Resource Response", "Failed with code: " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<List<ResourceModal>> call, Throwable throwable) {
                Log.d("Resource Failure", "Resource Error: "+throwable.getMessage());
            }
        });
    }
}
