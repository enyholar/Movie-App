package com.behruz.magmovie.presenter.PresenterView;

import android.annotation.SuppressLint;

import com.behruz.magmovie.base.presenter.Presenter;
import com.uwetrottmann.tmdb2.entities.BaseMovie;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface NowPlayingMoviePresenter extends Presenter<NowPlayingMovieListView> {

    void start();

    @SuppressLint("StaticFieldLeak")
    void loadNowPlayingMovieList(int page,boolean isRefresh);

    void openMovieDetailScreen(BaseMovie baseMovie);
}
