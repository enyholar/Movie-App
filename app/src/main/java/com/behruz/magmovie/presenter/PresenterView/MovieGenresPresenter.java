package com.behruz.magmovie.presenter.PresenterView;

import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.model.GenresModel;

import java.util.List;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface MovieGenresPresenter extends Presenter<MovieGenresView> {

  void start();
  List<GenresModel> getGenreLists();

    void openGenreDetailScreen(GenresModel genre);
}
