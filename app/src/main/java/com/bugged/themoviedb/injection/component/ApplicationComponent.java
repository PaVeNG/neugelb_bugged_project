package com.bugged.themoviedb.injection.component;

import android.app.Application;
import android.content.Context;

import com.bugged.themoviedb.data.DataManager;
import com.bugged.themoviedb.data.local.PreferencesHelper;
import com.bugged.themoviedb.injection.ApplicationContext;
import com.bugged.themoviedb.injection.module.ApplicationModule;
import com.bugged.themoviedb.util.AlertDialogManager;
import com.bugged.themoviedb.util.RxEventBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {


    @ApplicationContext
    Context context();
    Application application();
    PreferencesHelper preferencesHelper();
    DataManager dataManager();
    RxEventBus eventBus();
    AlertDialogManager alertDialogManager();

}
