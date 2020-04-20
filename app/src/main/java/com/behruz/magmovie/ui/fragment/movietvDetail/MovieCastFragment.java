package com.behruz.magmovie.ui.fragment.movietvDetail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.CastInfoAdapter;
import com.behruz.magmovie.adapter.MovieReviewAdapter;
import com.behruz.magmovie.adapter.MovieSimilarAdapter;
import com.behruz.magmovie.adapter.MovieTrailerAdapter;
import com.behruz.magmovie.base.BaseFragment;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.internal.di.component.DaggerProjectComponent;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.presenter.PresenterView.MovieDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.MovieDetailView;
import com.behruz.magmovie.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.Review;
import com.uwetrottmann.tmdb2.entities.Videos;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class MovieCastFragment extends BaseFragment implements MovieDetailView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressBar pgReviews;

    private BaseMovie movieData;

    @Inject
    MovieDetailPresenter presenter;
    private RecyclerView recyclerView;
    private String json;
    private CastInfoAdapter castAdapter;
    private TextView txt_movie_review;

    public MovieCastFragment() {
        // Required empty public constructor
    }

    public static MovieCastFragment newInstance(String movieData) {
        MovieCastFragment fragment = new MovieCastFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        presenter.loadMovieDetails();
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
        castAdapter = new CastInfoAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(castAdapter);
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
            Type type = new TypeToken<BaseMovie>() {
            }.getType();

            movieData = gson.fromJson(json, type);
        }
    }

    @Override
    public void setUpMovieDetailsToView(Movie movie) {

    }

        @Override
    public void showTrailers(List<Videos.Video> trailerDatas) {

    }

    @Override
    public void showReviews(List<Review> reviewDatas) {

    }

    @Override
    public BaseMovie getMovieData() {
        return movieData;
    }

    @Override
    public void hidePageReview() {
//        txt_movie_review.setVisibility(View.VISIBLE);
//        txt_movie_review.setText("No cast for this movie");
//        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void initExpandLayout() {

    }

    @Override
    public void showSimilarAvailabilityVideo() {

    }

    @Override
    public void initSimilarExpandLayout() {

    }

    @Override
    public void showPageReview() {

    }

    @Override
    public MovieSimilarAdapter getSimilarAdapter() {
        return null;
    }

    @Override
    public MovieReviewAdapter getReviewAdapter() {
        return null;
    }

    @Override
    public CastInfoAdapter getCastMemberAdapter() {
        return castAdapter;
    }


    @Override
    public MovieTrailerAdapter getTrailerAdapter() {
        return null;
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
