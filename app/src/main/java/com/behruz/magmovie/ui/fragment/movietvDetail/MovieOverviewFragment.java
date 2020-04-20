package com.behruz.magmovie.ui.fragment.movietvDetail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.behruz.magmovie.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.Review;
import com.uwetrottmann.tmdb2.entities.Videos;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieOverviewFragment extends BaseFragment implements MovieDetailView {
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
    private TextView txt_movie_detail_title;
    private TextView txt_movie_rating;
    private TextView txt_movie_overview;
    private TextView txt_movie_tagline,txt_movie_genres,txt_movie_review,txt_movie_similar;
    private String json;
    private RecyclerView rvReview;
    private MovieReviewAdapter reviewAdapter;
    private LinearLayout lnExpand,lnSimilarExpand;
//   private ExpandableLinearLayout expandableLinearLayout,expandableSimilarLinearLayout;
   private ImageView img_drop_down,img_drop_down_similar;
    private MovieSimilarAdapter similarMovieAdapter;
    private RecyclerView rvSimilar;
    private LinearLayoutManager horizontalLayoutManager;

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


    public MovieOverviewFragment() {
        // Required empty public constructor
    }

    public static MovieOverviewFragment newInstance(String movieData) {
        MovieOverviewFragment fragment = new MovieOverviewFragment();
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
//        txt_movie_detail_title = (TextView) view.findViewById(R.id.txt_movie_detail_title);
//        txt_movie_rating = (TextView) view.findViewById(R.id.txt_movie_rating);
//        txt_movie_overview = (TextView) view.findViewById(R.id.txt_movie_overview);
//        txt_movie_tagline = (TextView) view.findViewById(R.id.txt_movie_tagline);
//        txt_movie_genres = (TextView) view.findViewById(R.id.txt_movie_genres);
//        txt_movie_review= view.findViewById(R.id.no_review);
     //   txt_movie_similar = view.findViewById(R.id.no_similar);
//        lnExpand = view.findViewById(R.id.view_reviews);
//        expandableLinearLayout = view.findViewById(R.id.expandableLayout);
//        img_drop_down = view.findViewById(R.id.img_expanable);
//        lnExpand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                expandableLinearLayout.toggle();
//            }
//        });
//        new ExpandableViewAnimator(expandableLinearLayout, img_drop_down);

//        lnSimilarExpand = view.findViewById(R.id.view_similar);
//        expandableSimilarLinearLayout = view.findViewById(R.id.expandableLayoutSimilar);
//        img_drop_down_similar = view.findViewById(R.id.img_expanable_similar);
//        lnSimilarExpand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                expandableSimilarLinearLayout.toggle();
//            }
//        });
//        new ExpandableViewAnimator(expandableSimilarLinearLayout, img_drop_down_similar);

 //       rvReview = view.findViewById(R.id.rv_movie_reviews);
  //      rvSimilar =  view.findViewById(R.id.rv_movie_similar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        readArgs();
        injectInjector();
  //      initAdapter();
//        initSimilarAdapter();

    }

    private void initAdapter() {
        reviewAdapter = new MovieReviewAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvReview.setLayoutManager(linearLayoutManager);
        rvReview.setAdapter(reviewAdapter);
    }

    private void initSimilarAdapter() {
        similarMovieAdapter = new MovieSimilarAdapter(getContext());
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
       // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvSimilar.setLayoutManager(horizontalLayoutManager);
        rvSimilar.setAdapter(similarMovieAdapter);
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
        if (movie != null){
//            txt_movie_overview.setText(movie.overview);
//            txt_movie_tagline.setText(movie.tagline);
//            txt_movie_rating.setText(movie.vote_average.toString());
//            txt_movie_detail_title.setText(movie.original_title);
//            for (int i = 0; i < movie.genres.size(); i++) {
//                Genre genre = movie.genres.get(i);
//
//                if (i < movie.genres.size() - 1) {
//                    txt_movie_genres.append(genre.name + ",");
//                } else {
//                    txt_movie_genres.append(genre.name);
//                }
//            }


            Picasso.with(getContext())
                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movie.poster_path)
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit().centerCrop()
                    .error(R.drawable.ic_launcher_background)
                    .into(poster);
            setTitle(movie.title);
            name.setText(movie.title);
            originalName.setText(getString(R.string.details_movie_name, movie.original_title, StringUtils.getYear(movie.release_date)));
            genres.setText(StringUtils.getGenres(movie.genres));
            productionCountries.setText(StringUtils.getProductCountries(movie.production_countries));
            runtime.setText(StringUtils.formatRuntime(getContext(), movie.runtime));
            tagline.setText(getString(R.string.details_tagline, movie.tagline));
            overview.setText(movie.overview);
            releaseDate.setText(StringUtils.formatReleaseDate(movie.release_date));
            status.setText(movie.status.value);
            homepage.setText(movie.homepage);
            budget.setText(getString(R.string.details_budget_revenue, movie.budget));
            revenue.setText(getString(R.string.details_budget_revenue, movie.revenue));
        }
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
//        txt_movie_review.setText("No review for this movie");
//        rvReview.setVisibility(View.GONE);
    }

    @Override
    public void initExpandLayout() {
//        expandableLinearLayout.initLayout();
//        expandableLinearLayout.expand();
    }

    @Override
    public void showSimilarAvailabilityVideo() {

//        txt_movie_similar.setVisibility(View.VISIBLE);
//        txt_movie_similar.setText("No similar for this movie");
//        rvSimilar.setVisibility(View.GONE);
    }

    @Override
    public void initSimilarExpandLayout() {
//        expandableSimilarLinearLayout.initLayout();
//        expandableSimilarLinearLayout.expand();
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
        return null;
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
