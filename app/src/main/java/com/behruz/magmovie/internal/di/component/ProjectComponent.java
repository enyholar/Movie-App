package com.behruz.magmovie.internal.di.component;


import com.behruz.magmovie.internal.di.PerActivity;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.ui.activity.MovieFullDetailActivity;
import com.behruz.magmovie.ui.activity.TvFullDetailActivity;
import com.behruz.magmovie.ui.fragment.AiringMovieActivity;
import com.behruz.magmovie.ui.fragment.GenresDetailFragment;
import com.behruz.magmovie.ui.fragment.MostPopularMovieActivity;
import com.behruz.magmovie.ui.fragment.MostRatedMovieActivity;
import com.behruz.magmovie.ui.fragment.MovieDetailFragment;
import com.behruz.magmovie.ui.fragment.MovieGenresFragment;
import com.behruz.magmovie.ui.fragment.UpcomingMovieActivity;
import com.behruz.magmovie.ui.fragment.movietvDetail.MovieCastFragment;
import com.behruz.magmovie.ui.fragment.movietvDetail.MovieDetailNewFragment;
import com.behruz.magmovie.ui.fragment.movietvDetail.MovieOverviewFragment;
import com.behruz.magmovie.ui.fragment.movietvDetail.MovieReviewActivity;
import com.behruz.magmovie.ui.fragment.movietvDetail.TrailerFragment;
import com.behruz.magmovie.ui.fragment.tv.MostPopularTVActivity;
import com.behruz.magmovie.ui.fragment.tv.OnAiringTVActivity;
import com.behruz.magmovie.ui.fragment.tv.OnTheAirTVActivity;
import com.behruz.magmovie.ui.fragment.tv.TopRatedTvActivity;
import com.behruz.magmovie.ui.fragment.tv.TvDetailFragment;
import com.behruz.magmovie.ui.fragment.tv.TvOverviewFragment;
import com.behruz.magmovie.ui.fragment.tv.TvSeasonFragment;

import dagger.Component;

/**
 * Created by Enny on 29/11/2016.
 */
@PerActivity
@Component(modules = ProjectModule.class)
public interface ProjectComponent {

    void inject(MovieGenresFragment movieGenresFragment);

    void inject(GenresDetailFragment genresDetailFragment);

    void inject(MovieDetailFragment movieDetailFragment);

    void inject(MostPopularMovieActivity mostPopularVideoFragment);

    void inject(UpcomingMovieActivity mostUpcomingMovieActivity);

    void inject(AiringMovieActivity nowPlayingMovieFragment);

    void inject(MostRatedMovieActivity mostRatedMovieFragment);

    void inject(MostPopularTVActivity mostPopularTVFragment);

    void inject(OnAiringTVActivity onAiringTVFragment);

    void inject(OnTheAirTVActivity onTheAirTVFragment);

    void inject(TopRatedTvActivity topRatedTVFragment);

    void inject(TvDetailFragment tvDetailFragment);

    void inject(MovieDetailNewFragment movieDetailNewFragment);

    void inject(MovieOverviewFragment movieOverviewFragment);

    void inject(TrailerFragment trailerFragment);

    void inject(MovieReviewActivity movieReviewFragment);

    void inject (MovieCastFragment movieCastFragment);

//    void inject (TvCastFragment tvCastFragment);

    void inject (TvOverviewFragment tvOverviewFragment);

    void inject (TvSeasonFragment tvSeasonFragment);

    void inject (TvFullDetailActivity tvFullDetailActivity);

    void inject (MovieFullDetailActivity tvSeasonFragment);
}
