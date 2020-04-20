package com.behruz.magmovie.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.behruz.magmovie.ui.fragment.movietvDetail.MovieOverviewFragment;
import com.behruz.magmovie.ui.fragment.movietvDetail.TrailerFragment;
import com.behruz.magmovie.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.Review;
import com.uwetrottmann.tmdb2.entities.Videos;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Wim on 5/29/17.
 */

public class MovieDetailFragment extends BaseFragment implements MovieDetailView {

    private Toolbar toolbar;
    private ImageView imgPoster;
    private TextView tvMovieTitle;
    private TextView tvMovieDate;
    private TextView tvMovieDuration;
    private TextView tvMovieGenre;
    private TextView tvMovieHomepage;
    private TextView tvMovieOverview;
    private LinearLayout viewTrailers;
    private LinearLayout viewReviews;

    private ProgressBar pgTrailers;
    private ProgressBar pgReviews;

    private BaseMovie movieData;

    @Inject
    MovieDetailPresenter presenter;
    private ViewPager viewPager;
    private String json;
    private ImageView posterImage;

    public static MovieDetailFragment newInstance(String movieData) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INTENT_MOVIE_DETAILS, movieData);
        MovieDetailFragment detailFragment = new MovieDetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void injectInjector() {
        DaggerProjectComponent.builder()
                .projectModule(new ProjectModule(this.getActivity()))
                .build()
                .inject(this);
        presenter.setView(this);
        presenter.start();
    }

    @Override
    public void initModels() {

    }

    @Override
    public void initViews(View view) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        readArgs();
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        imgPoster = (ImageView) view.findViewById(R.id.img_poster);
        posterImage = (ImageView) view.findViewById(R.id.movie_art);
        tvMovieTitle = (TextView) view.findViewById(R.id.movie_title);
        tvMovieDate = (TextView) view.findViewById(R.id.movie_date);
        tvMovieDuration = (TextView) view.findViewById(R.id.movie_duration);
        tvMovieGenre = (TextView) view.findViewById(R.id.movie_genre);
        tvMovieHomepage = (TextView) view.findViewById(R.id.movie_homepage);
        tvMovieOverview = (TextView) view.findViewById(R.id.movie_overview);
        viewTrailers = (LinearLayout) view.findViewById(R.id.view_trailers);
        viewReviews = (LinearLayout) view.findViewById(R.id.view_reviews);

        pgTrailers = (ProgressBar) view.findViewById(R.id.pg_trailers);
        pgReviews = (ProgressBar) view.findViewById(R.id.pg_reviews);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
            viewPager.setOffscreenPageLimit(4);
        }
        setUpVideoImage();
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(MovieOverviewFragment.newInstance(json), this.getString(R.string.overview));
        adapter.addFragment(TrailerFragment.newInstance(json), this.getString(R.string.trailers));
        viewPager.setAdapter(adapter);
    }

    private void setUpVideoImage(){
        if (movieData.poster_path != null && !movieData.poster_path.isEmpty()) {
            Picasso.with(getActivity())
                    .load(Constant.IMG_URL + movieData.poster_path)
                    .into(posterImage);
        } else if (movieData.backdrop_path != null && !movieData.backdrop_path.isEmpty()) {
            Picasso.with(getActivity())
                    .load(Constant.IMG_URL + movieData.backdrop_path)
                    .into(posterImage);
        } else {
            posterImage.setImageResource(R.mipmap.ic_launcher);
        }
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.app_name);
            actionBar.setSubtitle(R.string.movie_detail);
        }

        injectInjector();

    }

    @Override
    public void setUpMovieDetailsToView(Movie movie) {
        if (movie != null) {
            if (movie.poster_path != null && !movie.poster_path.isEmpty()) {
                Picasso.with(getActivity())
                        .load(Constant.IMG_URL + movie.poster_path)
                        .into(imgPoster);
            } else if (movie.backdrop_path != null && !movie.backdrop_path.isEmpty()) {
                Picasso.with(getActivity())
                        .load(Constant.IMG_URL + movie.backdrop_path)
                        .into(imgPoster);
            } else {
                imgPoster.setImageResource(R.mipmap.ic_launcher);
            }


            tvMovieTitle.setText(movie.original_title);
            tvMovieDate.setText(movie.release_date.toString());
            tvMovieDuration.setText(movie.runtime + " Minutes");

            for (int i = 0; i < movie.genres.size(); i++) {
                Genre genre = movie.genres.get(i);

                if (i < movie.genres.size() - 1) {
                    tvMovieGenre.append(genre.name + ",");
                } else {
                    tvMovieGenre.append(genre.name);
                }
            }

            tvMovieHomepage.setText(movie.homepage);
            tvMovieOverview.setText(movie.overview);
        } else {
            Toast.makeText(getContext(), "No Data!", Toast.LENGTH_LONG).show();
        }
    }


//    private void loadTrailer(int id) {
//        pgTrailers.setVisibility(View.VISIBLE);
//
//        apiService.getTrailers(id, new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                Trailer trailer = (Trailer) response.body();
//
//                if(trailer != null) {
//                    showTrailers(trailer.getResults());
//                }else{
//                    Toast.makeText(getContext(), "No Data!", Toast.LENGTH_LONG).show();
//                }
//
//                pgTrailers.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                if(t instanceof SocketTimeoutException) {
//                    Toast.makeText(getContext(), "Request Timeout. Please try again!", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_LONG).show();
//                }
//
//                pgTrailers.setVisibility(View.GONE);
//            }
//        });
//    }


    @Override
    public void showTrailers(List<Videos.Video> trailerDatas) {
        viewTrailers.removeAllViews();

        for (int i = 0; i < trailerDatas.size(); i++) {

            final Videos.Video trailerData = trailerDatas.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trailer, viewTrailers, false);

//            ImageView trailerThumb = (ImageView) view.findViewById(R.id.trailer_thumb);
//            TextView trailerName = (TextView) view.findViewById(R.id.trailer_name);

//            if (trailerData.site.equalsIgnoreCase("youtube")) {
//                Picasso.with(getContext())
//                        .load("http://img.youtube.com/vi/" + trailerData.key + "/default.jpg")
//                        .into(trailerThumb);
//            }

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    presenter.openTrailerPlayerScreen(trailerData.name,trailerData.key);
//                       //watchYoutubeVideo(trailerData.key);
//                }
//            });
//
//
//            trailerName.setText(trailerData.name);
            viewTrailers.addView(view);
        }
    }

    //
    public void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
//
    @Override
    public void showReviews(List<Review> reviewDatas) {
        viewReviews.removeAllViews();

        for (int i = 0; i < reviewDatas.size(); i++) {

            Review reviewData = reviewDatas.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_review, viewReviews, false);

            TextView reviewers = (TextView) view.findViewById(R.id.reviewers);
            TextView content = (TextView) view.findViewById(R.id.content);

            reviewers.setText(reviewData.author);
            content.setText(reviewData.content.length() > 100 ?
                    reviewData.content.substring(0, 100) + "..." : reviewData.content);

            viewReviews.addView(view);
        }
    }

    @Override
    public BaseMovie getMovieData() {
        return movieData;
    }

    @Override
    public void showLineLoading() {

    }

    @Override
    public void hidePageReview() {
        pgReviews.setVisibility(View.GONE);
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
        pgReviews.setVisibility(View.VISIBLE);
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
    public void hideLineLoading() {

    }

    @Override
    public void showError(String message) {

    }
}
