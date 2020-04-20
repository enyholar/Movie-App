package com.behruz.magmovie.base;

import androidx.multidex.MultiDexApplication;

import com.behruz.magmovie.internal.di.component.ApplicationComponent;
import com.behruz.magmovie.internal.di.component.DaggerApplicationComponent;
import com.behruz.magmovie.internal.di.module.ApplicationModule;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Enny on 29/11/2016.
 */

public class AppApplication
        extends MultiDexApplication {
    private static AppApplication application;
    private ApplicationComponent mComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
//        ImageLoaderConfiguration localImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this).build();
//        ImageLoader.getInstance().init(localImageLoaderConfiguration);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }


    private void initApplication() {
        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mComponent.inject(this);
    }


    public static AppApplication get() {
        return application;
    }

}
