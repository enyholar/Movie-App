package com.behruz.magmovie.presenter.PresenterView;

import android.annotation.SuppressLint;

import com.behruz.magmovie.base.presenter.Presenter;
import com.uwetrottmann.tmdb2.entities.BaseMovie;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface MovieGenresDetailPresenter extends Presenter<MovieGenresDetailView> {

  void start();

  @SuppressLint("StaticFieldLeak")
  void loadGenresDetails(int page);

    void openMovieDetailScreen(BaseMovie baseMovie);
}
