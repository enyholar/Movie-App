package com.behruz.magmovie.adapter;
import com.uwetrottmann.tmdb2.entities.Videos;

/**
 * Created by ENNY on 2/16/2018.
 */

public interface TrailerListner {
  void ItemClick(Videos.Video model, int pos);
}
