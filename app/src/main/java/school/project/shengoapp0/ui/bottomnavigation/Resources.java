package school.project.shengoapp0.ui.bottomnavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import school.project.shengoapp0.R;
import school.project.shengoapp0.adapters.resources.BookAdapter;
import school.project.shengoapp0.model.Book;
import school.project.shengoapp0.utilities.FileOpener;
import school.project.shengoapp0.viewmodels.ResourceViewModel;

public class Resources extends Fragment {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private ResourceViewModel resourceViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resources, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.book_list);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        resourceViewModel = new ViewModelProvider(this).get(ResourceViewModel.class);


        resourceViewModel.getResourceData();
        Log.d("Data mutable", "onViewCreated: "+resourceViewModel.getData().toString());





        bookList = new ArrayList<>();


        resourceViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                if (books!=null){
                    bookAdapter = new BookAdapter(books, new BookAdapter.OnItemClickListener() {  //Setting the data
                        @Override
                        public void onItemClick(Book book) {
//                            String pdfPath = "/storage/"+book.getFilePath();

                            String pdfPath =book.getFilePath();
                            String title = book.getTitle();
                            String baseUrl = getContext().getString(R.string.base_url);
                            FileOpener.openPdfFile(getContext(), pdfPath, title, baseUrl);
//                            FileOpener.openPdfFile(getContext(), pdfPath); //Make a call
                            Log.d("FIle path", ": "+pdfPath);
                        }
                    });
                    recyclerView.setAdapter(bookAdapter);
                }
            }
        });

        resourceViewModel.getResourceData();



    }
}