package school.project.shengoapp0.repositories;

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

public class ResourceRepo {
    ResourceService resourceService;
    private String BASEURL = "http://192.168.179.196:8000";
    private String token = "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTkyLjE2OC4xNzkuMTk2OjgwMDAvYXBpL2F1dGgvbG9naW4iLCJpYXQiOjE3MzM0ODIyOTgsImV4cCI6MTczMzQ4NTg5OCwibmJmIjoxNzMzNDgyMjk4LCJqdGkiOiJsbUZVbjJ1VDc0ZUZmc1hnIiwic3ViIjoiYmRlNzNjY2UtODYwZC00YzRjLWExMzAtYjJhYjMzY2I3OTRiIiwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.bSZH7WD8OeLuLrQ7ZKeSDq9TVBzwRVW9OBITr30_2qE";
    private MutableLiveData<List<Book>> ResourceResponse = new MutableLiveData<>();

    public MutableLiveData<List<Book>> getResourceResponse() {
        return ResourceResponse;
    }

    public  ResourceRepo(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        resourceService = retrofit.create(ResourceService.class);
    }

    public void getResourceData(){

        Call<List<ResourceModal>> call = resourceService.getResource(token);

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
