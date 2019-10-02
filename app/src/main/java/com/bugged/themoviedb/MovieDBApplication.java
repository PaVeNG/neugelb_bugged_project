package com.bugged.themoviedb;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bugged.themoviedb.injection.component.ApplicationComponent;
import com.bugged.themoviedb.injection.component.DaggerApplicationComponent;
import com.bugged.themoviedb.injection.module.ApplicationModule;

import timber.log.Timber;

@SuppressLint("Registered")
public class MovieDBApplication extends Application {
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
//            Fabric.with(this, new Crashlytics());
        }
    }

    public static MovieDBApplication get(Context context) {
        return (MovieDBApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
            Log.d("Watch me","watch me");
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
