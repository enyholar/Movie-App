package com.behruz.magmovie.adapter;

import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.TvSeason;

/**
 * Created by ENNY on 2/16/2018.
 */

public interface TvSeriesListner {
  void ItemClick(TvSeason model, int pos);
}
