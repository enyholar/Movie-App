package com.behruz.magmovie.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.MovieListAdapter;
import com.behruz.magmovie.base.BaseActionbarActivity;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.internal.di.component.DaggerProjectComponent;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.presenter.PresenterView.UpcomingMovieListView;
import com.behruz.magmovie.presenter.PresenterView.UpcomingMoviePresenter;
import com.behruz.magmovie.utils.AppUtils;
import com.behruz.magmovie.utils.EndlessRecyclerOnScrollListener;
import com.behruz.magmovie.utils.GridMarginDecoration;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;

import javax.inject.Inject;

public class UpcomingMovieActivity extends BaseActionbarActivity implements UpcomingMovieListView, MovieListAdapter.OnMovieItemSelectedListener {
    @Inject
    UpcomingMoviePresenter presenter;
    private RecyclerView rvMovies;
    private LinearLayoutManager gridLayoutManager;
    private MovieListAdapter movieListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int page = 2;
    private int limit = 20;
    private String title;
    private TextView txt_title;
    private Toolbar toolbar;

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    private PreferenUtil preferenUtil;

//    public UpcomingMovieActivity() {
//        // Required empty public constructor
//    }


//    public static UpcomingMovieActivity newInstance() {
//        UpcomingMovieActivity fragment = new UpcomingMovieActivity();
//        Bundle args = new Bundle();
////        args.putString(Constant.GENRES_CONSTANT, json);
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public Presenter getPresenter() {
        return presenter;
    }


    @Override
    protected void injectInjector() {
        DaggerProjectComponent.builder()
                .projectModule(new ProjectModule(this))
                .build()
                .inject(this);
        presenter.setView(this);
        preferenUtil = PreferenUtil.getInstant(getContext());
        presenter.start();
        initAdapter();
    }

    @Override
    public void initView() {
        toolbar= findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Back");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
        txt_title = findViewById(R.id.title);
        txt_title.setText(title);
    }

    @Override
    public void initModel() {

    }

    private void getData(){
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.fragment_most_popular_movie);
        initView();
        injectInjector();
    }

//    private void initAdapter() {
//        movieListAdapter = new MovieListAdapter(getContext());
//        movieListAdapter.setOnMovieItemSelectedListener(this);
//
////        gridLayoutManager = new GridLayoutManager(getContext(), 2);
////        rvMovies.setLayoutManager(gridLayoutManager);
////
////        rvMovies.addItemDecoration(new GridMarginDecoration(getContext(), 1, 1, 1, 1));
////        rvMovies.setHasFixedSize(true);
////        rvMovies.setAdapter(movieListAdapter);
//
//
//        gridLayoutManager = new LinearLayoutManager(getContext());
//        rvMovies.setLayoutManager(gridLayoutManager);
//        rvMovies.setHasFixedSize(true);
//        rvMovies.setAdapter(movieListAdapter);
//
//        addScroll();
//
//        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData();
//            }
//        });
//        loadOfflineData();
//        presenter.loadUpcomingMovieList(page);
//    }


    private void initAdapter() {
        movieListAdapter = new MovieListAdapter(getContext());
        movieListAdapter.setOnMovieItemSelectedListener(this);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvMovies.setLayoutManager(gridLayoutManager);

        rvMovies.addItemDecoration(new GridMarginDecoration(getContext(), 1, 1, 1, 1));
        rvMovies.setHasFixedSize(true);
        rvMovies.setAdapter(movieListAdapter);

        addScroll();

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        loadOfflineData();
        presenter.loadUpcomingMovieList(page,false);
    }

    private void refreshData() {
        if(movieListAdapter != null) {
            movieListAdapter.clear();
        }
        page = 2;

        limit = 20;

        removeScroll();
        addScroll();
        presenter.loadUpcomingMovieList(page,true);
    }

    private void removeScroll() {
        rvMovies.removeOnScrollListener(endlessRecyclerOnScrollListener);
    }

    private void  loadOfflineData(){
        if (preferenUtil.getPopularMovieList() != null && preferenUtil.getPopularMovieList().size() >0){
            movieListAdapter.addAll(preferenUtil.getPopularMovieList());
        }


        if (AppUtils.isNetworkAvailableAndConnected(getContext())){

        }
    }



//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_most_popular_movie, container, false);
//        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
//        rvMovies = (RecyclerView) view.findViewById(R.id.rv_movies);
//
//
//        return view;
//    }

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


    private void addScroll() {
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager, page, limit) {
            @Override
            public void onLoadMore(int next) {
                page = next;
                presenter.loadUpcomingMovieList(page,false);
            }
        };

        rvMovies.addOnScrollListener(endlessRecyclerOnScrollListener);
    }


    @Override
    public void showLineLoading() {
        if (refreshLayout != null) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hideLineLoading() {
        if (refreshLayout != null)
            refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public MovieListAdapter getAdapter() {
        return movieListAdapter;
    }

    @Override
    public void onItemClick(View v, int position) {
        presenter.openMovieDetailScreen(movieListAdapter.getItem(position));
    }
}
