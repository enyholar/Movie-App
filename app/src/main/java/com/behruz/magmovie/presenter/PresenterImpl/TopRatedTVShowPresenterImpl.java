package com.behruz.magmovie.presenter.PresenterImpl;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.behruz.magmovie.internal.ProgressBarHandler;
import com.behruz.magmovie.presenter.PresenterView.TopRatedTVShowPresenter;
import com.behruz.magmovie.presenter.PresenterView.TopRatedTvShowView;
import com.behruz.magmovie.ui.activity.TvFullDetailActivity;
import com.behruz.magmovie.utils.Constant;
import com.google.gson.Gson;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.TvShowResultsPage;
import com.uwetrottmann.tmdb2.services.TvService;

import retrofit2.Response;

/**
 * Created by ENNY on 2/20/2018.
 */

public class TopRatedTVShowPresenterImpl implements TopRatedTVShowPresenter {
    private TopRatedTvShowView mView;
    private ProgressBarHandler progressBarHandler;


    @Override
    public void start() {
        progressBarHandler = new ProgressBarHandler(mView.getContext());
    }



    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setView(@NonNull TopRatedTvShowView view) {
        this.mView = view;
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadTopRatedTvShowList(final int page,boolean isRefresh) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.showLineLoading();
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                TvService tvService = tmdb.tvService();
// Call any of the available endpoints
                try {
                    Response<TvShowResultsPage> response = tvService.topRated(page,"en-US"
                    ).execute();
//                    Response<MovieResultsPage> response = moviesService.movies(mView.getGenres().id
//                    ,"en-US",true,SortBy.ORIGINAL_TITLE_ASC).execute();
                    if (response.isSuccessful()) {
                        if( mView.getAdapter() != null) {
                            mView.getAdapter().clear();
                        }
                        TvShowResultsPage movie = response.body();
                        assert movie != null;
                        mView.getAdapter().addAll(movie.results);
                       // mView.initAdapter(movie.genres);
                        // mView.updateAdapter(movie.genres);
                    } else {
                        System.out.println(" is awesome!");
                    }
                } catch (Exception e) {
                    System.out.println(e.toString() + " is awesome!");
                    // see execute() javadoc
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mView.hideLineLoading();
            }
        }.execute();
    }

    @Override
    public void openMovieDetailScreen(BaseTvShow baseMovie) {
        Intent intent = new Intent(mView.getContext(), TvFullDetailActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(baseMovie);
        intent.putExtra("baseMovie", json);
        mView.getContext().startActivity(intent);
    }

}