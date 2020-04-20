package com.behruz.magmovie.presenter.PresenterView;

import android.annotation.SuppressLint;

import com.behruz.magmovie.base.presenter.Presenter;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface MovieDetailPresenter extends Presenter<MovieDetailView> {

  void start();

  @SuppressLint("StaticFieldLeak")
  void loadMovieDetails();

    @SuppressLint("StaticFieldLeak")
    void loadReviewDetail();

    @SuppressLint("StaticFieldLeak")
    void loadTrailerDetail();

    void openTrailerPlayerScreen(String name, String key);
}
