package com.behruz.magmovie.ui.fragment.movietvDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.MovieReviewAdapter;
import com.behruz.magmovie.base.BaseActionbarActivity;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.presenter.PresenterView.MovieDetailPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uwetrottmann.tmdb2.entities.Review;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import static com.behruz.magmovie.utils.Constant.INTENT_REVIEW_DETAILS;

public class MovieReviewActivity extends BaseActionbarActivity  {
    private List<Review> movieData;

    @Inject
    MovieDetailPresenter presenter;
    private RecyclerView recyclerView;
    private String json;
    private MovieReviewAdapter reviewAdapter;
    private TextView txt_movie_review;
    private Toolbar toolbar;

    @Override
    public void initView() {
        toolbar= findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Review");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.rv_movie_reviews);
        txt_movie_review = findViewById(R.id.txt_movie_reviews_error);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void initModel() {
        initAdapter();
        reviewAdapter.addAll(movieData);
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void injectInjector() {
//        DaggerProjectComponent.builder()
//                .projectModule(new ProjectModule(this))
//                .build()
//                .inject(this);
//        presenter.setView(this);
//        presenter.start();
    }


    private void initAdapter() {
        reviewAdapter = new MovieReviewAdapter(MovieReviewActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MovieReviewActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(reviewAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.fragment_movie_review);
        initView();
        initModel();
    }

    private void getData() {
        json = getIntent().getStringExtra(INTENT_REVIEW_DETAILS);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Review>>() {
        }.getType();

        movieData = gson.fromJson(json, type);
    }

    public static Intent newIntent(Context context, String json) {
        Intent intent = new Intent(context, MovieReviewActivity.class);
        Bundle args = new Bundle();
        args.putString(INTENT_REVIEW_DETAILS, json);
        intent.putExtras(args);
        return intent;
    }

    public void hidePageReview() {
        txt_movie_review.setVisibility(View.VISIBLE);
        txt_movie_review.setText("No review for this movie");
        recyclerView.setVisibility(View.GONE);
    }
}
