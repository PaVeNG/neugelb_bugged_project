package com.bugged.themoviedb.injection.component;

import com.bugged.themoviedb.injection.PerActivity;
import com.bugged.themoviedb.injection.module.ActivityModule;
import com.bugged.themoviedb.ui.base.BaseFragment;
import com.bugged.themoviedb.ui.base.MovieDetails.MovieDetailsFragment;
import com.bugged.themoviedb.ui.base.MovieList.MoviesListActivity;
import com.bugged.themoviedb.util.AlertDialogManager;
import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseFragment baseFragment);
    void inject(MoviesListActivity moviesListActivity);
    void inject(AlertDialogManager alertDialogManager);
    void inject(MovieDetailsFragment movieDetailsFragment);
}
