package com.behruz.magmovie.presenter.PresenterView;

import com.behruz.magmovie.adapter.CastInfoAdapter;
import com.behruz.magmovie.adapter.TvSeasonAdapter;
import com.behruz.magmovie.base.presenter.LoadDataView;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.Review;
import com.uwetrottmann.tmdb2.entities.TvSeason;
import com.uwetrottmann.tmdb2.entities.TvShow;
import com.uwetrottmann.tmdb2.entities.Videos;

import java.util.List;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface TvDetailView extends LoadDataView {
    void setUpMovieDetailsToView(TvShow movie);

    void setUpMovieDetailsOverView(TvShow movie);

    void showTrailers(List<Videos.Video> trailerDatas);

    void showReviews(List<Review> reviewDatas);

    BaseTvShow getTvData();

    TvSeason getTvDataSeason();

    void hidePageReview();

    void showPageReview();

    CastInfoAdapter getCastMemberAdapter();

    TvSeasonAdapter getTvSeasonAdapter();
}
