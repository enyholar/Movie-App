package com.behruz.magmovie.presenter.PresenterImpl;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.behruz.magmovie.internal.ProgressBarHandler;
import com.behruz.magmovie.presenter.PresenterView.MovieDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.MovieDetailView;
import com.behruz.magmovie.ui.activity.PlayTestActivity;
import com.behruz.magmovie.utils.ConnectionUtil;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.AppendToResponse;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.CastMember;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.Review;
import com.uwetrottmann.tmdb2.entities.ReviewResultsPage;
import com.uwetrottmann.tmdb2.entities.Videos;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.uwetrottmann.tmdb2.services.MoviesService;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Response;

/**
 * Created by ENNY on 2/20/2018.
 */

public class MovieDetailPresenterImpl implements MovieDetailPresenter {
    private MovieDetailView mView;
    private ProgressBarHandler progressBarHandler;
    private Movie movie;
    private List<Review> reviewArrayList;
    private List<Videos.Video> trailerList;
    private List<BaseMovie> similarList;
    private List<CastMember> castMemberList;
    private String error;
    private PreferenUtil preferenUtil;


    @Override
    public void start() {
        progressBarHandler = new ProgressBarHandler(mView.getContext());
        preferenUtil = PreferenUtil.getInstant(mView.getContext());
        loadData();
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
    public void setView(@NonNull MovieDetailView view) {
        this.mView = view;
    }

    private void loadData() {
        if (ConnectionUtil.isConnected(mView.getContext())) {
            loadMovieDetails();
        } else {
            if (preferenUtil.getMovieDetail(String.valueOf(mView.getMovieData().id)) != null){
                mView.setUpMovieDetailsToView(preferenUtil.getMovieDetail(String.valueOf(mView.getMovieData().id)));
            }else {
                Toast.makeText(mView.getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadMovieDetails() {
   //     Movie movies = null;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.showLineLoading();
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                MoviesService moviesService = tmdb.moviesService();
                AppendToResponse appendToResponse = new AppendToResponse(AppendToResponseItem.values());
// Call any of the available endpoints
                try {
                    Response<Movie> response = moviesService.summary(mView.getMovieData().id, "en-US",appendToResponse).execute();
                    if (response.isSuccessful()) {
                        movie = response.body();
                        preferenUtil.saveMovieDetail(movie,String.valueOf(mView.getMovieData().id));
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
                assert movie != null;
                mView.setUpMovieDetailsToView(movie);
                mView.hideLineLoading();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadReviewDetail() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.showPageReview();
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                MoviesService reviewService = tmdb.moviesService();
                try {
                    Response<ReviewResultsPage> response = reviewService.reviews(mView.getMovieData().id, 1, "en-US").execute();
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        reviewArrayList = response.body().results;

                    } else {
                        System.out.println(" is awesome!");
                    }
                } catch (Exception e) {
                    if (e instanceof SocketTimeoutException) {
                        error = "Request Timeout. Please try again!";
                    } else {
                        error = "Connection Error!";
                    }
                    // see execute() javadoc
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (reviewArrayList != null && reviewArrayList.size() > 0) {
                    mView.showReviews(reviewArrayList);
                }
                mView.hidePageReview();
                if (error != null) {
                    Toast.makeText(mView.getContext(), error, Toast.LENGTH_LONG).show();
                }

            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadTrailerDetail() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(final Void... unused) {

                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                MoviesService moviesService = tmdb.moviesService();
                AppendToResponse appendToResponse = new AppendToResponse(AppendToResponseItem.values());
// Call any of the available endpoints
                try {
                    Response<Movie> response = moviesService.summary(mView.getMovieData().id, "en-US",appendToResponse).execute();
                    if (response.isSuccessful()) {
                        movie = response.body();
                        assert response.body() != null;
                     trailerList = response.body().videos.results;

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
                if (trailerList != null && trailerList.size() > 0) {

                }
//

            }
        }.execute();
    }


    @Override
    public void openTrailerPlayerScreen(String name, String key){
        Intent intent = new Intent(mView.getContext(), PlayTestActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("key",key);
        mView.getContext().startActivity(intent);
    }


}