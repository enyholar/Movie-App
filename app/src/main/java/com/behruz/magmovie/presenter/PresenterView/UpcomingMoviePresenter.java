package com.behruz.magmovie.presenter.PresenterView;

import android.annotation.SuppressLint;

import com.behruz.magmovie.base.presenter.Presenter;
import com.uwetrottmann.tmdb2.entities.BaseMovie;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface UpcomingMoviePresenter extends Presenter<UpcomingMovieListView> {

  void start();

  @SuppressLint("StaticFieldLeak")
  void loadUpcomingMovieList(int page,boolean isRefresh);

    void openMovieDetailScreen(BaseMovie baseMovie);
}
