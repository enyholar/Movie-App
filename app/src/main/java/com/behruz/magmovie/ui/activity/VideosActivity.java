package com.behruz.magmovie.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.VideoAdapter;
import com.behruz.magmovie.utils.CustomErrorView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uwetrottmann.tmdb2.entities.Videos;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideosActivity extends AppCompatActivity {

    private static final String TAG = VideosActivity.class.getSimpleName();

    private static final String ARG_TYPE = "org.asdtm.fas.video_type";
    private static final String ARG_ID = "org.asdtm.fas.id";

    public static final int TYPE_MOVIES = 0;
    public static final int TYPE_TV = 1;

    private VideoAdapter mAdapter;
    private String jsonData;
    private String mLang;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    AVLoadingIndicatorView progressBar;
    @BindView(R.id.error)
    CustomErrorView mCustomErrorView;
    private List<Videos.Video> movieData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        ButterKnife.bind(this);

        Bundle args = getIntent().getExtras();
        jsonData = args.getString(ARG_ID);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Videos.Video>>() {
        }.getType();

        movieData = gson.fromJson(jsonData, type);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.video);
        }

        mAdapter = new VideoAdapter(movieData);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // loadTvMovieVideos();

    }



//    private void loadTvMovieVideos() {
//        mVideos.addAll(movieData);
//        mAdapter.notifyDataSetChanged();
//    }

    private void updateProgressBar(boolean visibility) {
        if (progressBar != null) {
            progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }

    private void onLoadFailed(Throwable t) {
        mCustomErrorView.setError(t);
        mCustomErrorView.setVisibility(View.VISIBLE);
        updateProgressBar(false);
    }

    public static Intent newIntent(Context context, String json) {
        Intent intent = new Intent(context, VideosActivity.class);
        Bundle args = new Bundle();
        args.putString(ARG_ID, json);
        intent.putExtras(args);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
