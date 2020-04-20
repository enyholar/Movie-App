package com.behruz.magmovie.adapter;

import com.uwetrottmann.tmdb2.entities.BaseMovie;

/**
 * Created by ENNY on 2/16/2018.
 */

public interface HomeMovieListner {
  void ItemClick(BaseMovie model, int pos);
}
