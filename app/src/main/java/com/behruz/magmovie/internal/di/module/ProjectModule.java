package com.behruz.magmovie.internal.di.module;

import android.app.Activity;


import com.behruz.magmovie.internal.di.PerActivity;
import com.behruz.magmovie.presenter.PresenterImpl.MostPopularMoviePresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.MostPopularTVShowPresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.MostRatedMoviePresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.MovieDetailPresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.MovieGenresDetailPresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.MovieGenresPresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.NowPlayingMoviePresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.OnAiringTVShowPresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.OnTheAirTVShowPresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.TopRatedTVShowPresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.TvDetailPresenterImpl;
import com.behruz.magmovie.presenter.PresenterImpl.UpcomingMoviePresenterImpl;
import com.behruz.magmovie.presenter.PresenterView.MostPopularMoviePresenter;
import com.behruz.magmovie.presenter.PresenterView.MostPopularTVShowPresenter;
import com.behruz.magmovie.presenter.PresenterView.MostRatedMoviePresenter;
import com.behruz.magmovie.presenter.PresenterView.MovieDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.MovieGenresDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.MovieGenresPresenter;
import com.behruz.magmovie.presenter.PresenterView.NowPlayingMoviePresenter;
import com.behruz.magmovie.presenter.PresenterView.OnAiringTVShowPresenter;
import com.behruz.magmovie.presenter.PresenterView.OnTheAirTVShowPresenter;
import com.behruz.magmovie.presenter.PresenterView.TopRatedTVShowPresenter;
import com.behruz.magmovie.presenter.PresenterView.TvDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.UpcomingMoviePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Enny  on 29/11/2016.
 */
@Module(includes = ApplicationModule.class)
public class ProjectModule {

    private final Activity activity;

    public ProjectModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }


    @Provides
    MovieGenresPresenter provideRadioListPresenter() {
        return new MovieGenresPresenterImpl();
    }


    @Provides
    MovieGenresDetailPresenter provideMovieGenresDetailPresenter() {
        return new MovieGenresDetailPresenterImpl();
    }

    @Provides
    MovieDetailPresenter provideMovieDetailPresenter() {
        return new MovieDetailPresenterImpl();
    }

    @Provides
    MostPopularMoviePresenter provideMostPopularMoviePresenter() {
        return new MostPopularMoviePresenterImpl();
    }

    @Provides
    UpcomingMoviePresenter provideUpcomingMoviePresenter() {
        return new UpcomingMoviePresenterImpl();
    }

    @Provides
    MostRatedMoviePresenter provideMostRatedMoviePresenter() {
        return new MostRatedMoviePresenterImpl();
    }

    @Provides
    NowPlayingMoviePresenter provideNowPlayingMoviePresenter() {
        return new NowPlayingMoviePresenterImpl();
    }


    @Provides
    MostPopularTVShowPresenter provideMostPopularTVShowPresenter() {
        return new MostPopularTVShowPresenterImpl();
    }

    @Provides
    OnTheAirTVShowPresenter provideOnTheAirTVShowPresenter() {
        return new OnTheAirTVShowPresenterImpl();
    }

    @Provides
    OnAiringTVShowPresenter provideOnAiringTVShowPresenter() {
        return new OnAiringTVShowPresenterImpl();
    }

    @Provides
    TopRatedTVShowPresenter provideTopRatedTVShowPresenter() {
        return new TopRatedTVShowPresenterImpl();
    }

    @Provides
    TvDetailPresenter provideTvDetailPresenter() {
        return new TvDetailPresenterImpl();
    }

}

