package com.behruz.magmovie.ui.fragment.movietvDetail;

import android.annotation.SuppressLint;
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
import com.behruz.magmovie.ui.activity.VideosActivity;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
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

public class MovieDetailNewFragment extends BaseFragment implements MovieDetailView {

    private Toolbar toolbar;
    private BaseMovie movieData;

    @Inject
    MovieDetailPresenter presenter;
    private ViewPager viewPager;
    private String json;
    private ImageView videoImage;
    private TextView txtRating,txtDate,txtDuration;

    public static MovieDetailNewFragment newInstance(String movieData) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INTENT_MOVIE_DETAILS, movieData);
        MovieDetailNewFragment detailFragment = new MovieDetailNewFragment();
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
        presenter.loadMovieDetails();
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
        View view = inflater.inflate(R.layout.layout_movie_series_screen, container, false);
        readArgs();
        toolbar = (Toolbar) view.findViewById(R.id.toolbar2);
        //collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.ct_movie_details);
       // posterImage = (ImageView) view.findViewById(R.id.img_movie);
        videoImage = (ImageView) view.findViewById(R.id.img_trailer);
        txtDate = view.findViewById(R.id.txt_date);
        txtDuration = view.findViewById(R.id.txt_time);
        txtRating = view.findViewById(R.id.txt_rating);
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
     //   adapter.addFragment(MovieReviewActivity.newInstance(json), this.getString(R.string.reviews));
        adapter.addFragment(MovieCastFragment.newInstance(json), this.getString(R.string.cast));
        adapter.addFragment(TrailerFragment.newInstance(json), this.getString(R.string.trailers));
        viewPager.setAdapter(adapter);
    }

    @SuppressLint("CheckResult")
    private void setUpVideoImage(){
        Picasso.with(getContext())
                .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + movieData.backdrop_path)
                .placeholder(R.drawable.ic_launcher_background)
                .fit().centerCrop()
                .noFade()
                .error(R.drawable.ic_launcher_background)
                .into(videoImage);

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
            actionBar.setTitle("");
            actionBar.setSubtitle("");
        }

        injectInjector();

    }

    @Override
    public void setUpMovieDetailsToView(Movie movie) {
        if (movie != null){
            txtDuration.setText(StringUtils.formatRuntime(getContext(), movie.runtime));
            txtRating.setText(movie.vote_average.toString() + " /10");
            txtDate.setText(StringUtils.formatReleaseDate(movie.release_date));
        }
    }




    @Override
    public void showTrailers(List<Videos.Video> trailerDatas) {
        Videos.Video video = null;
        String json = null;
        if (trailerDatas != null && trailerDatas.size() > 0){
            Gson gson = new Gson();
            json = gson.toJson(trailerDatas);
             video = trailerDatas.get(0);
            Picasso.with(getContext())
                    .load(String.format(Constant.YOUTUBE_THUMBNAIL_URL, video.key))
                    .fit().centerCrop()
                    .into(videoImage);
        }
        Videos.Video finalVideo1 = video;
        String finalJson = json;
        videoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = String.format(Constant.YOUTUBE_EMBED_VIDEO_URL, finalVideo1.key);
//                Intent intent = VideoActivity.newIntent(getContext(), url);
//                startActivity(intent);

                Intent intent = VideosActivity.newIntent(getActivity(), finalJson);
                startActivity(intent);
            }
        });
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
//        viewReviews.removeAllViews();
//
//        for (int i = 0; i < reviewDatas.size(); i++) {
//
//            Review reviewData = reviewDatas.get(i);
//            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_review, viewReviews, false);
//
//            TextView reviewers = (TextView) view.findViewById(R.id.reviewers);
//            TextView content = (TextView) view.findViewById(R.id.content);
//
//            reviewers.setText(reviewData.author);
//            content.setText(reviewData.content.length() > 100 ?
//                    reviewData.content.substring(0, 100) + "..." : reviewData.content);
//
//            viewReviews.addView(view);
//        }
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
  //      pgReviews.setVisibility(View.GONE);
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
 //       pgReviews.setVisibility(View.VISIBLE);
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
