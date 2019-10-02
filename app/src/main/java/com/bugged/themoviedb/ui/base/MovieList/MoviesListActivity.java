package com.bugged.themoviedb.ui.base.MovieList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.bugged.themoviedb.R;
import com.bugged.themoviedb.data.model.Movie;
import com.bugged.themoviedb.data.model.Page;
import com.bugged.themoviedb.ui.base.BaseActivity;
import com.bugged.themoviedb.ui.base.MovieDetails.MovieDetailsFragment;
import com.bugged.themoviedb.util.AlertDialogManager;
import com.bugged.themoviedb.util.NetworkUtil;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesListActivity extends BaseActivity implements MoviesListView, TextWatcher {



    @Order(1)
    @BindView(R.id.movies_list)
    RecyclerView movies_list;
    @Order(2)
    @BindView(R.id.autoCompleteTextView1)
    AutoCompleteTextView autoCompleteTextView1;

    @Inject
    MoviesListPresenter moviesListPresenter;

    @Inject
    AlertDialogManager alertDialogManager;

    BaseActivity baseActivity;


    NetworkUtil networkUtills;
    MoviesAdapter moviesAdapter=null;
    Gson gson;
    ProgressDialog progressDialog;
    public static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private boolean search=false;
    private boolean show_progress=true;
    ArrayAdapter<String> adapter;
    private LinearLayoutManager mLayoutManager;
    private int lastPage=2;
    private ArrayList<String> movie_names_list = new ArrayList<>();

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        baseActivity = (BaseActivity) this;
        baseActivity.activityComponent().inject(this);
        ButterKnife.bind(this);
        moviesListPresenter.attachView(this);
        gson = new Gson();
        mLayoutManager = new LinearLayoutManager(this);
        networkUtills = new NetworkUtil(MoviesListActivity.this);
        movies_list.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if(currentPage<lastPage){
                    isLoading = true;
                    currentPage++;
                    if(!search){
                        getMoviesForList();
                    }

                }else{
                    isLoading=false;
                    isLastPage=true;
                }
            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,movie_names_list);
        autoCompleteTextView1.setAdapter(adapter);
        autoCompleteTextView1.addTextChangedListener(this);
        getMoviesForList();

    }

    private void getMoviesForList() {
        if(networkUtills.isNetworkConnected()){
            progressDialog = alertDialogManager.showProgressBar(MoviesListActivity.this);
            if (show_progress) {
                show_progress=false;
                progressDialog.show();
            }
            moviesListPresenter.getMovies(String.valueOf(currentPage));
        }else{
            alertDialogManager.showAlertDialog(MoviesListActivity.this,"No Internet Connection!");
        }
    }

    public void onItemClicked(Movie movie) {
        for(int i=0; i<getSupportFragmentManager().getBackStackEntryCount(); i++){
            getSupportFragmentManager().popBackStack();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        String data = gson.toJson(movie);
        bundle.putString("data",data);
        fragment.setArguments(bundle);
        dismissProgress();
        fragmentTransaction.replace(R.id.fragment_container,fragment,null).addToBackStack(null).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle("Latest Movies");
    }

    @Override
    public void onRequestSuccess(Page page) {
        try {
            lastPage = Integer.parseInt(page.getTotal_pages());
            movie_names_list = moviesListPresenter.getMovieNames(page.getResults(),movie_names_list);
            if (adapter!=null) {
                adapter.notifyDataSetChanged();
            }
            if (moviesAdapter==null) {
                moviesAdapter = new MoviesAdapter(page.getResults(), this, new MoviesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Movie item) {
                        onItemClicked(item);
                    }
                });
                movies_list.setLayoutManager(new LinearLayoutManager(this));
                movies_list.setAdapter(moviesAdapter);
                progressDialog.dismiss();
            }else{
                moviesAdapter.addAll(page.getResults());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            dismissProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestError(String s) {
        dismissProgress();
        alertDialogManager.showAlertDialog(MoviesListActivity.this,s);
    }

    @Override
    public void onUserNotFound(String s) {
        dismissProgress();
        alertDialogManager.showAlertDialog(MoviesListActivity.this,s);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length()>0){
            search=true;
        }else{
            search=false;
        }
        moviesAdapter.getFilter().filter(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void dismissProgress(){
        if (progressDialog!=null) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }
}
