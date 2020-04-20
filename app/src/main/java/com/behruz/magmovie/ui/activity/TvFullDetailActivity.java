package com.behruz.magmovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.CastInfoAdapter;
import com.behruz.magmovie.adapter.ImageMovieListAdapter;
import com.behruz.magmovie.adapter.MovieTrailerAdapter;
import com.behruz.magmovie.adapter.TrailerListner;
import com.behruz.magmovie.adapter.TvSeasonAdapter;
import com.behruz.magmovie.adapter.TvSeriesListner;
import com.behruz.magmovie.base.BaseActionbarActivity;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.internal.di.component.DaggerProjectComponent;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.presenter.PresenterView.TvDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.TvDetailView;
import com.behruz.magmovie.ui.fragment.tv.AllCastActivity;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.CrewMember;
import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.Review;
import com.uwetrottmann.tmdb2.entities.TvSeason;
import com.uwetrottmann.tmdb2.entities.TvShow;
import com.uwetrottmann.tmdb2.entities.Videos;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class TvFullDetailActivity extends BaseActionbarActivity implements TvDetailView {
    @Inject
    TvDetailPresenter presenter;
    private String baseMovie;
    private BaseTvShow movieData;
    private TvSeason tvSeasonSeries;
    private TextView txtRating,txt_review_count,txt_Title,txt_Year,txt_Genres,txt_Duration,
            txt_storyLine,txt_writer,txt_stars,txt_director,txt_image;
    private Button castViewAll,trailerViewAll,imageViewAll;
    private SimpleRatingBar ratingStarCount;
    private RecyclerView recyclerViewCast,recyclerViewImage,recyclerViewTrailer,recyclerViewTvSeason;
    private ImageView trailerBackDrop;
    private CastInfoAdapter castAdapter;
    private MovieTrailerAdapter trailerAdapter;
    private ImageMovieListAdapter imageAdapter;
    private  TvSeasonAdapter tvSeasonAdapter;
    private AVLoadingIndicatorView progressBar;
    private Toolbar toolbar;
  //  private TextView txt_Review;
    private TvShow globalMovie;

    private RelativeLayout relTrailer,relImage,relSeason;
    private String baseTvMovie;

    @Override
    public void initView() {
        toolbar= findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
//        if (getTvDataSeason() != null){
//            getSupportActionBar().setTitle(getTvDataSeason().name);
//        }else {
            getSupportActionBar().setTitle(movieData.name);
      //  }

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        txtRating = findViewById(R.id.txt_rate);
        txt_review_count = findViewById(R.id.txt_review_count);
        txt_Title = findViewById(R.id.mTitle);
        txt_Year = findViewById(R.id.mYear);
        txt_Genres = findViewById(R.id.mGenres);
        txt_Duration = findViewById(R.id.txt_min);
        txt_storyLine = findViewById(R.id.mStoryLine);
        txt_image = findViewById(R.id.appCompatTextView6);
        txt_writer = findViewById(R.id.mWriter);
        txt_stars = findViewById(R.id.mStars);
    //    txt_Review = findViewById(R.id.txt);
        txt_director = findViewById(R.id.mDirector);
        ratingStarCount = findViewById(R.id.rating_rating_bar);
        castViewAll = findViewById(R.id.cast_view_all);
        trailerViewAll = findViewById(R.id.trailer_view_all);
        imageViewAll = findViewById(R.id.images_view_all);
        recyclerViewCast = findViewById(R.id.rec_cast);
        recyclerViewImage = findViewById(R.id.rec_image);
        recyclerViewTrailer = findViewById(R.id.rec_trailer);
        recyclerViewTvSeason = findViewById(R.id.rec_series_season);
        trailerBackDrop = findViewById(R.id.img_trailer);
        relTrailer = findViewById(R.id.rel_trailer);
        relImage = findViewById(R.id.rel_image);
        relSeason = findViewById(R.id.rel_season);
        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progreesbar_home_fragment);
        castViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = gson.toJson(globalMovie.credits);
                Intent intent = AllCastActivity.newIntent(TvFullDetailActivity.this, json);
                startActivity(intent);
            }
        });

        trailerViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (globalMovie != null && globalMovie.videos != null && globalMovie.videos.results != null
                        && globalMovie.videos.results.size() > 0){
                    Gson gson = new Gson();
                    String json = gson.toJson(globalMovie.videos.results);
                    Intent intent = VideosActivity.newIntent(TvFullDetailActivity.this, json);
                    startActivity(intent);
                }

            }
        });


        imageViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void setUpMovieDetailsOverView(TvShow movie) {

    }

    @Override
    public void setUpMovieDetailsToView(TvShow movie) {
        if (movie != null){
            globalMovie = movie;
            setUpVideoImage(movie);
            ratingStarCount.setRating(Float.valueOf(String.valueOf(movie.vote_average)));
            txtRating.setText(String.valueOf(movie.vote_average) + " Rating");
           // txt_review_count.setText(String.valueOf(movie.reviews.total_results));
            txt_Title.setText(movie.original_name);
          //  txtDate.setText(StringUtils.formatReleaseDate(movie.first_air_date));
            txt_Year.setText(StringUtils.getYear(movie.first_air_date));
            if (movie.genres != null && movie.genres.size() > 0){
                for (int i = 0; i < movie.genres.size(); i++) {
                    Genre genre = movie.genres.get(i);
                    if (i < movie.genres.size() - 1) {
                        txt_Genres.append(genre.name + ",");
                    } else {
                        txt_Genres.append(genre.name);
                    }
                }
            }

            txt_Duration.setText(movie.episode_run_time.get(0) + " Minutes");
            txt_storyLine.setText(movie.overview);
           // txt_writer.setText(movie.belongs_to_collection);
//            if (movie.credits.guest_stars != null && movie.credits.guest_stars.size() > 0){
//                for (int i = 0; i < movie.credits.guest_stars.size(); i++) {
//                    CastMember castMember = movie.credits.guest_stars.get(i);
//                    if (i < movie.credits.guest_stars.size() - 1) {
//                        txt_stars.append(castMember.name + ",");
//                    } else {
//                        txt_stars.append(castMember.name);
//                    }
//                }
//            }

            if (movie.credits.crew != null && movie.credits.crew.size() > 0){
                for (int i = 0; i < movie.credits.crew.size(); i++) {
                    CrewMember castMember = movie.credits.crew.get(i);

                    if (castMember.job.equals("Director")){
                        txt_director.setText(castMember.name);
                        break;
                    }

                }
            }


            if (movie.credits.crew != null && movie.credits.crew.size() > 0){
                for (int i = 0; i < movie.credits.crew.size(); i++) {
                    CrewMember castMember = movie.credits.crew.get(i);
                    if (castMember.job.equals("Screenplay")){
                        txt_writer.setText(castMember.name);
                        break;
                    }

                }
            }


            if (movie.credits.crew != null && movie.credits.crew.size() > 0){
                for (int i = 0; i < movie.credits.crew.size(); i++) {
                    CrewMember castMember = movie.credits.crew.get(i);
                    if (castMember.job.equals("Producer")){
                        txt_stars.setText(castMember.name);
                        break;
                    }
                }
            }
            if (movie.credits.cast != null && movie.credits.cast.size() > 0) {
                castAdapter.addAll(movie.credits.cast);
            }

            if (movie.seasons != null && movie.seasons.size() > 0) {
                tvSeasonAdapter.addAll(movie.seasons);
            }else {
                relSeason.setVisibility(View.GONE);
                recyclerViewTvSeason.setVisibility(View.GONE);
            }

            if (movie.images != null && movie.images.posters != null) {
                imageAdapter.addAll(movie.images.posters);
            }else {
                recyclerViewImage.setVisibility(View.GONE);
                relImage.setVisibility(View.GONE);
                imageViewAll.setVisibility(View.GONE);
                txt_image.setVisibility(View.GONE);
            }

//            else if (movie.images != null && movie.images.backdrops != null) {
//                imageAdapter.addAll(movie.images.backdrops);
//            }else if (movie.images != null && movie.images.stills != null) {
//                imageAdapter.addAll(movie.images.stills);
        //    }

            if (movie.videos.results != null && movie.videos.results.size() > 0) {
                trailerAdapter.addAll(movie.videos.results);
            }else {
                relTrailer.setVisibility(View.GONE);
                recyclerViewTrailer.setVisibility(View.GONE);
            }
        //    txt_director.setText(movie.overview);
        }

    }

    private void initCastAdapter(){
        castAdapter = new CastInfoAdapter(getContext());
        recyclerViewCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCast.setHasFixedSize(true);
        recyclerViewCast.setAdapter(castAdapter);
    }

    private void initTvAdapter(){
        tvSeasonAdapter = new TvSeasonAdapter(getContext(), new TvSeriesListner() {
            @Override
            public void ItemClick(TvSeason model, int pos) {
//                Intent intent = new Intent(getContext(), TvFullDetailActivity.class);
//                Gson gson = new Gson();
//                String json = gson.toJson(model);
//                intent.putExtra("tv", json);
//                startActivity(intent);
//                finish();
            }
        });
        recyclerViewTvSeason.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTvSeason.setAdapter(tvSeasonAdapter);
    }


    private void initTrailerAdapter(){
        trailerAdapter = new MovieTrailerAdapter(getContext(), new TrailerListner() {
            @Override
            public void ItemClick(Videos.Video model, int pos) {
                String url = String.format(Constant.YOUTUBE_EMBED_VIDEO_URL, model.key);
                Intent intent = VideoActivity.newIntent(getContext(), url);
                startActivity(intent);
            }
        });
        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTrailer.setHasFixedSize(true);
        recyclerViewTrailer.setAdapter(trailerAdapter);
    }

    private void initImageAdapter(){
        imageAdapter = new ImageMovieListAdapter(getContext());
        recyclerViewImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewImage.setHasFixedSize(true);
        recyclerViewImage.setAdapter(imageAdapter);
    }

    @Override
    public void initModel() {

    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        getTvSeries();
        setContentView(R.layout.layout_details_tv_test);
        initView();
        injectInjector();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.start();
                break;

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void injectInjector() {
        DaggerProjectComponent.builder()
                .projectModule(new ProjectModule(this))
                .build()
                .inject(this);
        presenter.setView(this);
      //  presenter.loadMovieDetails();
        initCastAdapter();
        initTrailerAdapter();
        initImageAdapter();
        initTvAdapter();
        presenter.start();
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
        return tvSeasonSeries;
    }


    private void setUpVideoImage(TvShow model){
 //       if (tvSeason != null){
//
//        }else {
//
//        }
        Picasso.with(getContext())
                .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + model.backdrop_path)
                .placeholder(R.drawable.ic_launcher_background)
                .fit().centerCrop()
                .noFade()
                .error(R.drawable.ic_launcher_background)
                .into(trailerBackDrop);
        trailerBackDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.videos.results != null && model.videos.results.size() > 0) {
                    String url = String.format(Constant.YOUTUBE_EMBED_VIDEO_URL, model.videos.results.get(0).key);
                    Intent intent = VideoActivity.newIntent(getContext(), url);
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public void hidePageReview() {

    }


    @Override
    public void showPageReview() {

    }


    @Override
    public CastInfoAdapter getCastMemberAdapter() {
        return castAdapter;
    }

    @Override
    public TvSeasonAdapter getTvSeasonAdapter() {
        return null;
    }

//    @Override
//    public MovieTrailerAdapter getTrailerAdapter() {
//        return trailerAdapter;
//    }

    @Override
    public void showLineLoading() {
        updateProgressBarSeries(true);
    }

    @Override
    public void hideLineLoading() {
        updateProgressBarSeries(false);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return this;
    }

      private void getData() {
        baseMovie =  getIntent().getStringExtra("baseMovie");
        if (baseMovie != null && baseMovie.length() > 0){
            Gson gson = new Gson();
            Type type = new TypeToken<BaseTvShow>() {
            }.getType();
            movieData = gson.fromJson(baseMovie, type);
        }

    }


    private void getTvSeries() {
        baseTvMovie =  getIntent().getStringExtra("tv");
        if (baseTvMovie != null && baseTvMovie.length() > 0){
            Gson gson = new Gson();
            Type type = new TypeToken<TvSeason>() {
            }.getType();
            tvSeasonSeries = gson.fromJson(baseTvMovie, type);
        }

    }



    public void updateProgressBarViewPager(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void updateProgressBarSeries(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

}
