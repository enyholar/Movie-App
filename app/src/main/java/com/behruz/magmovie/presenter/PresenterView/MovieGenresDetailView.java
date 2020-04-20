package com.behruz.magmovie.presenter.PresenterView;

import com.behruz.magmovie.adapter.MovieListAdapter;
import com.behruz.magmovie.base.presenter.LoadDataView;
import com.behruz.magmovie.model.GenresModel;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface MovieGenresDetailView extends LoadDataView {
    GenresModel getGenres();
    MovieListAdapter getAdapter();
}
