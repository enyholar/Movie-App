package com.behruz.magmovie.presenter.PresenterView;

import android.annotation.SuppressLint;

import com.behruz.magmovie.base.presenter.Presenter;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface TvDetailPresenter extends Presenter<TvDetailView> {

  void start();

  @SuppressLint("StaticFieldLeak")
  void loadMovieDetails();

    @SuppressLint("StaticFieldLeak")
    void loadTvSeason();

    @SuppressLint("StaticFieldLeak")
    void loadCastMember();

    @SuppressLint("StaticFieldLeak")
    void loadTrailers();
}
