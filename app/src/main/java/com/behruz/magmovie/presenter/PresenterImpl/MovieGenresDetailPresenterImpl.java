package com.behruz.magmovie.presenter.PresenterImpl;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.behruz.magmovie.internal.ProgressBarHandler;
import com.behruz.magmovie.presenter.PresenterView.MovieGenresDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.MovieGenresDetailView;
import com.behruz.magmovie.ui.activity.MovieFullDetailActivity;
import com.behruz.magmovie.utils.Constant;
import com.google.gson.Gson;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.DiscoverFilter;
import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import com.uwetrottmann.tmdb2.services.DiscoverService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by ENNY on 2/20/2018.
 */

public class MovieGenresDetailPresenterImpl implements MovieGenresDetailPresenter {
    private MovieGenresDetailView mView;
    private ProgressBarHandler progressBarHandler;
    private List<Genre> genreLists = new ArrayList<>();


    @Override
    public void start() {
        progressBarHandler = new ProgressBarHandler(mView.getContext());
     //   loadGenresDetails();
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
    public void setView(@NonNull MovieGenresDetailView view) {
        this.mView = view;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadGenresDetails(final int page) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.showLineLoading();
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                DiscoverService moviesService = tmdb.discoverService();
                DiscoverFilter discoverFilter = new DiscoverFilter(mView.getGenres().id);
// Call any of the available endpoints
                try {
                    Response<MovieResultsPage> response = moviesService.discoverMovie("en-US",null,SortBy.ORIGINAL_TITLE_ASC,null,null,null,null,null,page,null,null,null,null,null,null,null,null,null,null,null,null,discoverFilter,
                            null,null,null,null,null,null,null,null,null
                    ).execute();
//                    Response<MovieResultsPage> response = moviesService.movies(mView.getGenres().id
//                    ,"en-US",true,SortBy.ORIGINAL_TITLE_ASC).execute();
                    if (response.isSuccessful()) {
                        MovieResultsPage movie = response.body();
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
    public void openMovieDetailScreen(BaseMovie baseMovie) {
        Intent intent = new Intent(mView.getContext(), MovieFullDetailActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(baseMovie);
        intent.putExtra("baseMovie", json);
        mView.getContext().startActivity(intent);
    }

}