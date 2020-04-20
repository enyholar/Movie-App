package com.behruz.magmovie.presenter.PresenterView;

import android.annotation.SuppressLint;

import com.behruz.magmovie.base.presenter.Presenter;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface OnTheAirTVShowPresenter extends Presenter<OnTheAirTvShowView> {

    void start();

    @SuppressLint("StaticFieldLeak")
    void loadOnAiringTvShowList(int page,boolean isRefresh);

    void openMovieDetailScreen(BaseTvShow baseTvShow);
}
