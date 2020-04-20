package com.behruz.magmovie.ui.fragment.tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.CastCrewGuestAdapter;
import com.behruz.magmovie.base.BaseActionbarActivity;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.utils.GridMarginDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uwetrottmann.tmdb2.entities.CastMember;
import com.uwetrottmann.tmdb2.entities.Credits;
import com.uwetrottmann.tmdb2.entities.CrewMember;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.behruz.magmovie.utils.Constant.INTENT_CAST_DETAILS;

public class AllCastActivity extends BaseActionbarActivity {

    private Credits movieData;

    private RecyclerView recyclerView;
    private String json;
    private CastCrewGuestAdapter castAdapter;
    private TextView txt_movie_review;
    private Toolbar toolbar;

    @Override
    public void initView() {
        toolbar= findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cast Member");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.rv_movie_reviews);
        txt_movie_review = findViewById(R.id.txt_movie_reviews_error);
    }

    @Override
    public void initModel() {
        initAdapter();
    }

    @Override
    public Presenter getPresenter() {
        return null;
    }


    @Override
    protected void injectInjector() {
//        DaggerProjectComponent.builder()
//                .projectModule(new ProjectModule(this.getActivity()))
//                .build()
//                .inject(this);
//        presenter.setView(this);
//        presenter.start();
//        presenter.loadCastMember();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readArgs();
        setContentView(R.layout.fragment_movie_review);
        initView();
        initModel();
    }

    private void initAdapter() {
        List<CrewMember> crewMemberDatas = new ArrayList<>();
         List<CastMember> guestMemberDatas = new ArrayList<>();
        List<CastMember> castMemberDatas = new ArrayList<>();
        if (movieData.crew != null){
            crewMemberDatas.addAll(movieData.crew);
        }

        if (movieData.guest_stars != null){
            guestMemberDatas.addAll(movieData.guest_stars);
        }

        if (movieData.cast != null){
            castMemberDatas.addAll(movieData.cast);
        }
        castAdapter = new CastCrewGuestAdapter(AllCastActivity.this,castMemberDatas,crewMemberDatas,guestMemberDatas);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AllCastActivity.this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addItemDecoration(new GridMarginDecoration(AllCastActivity.this, 1, 1, 1, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(castAdapter);
    }

    public static Intent newIntent(Context context, String json) {
        Intent intent = new Intent(context, AllCastActivity.class);
        Bundle args = new Bundle();
        args.putString(INTENT_CAST_DETAILS, json);
        intent.putExtras(args);
        return intent;
    }

    private void readArgs() {
            json = getIntent().getStringExtra(INTENT_CAST_DETAILS);
            Gson gson = new Gson();
            Type type = new TypeToken<Credits>() {
            }.getType();

            movieData = gson.fromJson(json, type);
        }


    public void hidePageReview() {
//        txt_movie_review.setVisibility(View.VISIBLE);
//        txt_movie_review.setText("No cast for this movie");
//        recyclerView.setVisibility(View.GONE);
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


}
