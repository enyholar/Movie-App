package com.behruz.magmovie.adapter;
import com.uwetrottmann.tmdb2.entities.CastMember;
import com.uwetrottmann.tmdb2.entities.CrewMember;

/**
 * Created by ENNY on 2/16/2018.
 */

public interface CastGuestCrewListner {
  void ItemCastClick(CastMember model, int pos);
  void ItemCrewClick(CrewMember model, int pos);
  void ItemGuestClick(CastMember model, int pos);
}
