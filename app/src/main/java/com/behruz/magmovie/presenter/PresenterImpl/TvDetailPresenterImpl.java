package com.behruz.magmovie.presenter.PresenterImpl;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.behruz.magmovie.internal.ProgressBarHandler;
import com.behruz.magmovie.presenter.PresenterView.TvDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.TvDetailView;
import com.behruz.magmovie.utils.ConnectionUtil;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.AppendToResponse;
import com.uwetrottmann.tmdb2.entities.CastMember;
import com.uwetrottmann.tmdb2.entities.TvSeason;
import com.uwetrottmann.tmdb2.entities.TvShow;
import com.uwetrottmann.tmdb2.entities.Videos;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.uwetrottmann.tmdb2.services.TvService;

import java.util.List;

import retrofit2.Response;

/**
 * Created by ENNY on 2/20/2018.
 */

public class TvDetailPresenterImpl implements TvDetailPresenter {
    private TvDetailView mView;
    private ProgressBarHandler progressBarHandler;
    private TvShow tvShow;
    public List<TvSeason> seasons;
    private List<Videos.Video> trailerList;
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
    public void setView(@NonNull TvDetailView view) {
        this.mView = view;
    }

//    private void loadData() {
//        if (ConnectionUtil.isConnected(mView.getContext())) {
//            if (mView.getTvData() != null) {
//                loadMovieDetails();
//                loadTvSeason();
//                loadCastMember();
//            }
//        } else {
//            Toast.makeText(mView.getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
//        }
//    }

    private void loadData() {
        if (ConnectionUtil.isConnected(mView.getContext())) {
            loadMovieDetails();
        } else {
            if (preferenUtil.getTvDetail(String.valueOf(mView.getTvData().id)) != null){
                mView.setUpMovieDetailsToView(preferenUtil.getTvDetail(String.valueOf(mView.getTvData().id)));
            }else {
                Toast.makeText(mView.getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadMovieDetails() {
        //TvShow tvShow = null;
//        if (mView.getTvDataSeason() != null){
//            new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected void onPreExecute() {
//                    super.onPreExecute();
//                    mView.showLineLoading();
//                }
//
//                @Override
//                protected Void doInBackground(final Void... unused) {
//                    Tmdb tmdb = new Tmdb(Constant.API_KEYS);
//                    TvService tvService = tmdb.tvService();
//                    AppendToResponse appendToResponse = new AppendToResponse(AppendToResponseItem.values());
//                    int id = mView.getTvDataSeason().id;
//
//// Call any of the available endpoints
//                    try {
//                        Response<TvShow> response = tvService.tv(id, "en-US",appendToResponse).execute();
//                        if (response.isSuccessful()) {
//                            //    reviewArrayList = response.body().;
//                            assert response.body() != null;
//                            tvShow = response.body();
//                            preferenUtil.saveTvDetail(tvShow,String.valueOf(mView.getTvData().id));
//
//                        } else {
//                            System.out.println(" is awesome!");
//                        }
//                    } catch (Exception e) {
//                        System.out.println(e.toString() + " is awesome!");
//                        // see execute() javadoc
//                    }
//                    return null;
//                }
//
//                @Override
//                protected void onPostExecute(Void aVoid) {
//                    mView.hideLineLoading();
//                    if (tvShow != null){
//                        mView.setUpMovieDetailsToView(tvShow);
//                    }
//                }
//            }.execute();
//        }else {
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
                    AppendToResponse appendToResponse = new AppendToResponse(AppendToResponseItem.values());

// Call any of the available endpoints
                    try {
                        Response<TvShow> response = tvService.tv(mView.getTvData().id, "en-US",appendToResponse).execute();
                        if (response.isSuccessful()) {
                            //    reviewArrayList = response.body().;
                            assert response.body() != null;
                            tvShow = response.body();
                            preferenUtil.saveTvDetail(tvShow,String.valueOf(mView.getTvData().id));

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
                    if (tvShow != null){
                        mView.setUpMovieDetailsToView(tvShow);
                    }
                }
            }.execute();
  //      }

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadTvSeason() {
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
                AppendToResponse appendToResponse = new AppendToResponse(AppendToResponseItem.values());

// Call any of the available endpoints
                try {
                    Response<TvShow> response = tvService.tv(mView.getTvData().id, "en-US",appendToResponse).execute();
                    if (response.isSuccessful()) {
                        tvShow = response.body();
                        assert response.body() != null;
                        seasons = response.body().seasons;

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
                if (seasons != null && seasons.size() > 0) {
                    mView.getTvSeasonAdapter().addAll(seasons);
                }
                mView.hideLineLoading();

            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadCastMember() {
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
                AppendToResponse appendToResponse = new AppendToResponse(AppendToResponseItem.values());

// Call any of the available endpoints
                try {
                    Response<TvShow> response = tvService.tv(mView.getTvData().id, "en-US",appendToResponse).execute();
                    if (response.isSuccessful()) {
                        tvShow = response.body();
                        assert response.body() != null;
                        castMemberList = response.body().credits.cast;

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
                if (castMemberList != null && castMemberList.size() > 0) {
                    mView.getCastMemberAdapter().addAll(castMemberList);
                }
                mView.hideLineLoading();

            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadTrailers() {
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
                AppendToResponse appendToResponse = new AppendToResponse(AppendToResponseItem.values());

// Call any of the available endpoints
                try {
                    Response<TvShow> response = tvService.tv(mView.getTvData().id, "en-US",appendToResponse).execute();
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        tvShow = response.body();
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
                mView.setUpMovieDetailsToView(tvShow);
                if (trailerList != null && trailerList.size() > 0) {
                    mView.showTrailers(trailerList);
                }
                mView.hideLineLoading();

            }
        }.execute();
    }


}