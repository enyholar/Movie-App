package com.behruz.magmovie.ui.fragment.tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.TvListAdapter;
import com.behruz.magmovie.base.BaseActionbarActivity;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.internal.di.component.DaggerProjectComponent;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.presenter.PresenterView.TopRatedTVShowPresenter;
import com.behruz.magmovie.presenter.PresenterView.TopRatedTvShowView;
import com.behruz.magmovie.utils.AppUtils;
import com.behruz.magmovie.utils.EndlessRecyclerOnScrollListener;
import com.behruz.magmovie.utils.GridMarginDecoration;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;

import javax.inject.Inject;

public class TopRatedTvActivity extends BaseActionbarActivity implements TopRatedTvShowView, TvListAdapter.OnMovieItemSelectedListener {
    private String json;
    @Inject
    TopRatedTVShowPresenter presenter;
    private RecyclerView rvMovies;
    private GridLayoutManager gridLayoutManager;
    private TvListAdapter tvListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int page = 2;
    private int limit = 20;

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    private TextView txt_title;
    private Toolbar toolbar;
    private String title;
    private PreferenUtil preferenUtil;

//    public TopRatedTvActivity() {
//        // Required empty public constructor
//    }
//
//
//    public static TopRatedTvActivity newInstance() {
//        TopRatedTvActivity fragment = new TopRatedTvActivity();
//        Bundle args = new Bundle();
////        args.putString(Constant.GENRES_CONSTANT, json);
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Back");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
        txt_title = findViewById(R.id.title);
        txt_title.setText(title);
    }

    private void getData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
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
        setContentView(R.layout.fragment_most_popular_movie);
        initView();
        injectInjector();
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
        finish();
        super.onBackPressed();
    }

    private void initAdapter() {
        tvListAdapter = new TvListAdapter(getContext());
        tvListAdapter.setOnMovieItemSelectedListener(this);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvMovies.setLayoutManager(gridLayoutManager);

        rvMovies.addItemDecoration(new GridMarginDecoration(getContext(), 1, 1, 1, 1));
        rvMovies.setHasFixedSize(true);
        rvMovies.setAdapter(tvListAdapter);

        addScroll();

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
             refreshData();
            }
        });
        loadOfflineData();
        presenter.loadTopRatedTvShowList(page,false);
    }

    private void  loadOfflineData(){
        if (preferenUtil.getTrendingTvList() != null && preferenUtil.getTrendingTvList().size() >0){
            tvListAdapter.addAll(preferenUtil.getTrendingTvList());
        }


        if (AppUtils.isNetworkAvailableAndConnected(getContext())){

        }
    }

    private void refreshData() {
        if(tvListAdapter != null) {
            tvListAdapter.clear();
        }
        page = 2;

        limit = 20;

        removeScroll();
        addScroll();
        presenter.loadTopRatedTvShowList(page,true);
    }

    private void removeScroll() {
        rvMovies.removeOnScrollListener(endlessRecyclerOnScrollListener);
    }


    private void addScroll() {
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager, page, limit) {
            @Override
            public void onLoadMore(int next) {
                page = next;
                presenter.loadTopRatedTvShowList(page,false);
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
    public TvListAdapter getAdapter() {
        return tvListAdapter;
    }

    @Override
    public void onItemClick(View v, int position) {
        presenter.openMovieDetailScreen(tvListAdapter.getItem(position));
    }


}
