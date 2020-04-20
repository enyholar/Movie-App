package com.behruz.magmovie.ui.fragment.tv;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.CastInfoAdapter;
import com.behruz.magmovie.adapter.TvSeasonAdapter;
import com.behruz.magmovie.base.BaseFragment;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.internal.di.component.DaggerProjectComponent;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.presenter.PresenterView.TvDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.TvDetailView;
import com.behruz.magmovie.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.Review;
import com.uwetrottmann.tmdb2.entities.TvSeason;
import com.uwetrottmann.tmdb2.entities.TvShow;
import com.uwetrottmann.tmdb2.entities.Videos;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class TvSeasonFragment extends BaseFragment implements TvDetailView {

    private BaseTvShow movieData;

    @Inject
    TvDetailPresenter presenter;
    private RecyclerView recyclerView;
    private String json;
    private TvSeasonAdapter tvSeasonAdapter;
    private TextView txt_movie_review;

    public TvSeasonFragment() {
        // Required empty public constructor
    }

    public static TvSeasonFragment newInstance(String movieData) {
        TvSeasonFragment fragment = new TvSeasonFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INTENT_MOVIE_DETAILS, movieData);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    protected void injectInjector() {
        DaggerProjectComponent.builder()
                .projectModule(new ProjectModule(this.getActivity()))
                .build()
                .inject(this);
        presenter.setView(this);
        presenter.start();
        presenter.loadTvSeason();
    }

    @Override
    public void initModels() {

    }

    @Override
    public void initViews(View view) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_movie_review, container, false);
        recyclerView = view.findViewById(R.id.rv_movie_reviews);
        txt_movie_review = view.findViewById(R.id.txt_movie_reviews_error);
        return view;
    }

    private void initAdapter() {
//        tvSeasonAdapter = new TvSeasonAdapter(getContext());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(tvSeasonAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        readArgs();
        injectInjector();
        initAdapter();

    }

    private void readArgs() {
        Bundle args = getArguments();
        if (args != null) {
            json = args.getString(Constant.INTENT_MOVIE_DETAILS);
            Gson gson = new Gson();
            Type type = new TypeToken<BaseTvShow>() {
            }.getType();

            movieData = gson.fromJson(json, type);
        }
    }

    @Override
    public void setUpMovieDetailsToView(TvShow movie) {

    }

    @Override
    public void setUpMovieDetailsOverView(TvShow movie) {

    }

    @Override
    public void showTrailers(List<Videos.Video> trailerDatas) {

    }

    @Override
    public void showReviews(List<Review> reviewDatas) {

    }

    @Override
    public BaseTvShow getTvData() {
        return movieData;
    }

    @Override
    public TvSeason getTvDataSeason() {
        return null;
    }

    @Override
    public void hidePageReview() {
//        txt_movie_review.setVisibility(View.VISIBLE);
//        txt_movie_review.setText("No cast for this movie");
//        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showPageReview() {

    }


    @Override
    public CastInfoAdapter getCastMemberAdapter() {
        return null;
    }

    @Override
    public TvSeasonAdapter getTvSeasonAdapter() {
        return tvSeasonAdapter;
    }

    @Override
    public void showLineLoading() {

    }

    @Override
    public void hideLineLoading() {

    }

    @Override
    public void showError(String message) {

    }
}
