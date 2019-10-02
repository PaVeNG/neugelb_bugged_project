package com.bugged.themoviedb.ui.base.MovieDetails;

import android.os.Bundle;

import com.bugged.themoviedb.data.DataManager;
import com.bugged.themoviedb.data.model.Movie;
import com.bugged.themoviedb.ui.base.BasePresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;

import javax.inject.Inject;


import rx.Subscription;

public class MovieDetailsPresenter extends BasePresenter<MovieDetailsView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MovieDetailsPresenter(DataManager dataManager){mDataManager = dataManager;}

    @Override
    public void attachView(MovieDetailsView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mSubscription !=null) mSubscription.unsubscribe();
    }

    public Movie getMovie(Bundle userStr){
        String data = userStr.getString("data");
        Gson gson = new Gson();
        Type type = new TypeToken<Movie>(){}.getType();
        Movie movie = gson.fromJson(data,type);

        return movie;
    }

}
