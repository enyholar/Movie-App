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
import com.behruz.magmovie.presenter.PresenterView.OnTheAirTVShowPresenter;
import com.behruz.magmovie.presenter.PresenterView.OnTheAirTvShowView;
import com.behruz.magmovie.utils.AppUtils;
import com.behruz.magmovie.utils.EndlessRecyclerOnScrollListener;
import com.behruz.magmovie.utils.GridMarginDecoration;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;

import javax.inject.Inject;

public class OnTheAirTVActivity extends BaseActionbarActivity implements OnTheAirTvShowView, TvListAdapter.OnMovieItemSelectedListener {
    private String json;
    @Inject
    OnTheAirTVShowPresenter presenter;
    private RecyclerView rvMovies;
    private GridLayoutManager gridLayoutManager;
    private TvListAdapter tvListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int page = 2;
    private int limit = 20;

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    private Toolbar toolbar;
    private TextView txt_title;
    private String title;
    private PreferenUtil preferenUtil;

    @Override
    public void initView() {
        toolbar= findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Back");
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        rvMovies = (RecyclerView)findViewById(R.id.rv_movies);
        txt_title = findViewById(R.id.title);
        txt_title.setText(title);
    }

    private void getData(){
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
        preferenUtil = PreferenUtil.getInstant(getContext());
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
        presenter.start();
        initAdapter();
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
        presenter.loadOnAiringTvShowList(page,false);
    }

    private void  loadOfflineData(){
        if (preferenUtil.getAiringTvList() != null && preferenUtil.getAiringTvList().size() >0){
            tvListAdapter.addAll(preferenUtil.getAiringTvList());
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
        presenter.loadOnAiringTvShowList(page,true);
    }

    private void removeScroll() {
        rvMovies.removeOnScrollListener(endlessRecyclerOnScrollListener);
    }


    private void addScroll() {
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager, page, limit) {
            @Override
            public void onLoadMore(int next) {
                page = next;
                presenter.loadOnAiringTvShowList(page,false);
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
