package com.bugged.themoviedb.ui.base.MovieList;


import android.util.Log;

import com.bugged.themoviedb.data.DataManager;
import com.bugged.themoviedb.data.model.Movie;
import com.bugged.themoviedb.data.model.Page;
import com.bugged.themoviedb.ui.base.BasePresenter;
import com.bugged.themoviedb.util.ConstantUtils;
import com.bugged.themoviedb.util.RxUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MoviesListPresenter extends BasePresenter<MoviesListView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MoviesListPresenter(DataManager dataManager){mDataManager = dataManager;}

    @Override
    public void attachView(MoviesListView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mSubscription !=null) mSubscription.unsubscribe();
    }

    public void getMovies(String page) {
        Log.e("TESTINGRESP","===1");
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getnewMovieDBServices().getMovies("release_date.desc", ConstantUtils.API_KEY,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Page>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TESTINGRESP","===2");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TESTINGRESP","===3"+e.toString());
                        getMvpView().onRequestError("User Not Found!");
                    }

                    @Override
                    public void onNext(Page page) {
                        getMvpView().onRequestSuccess(page);
                        Log.e("TESTINGRESP","==="+new Gson().toJson(page));
                    }
                });
    }

    public ArrayList<String> getMovieNames(ArrayList<Movie> results, ArrayList<String> movie_names_list) {
        for(int i=0; i<results.size(); i++){
            movie_names_list.add(results.get(i).getTitle());
        }

        return movie_names_list;
    }
}
