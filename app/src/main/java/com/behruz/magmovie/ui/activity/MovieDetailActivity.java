package com.behruz.magmovie.ui.activity;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.behruz.magmovie.R;
import com.behruz.magmovie.ui.fragment.movietvDetail.MovieDetailNewFragment;

public class MovieDetailActivity extends AppCompatActivity {
    private String baseMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.activity_genres_detail);
        initFrag();

    }

    private void initFrag() {
        MovieDetailNewFragment newFragment = MovieDetailNewFragment.newInstance(baseMovie);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container_genres_details, newFragment).commitAllowingStateLoss();
    }



    private void getData() {
        baseMovie =  getIntent().getStringExtra("baseMovie");
    }
}
