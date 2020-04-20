package com.behruz.magmovie.presenter.PresenterView;

import com.behruz.magmovie.adapter.MovieGenresAdapter;
import com.behruz.magmovie.base.presenter.LoadDataView;
import com.behruz.magmovie.model.GenresModel;

import java.util.List;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface MovieGenresView extends LoadDataView {
    void initAdapter(List<GenresModel> genres);
    void updateAdapter(List<GenresModel> genresList);
    MovieGenresAdapter getAdapter();
}
