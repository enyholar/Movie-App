package com.behruz.magmovie.presenter.PresenterView;

import android.annotation.SuppressLint;

import com.behruz.magmovie.base.presenter.Presenter;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface TopRatedTVShowPresenter extends Presenter<TopRatedTvShowView> {

    void start();

    @SuppressLint("StaticFieldLeak")
    void loadTopRatedTvShowList(int page,boolean isRefresh);

    void openMovieDetailScreen(BaseTvShow baseTvShow);
}
