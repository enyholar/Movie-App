package com.behruz.magmovie.presenter.PresenterView;

import com.behruz.magmovie.adapter.CastInfoAdapter;
import com.behruz.magmovie.adapter.MovieReviewAdapter;
import com.behruz.magmovie.adapter.MovieSimilarAdapter;
import com.behruz.magmovie.adapter.MovieTrailerAdapter;
import com.behruz.magmovie.base.presenter.LoadDataView;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.Review;
import com.uwetrottmann.tmdb2.entities.Videos;

import java.util.List;

/**
 * Created by ENNY on 2/20/2018.
 */

public interface MovieDetailView extends LoadDataView {
    void setUpMovieDetailsToView(Movie movie);

    void showTrailers(List<Videos.Video> trailerDatas);

    void showReviews(List<Review> reviewDatas);

    BaseMovie getMovieData();

    void hidePageReview();

    void initExpandLayout();

    void showSimilarAvailabilityVideo();


    void initSimilarExpandLayout();

    void showPageReview();

    MovieSimilarAdapter getSimilarAdapter();

    MovieReviewAdapter getReviewAdapter();

    CastInfoAdapter getCastMemberAdapter();

    MovieTrailerAdapter getTrailerAdapter();
}
