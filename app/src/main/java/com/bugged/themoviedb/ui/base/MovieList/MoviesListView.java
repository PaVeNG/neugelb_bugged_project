package com.bugged.themoviedb.ui.base.MovieList;

import com.bugged.themoviedb.data.model.Page;
import com.bugged.themoviedb.ui.base.MvpView;

public interface MoviesListView extends MvpView {

    void onRequestSuccess(Page page);

    void onRequestError(String s);

    void onUserNotFound(String s);
}
