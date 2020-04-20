package com.behruz.magmovie.presenter.PresenterImpl;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.behruz.magmovie.R;
import com.behruz.magmovie.internal.ProgressBarHandler;
import com.behruz.magmovie.model.GenresModel;
import com.behruz.magmovie.presenter.PresenterView.MovieGenresPresenter;
import com.behruz.magmovie.presenter.PresenterView.MovieGenresView;
import com.behruz.magmovie.ui.activity.GenresDetailActivity;
import com.behruz.magmovie.utils.Constant;
import com.google.gson.Gson;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.GenreResults;
import com.uwetrottmann.tmdb2.services.GenresService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Response;

/**
 * Created by ENNY on 2/20/2018.
 */

public class MovieGenresPresenterImpl implements MovieGenresPresenter {
    private MovieGenresView mView;
    private ProgressBarHandler progressBarHandler;
    private List<GenresModel> genreLists = new ArrayList<>();


    @Override
    public void start() {
        progressBarHandler = new ProgressBarHandler(mView.getContext());
        initData();
    }

    private void initData(){
        final List<String> genresValues = Arrays.asList(mView.getContext().getResources().getStringArray(R.array.genresValues));
        final String[] genres = mView.getContext().getResources().getStringArray(R.array.genres);
        int[] flagId = new int[]{
                R.drawable.ic_action_genres, R.drawable.ic_adventure_genres, R.drawable.ic_animation_genres
                , R.drawable.ic_comedy_genres, R.drawable.ic_crime_genres, R.drawable.ic_documentary_genres, R.drawable.ic_drama_genres, R.drawable.ic_family, R.drawable.ic_fantasy_genres
                , R.drawable.ic_history_genres, R.drawable.ic_horror_genres, R.drawable.ic_music_genres, R.drawable.ic_mystery_genres, R.drawable.ic_romance, R.drawable.ic_sceince_genres
                , R.drawable.ic_tv_genres, R.drawable.ic_thriller_genres, R.drawable.ic_war_genres, R.drawable.ic_western_genres
        };
        for (int i = 0; i <genres.length ; i++) {
            GenresModel genre = new GenresModel(Integer.parseInt(genresValues.get(i)),genres[i],flagId[i]);
            genreLists.add(genre);
        }

        mView.initAdapter(genreLists);
    }

    @Override
    public List<GenresModel> getGenreLists() {
        return genreLists;
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
    public void setView(@NonNull MovieGenresView view) {
        this.mView = view;
    }

    @SuppressLint("StaticFieldLeak")
    private void loadGenres() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                GenresService moviesService = tmdb.genreService();
// Call any of the available endpoints
                try {
                    Response<GenreResults> response = moviesService.movie("en-US").execute();
                    if (response.isSuccessful()) {
                        GenreResults movie = response.body();
                        assert movie != null;

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
                mView.getAdapter().notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void openGenreDetailScreen(GenresModel genre) {
        Intent intent = new Intent(mView.getContext(), GenresDetailActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(genre);
        intent.putExtra("genre", json);
        mView.getContext().startActivity(intent);
    }


}