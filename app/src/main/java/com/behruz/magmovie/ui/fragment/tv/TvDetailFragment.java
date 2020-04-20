package com.behruz.magmovie.ui.fragment.tv;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.behruz.magmovie.adapter.TvSeasonAdapter;
import com.behruz.magmovie.base.BaseFragment;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.internal.di.component.DaggerProjectComponent;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.presenter.PresenterView.TvDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.TvDetailView;
import com.behruz.magmovie.ui.activity.VideosActivity;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Wim on 5/29/17.
 */

public class TvDetailFragment extends BaseFragment implements TvDetailView {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private String json;
    private ImageView videoImage;
    private TextView txtRating, txtDate, txtDuration;

    private BaseTvShow movieData;

    @Inject
    TvDetailPresenter presenter;
    private TabLayout tabLayout;

    public static TvDetailFragment newInstance(String movieData) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INTENT_MOVIE_DETAILS, movieData);
        TvDetailFragment detailFragment = new TvDetailFragment();
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
        presenter.loadTrailers();
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
        toolbar = (Toolbar) view.findViewById(R.id.toolbar2);
        videoImage = (ImageView) view.findViewById(R.id.img_trailer);
        txtDate = view.findViewById(R.id.txt_date);
        txtDuration = view.findViewById(R.id.txt_time);
        txtRating = view.findViewById(R.id.txt_rating);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        return view;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.app_name);
            actionBar.setSubtitle(R.string.movie_detail);
        }
        readArgs();
        if (viewPager != null) {
            setupViewPager(viewPager);
            viewPager.setOffscreenPageLimit(4);
        }
        setUpVideoImage();
        tabLayout.setupWithViewPager(viewPager);
        injectInjector();

    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(TvOverviewFragment.newInstance(json), this.getString(R.string.overview));
        adapter.addFragment(TvSeasonFragment.newInstance(json), this.getString(R.string.seasons));
     //   adapter.addFragment(AllCastActivity.newInstance(json), this.getString(R.string.cast));
        viewPager.setAdapter(adapter);
    }

    @SuppressLint("CheckResult")
    private void setUpVideoImage() {
        Picasso.with(getContext())
                .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movieData.backdrop_path)
                .placeholder(R.drawable.ic_launcher_background)
                .fit().centerCrop()
                .noFade()
                .error(R.drawable.ic_launcher_background)
                .into(videoImage);

    }


    @Override
    public void setUpMovieDetailsToView(TvShow movie) {
            if (movie != null) {
                txtDuration.setText(StringUtils.formatRuntime(getContext(), movie.episode_run_time.get(0)));
                txtRating.setText(movie.vote_average.toString() + " /10");
                txtDate.setText(StringUtils.formatReleaseDate(movie.first_air_date));
            }
    }

    @Override
    public void setUpMovieDetailsOverView(TvShow movie) {

    }


    @Override
        public void showTrailers (List < Videos.Video > trailerDatas) {
            Videos.Video video = null;
            String json = null;
            if (trailerDatas != null && trailerDatas.size() > 0) {
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


        @Override
        public void showReviews (List < Review > reviewDatas) {

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


        @Override
        public BaseTvShow getTvData () {
            return movieData;
        }

    @Override
    public TvSeason getTvDataSeason() {
        return null;
    }

    @Override
        public void showLineLoading () {

        }

        @Override
        public void hidePageReview () {
        }

        @Override
        public void showPageReview () {
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
        public void hideLineLoading () {

        }

        @Override
        public void showError (String message){

        }
    }

