package com.behruz.magmovie.ui.fragment.tv;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.CastInfoAdapter;
import com.behruz.magmovie.adapter.MovieReviewAdapter;
import com.behruz.magmovie.adapter.MovieSimilarAdapter;
import com.behruz.magmovie.adapter.TvSeasonAdapter;
import com.behruz.magmovie.base.BaseFragment;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.internal.di.component.DaggerProjectComponent;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.presenter.PresenterView.TvDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.TvDetailView;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.Review;
import com.uwetrottmann.tmdb2.entities.TvSeason;
import com.uwetrottmann.tmdb2.entities.TvShow;
import com.uwetrottmann.tmdb2.entities.Videos;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvOverviewFragment extends BaseFragment implements TvDetailView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressBar pgReviews;

    private BaseTvShow movieData;

    @Inject
    TvDetailPresenter presenter;
    private String json;
    private MovieReviewAdapter reviewAdapter;
    private MovieSimilarAdapter similarMovieAdapter;

    @BindView(R.id.page_movie_details)
    NestedScrollView contentView;
    @BindView(R.id.movie_details_poster) ImageView poster;
    @BindView(R.id.movie_details_name) TextView name;
    @BindView(R.id.movie_details_original_name) TextView originalName;
    @BindView(R.id.movie_details_genres) TextView genres;
    @BindView(R.id.movie_details_countries) TextView productionCountries;
    @BindView(R.id.movie_details_runtime) TextView runtime;
    @BindView(R.id.movie_details_tagline) TextView tagline;
    @BindView(R.id.movie_details_overview) TextView overview;
    @BindView(R.id.movie_details_release_date) TextView releaseDate;
    @BindView(R.id.movie_details_status) TextView status;
    @BindView(R.id.movie_details_homepage) TextView homepage;
    @BindView(R.id.movie_details_budget) TextView budget;
    @BindView(R.id.movie_details_revenue) TextView revenue;

    @BindView(R.id.progressBar)
    AVLoadingIndicatorView progressBar;


    public TvOverviewFragment() {
        // Required empty public constructor
    }

    public static TvOverviewFragment newInstance(String movieData) {
        TvOverviewFragment fragment = new TvOverviewFragment();
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
        View view = inflater.inflate(R.layout.layout_movie_details_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        readArgs();
        injectInjector();
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
        if (movie != null){
            Picasso.with(getContext())
                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movie.poster_path)
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit().centerCrop()
                    .error(R.drawable.ic_launcher_background)
                    .into(poster);
            setTitle(movie.name);
            name.setText(movie.original_name);
            originalName.setText(getString(R.string.details_movie_name, movie.original_name, StringUtils.getYear(movie.first_air_date)));
            genres.setText(StringUtils.getGenres(movie.genres));
            productionCountries.setText(StringUtils.getTvProductCountries(movie.origin_country));
            runtime.setText(StringUtils.formatRuntime(getContext(), movie.episode_run_time.get(0)));
         //   tagline.setText(getString(R.string.details_tagline, movie.));
            overview.setText(movie.overview);
            releaseDate.setText(StringUtils.formatReleaseDate(movie.first_air_date));
            status.setText(movie.status);
            homepage.setText(movie.homepage);
//            budget.setText(getString(R.string.details_budget_revenue, movie));
//            revenue.setText(getString(R.string.details_budget_revenue, movie));
        }
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
//        txt_movie_review.setText("No review for this movie");
//        rvReview.setVisibility(View.GONE);
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
