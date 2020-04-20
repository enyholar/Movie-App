package com.behruz.magmovie.presenter.PresenterView;

import com.behruz.magmovie.adapter.MovieListAdapter;
import com.behruz.magmovie.base.presenter.LoadDataView;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface NowPlayingMovieListView extends LoadDataView {
    MovieListAdapter getAdapter();
}
