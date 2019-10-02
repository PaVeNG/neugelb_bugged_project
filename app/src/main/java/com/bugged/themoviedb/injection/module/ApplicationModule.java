package com.bugged.themoviedb.injection.module;

import android.app.Application;
import android.content.Context;

import com.bugged.themoviedb.data.remote.MovieListService;
import com.bugged.themoviedb.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    MovieListService provideRibotsService() {
        return MovieListService.Creator.newMovieDBServices();
    }

}
