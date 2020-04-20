package com.behruz.magmovie.presenter.PresenterView;

import com.behruz.magmovie.adapter.TvListAdapter;
import com.behruz.magmovie.base.presenter.LoadDataView;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface MostPopularTvShowView extends LoadDataView {
    TvListAdapter getAdapter();
}
