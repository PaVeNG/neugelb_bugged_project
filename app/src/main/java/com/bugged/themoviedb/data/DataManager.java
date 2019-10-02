package com.bugged.themoviedb.data;



import com.bugged.themoviedb.data.local.PreferencesHelper;
import com.bugged.themoviedb.data.remote.MovieListService;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DataManager {

    private final PreferencesHelper mPreferencesHelper;
    private final MovieListService mMovieListService;

    @Inject
    public DataManager(MovieListService movieListService, PreferencesHelper preferencesHelper) {
        mMovieListService = movieListService;
        mPreferencesHelper = preferencesHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public MovieListService getnewMovieDBServices() {
        return mMovieListService;
    }

}
