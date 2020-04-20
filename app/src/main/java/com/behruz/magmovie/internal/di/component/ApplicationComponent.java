package com.behruz.magmovie.internal.di.component;

import com.behruz.magmovie.base.AppApplication;
import com.behruz.magmovie.base.BaseActionbarActivity;
import com.behruz.magmovie.base.BaseFragment;
import com.behruz.magmovie.internal.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Enny on 29/11/2016.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(AppApplication app);
    void inject(BaseActionbarActivity activity);
    void inject(BaseFragment activity);

//    void inject(LocalDataSource localDataSource);
}
