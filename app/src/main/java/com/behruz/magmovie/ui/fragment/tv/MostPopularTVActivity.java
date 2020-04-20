package com.behruz.magmovie.ui.fragment.tv;

import android.content.Context;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.TvListAdapter;
import com.behruz.magmovie.base.BaseActionbarActivity;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.internal.di.component.DaggerProjectComponent;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.presenter.PresenterView.MostPopularTVShowPresenter;
import com.behruz.magmovie.presenter.PresenterView.MostPopularTvShowView;
import com.behruz.magmovie.utils.AppUtils;
import com.behruz.magmovie.utils.EndlessRecyclerOnScrollListener;
import com.behruz.magmovie.utils.GridMarginDecoration;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;

import javax.inject.Inject;

public class MostPopularTVActivity extends BaseActionbarActivity implements MostPopularTvShowView, TvListAdapter.OnMovieItemSelectedListener {
    private String json;
    @Inject
    MostPopularTVShowPresenter presenter;
    private RecyclerView rvMovies;
    private GridLayoutManager gridLayoutManager;
    private TvListAdapter tvListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int page = 1;
    private int limit = 20;

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    private PreferenUtil preferenUtil;


    @Override
    public void initView() {
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        rvMovies = (RecyclerView) findViewById(R.id.rv_movies);
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


    private void initAdapter() {
        tvListAdapter = new TvListAdapter(getContext());
        tvListAdapter.setOnMovieItemSelectedListener(this);

        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvMovies.setLayoutManager(gridLayoutManager);

        rvMovies.addItemDecoration(new GridMarginDecoration(getContext(), 1, 1, 1, 1));
        rvMovies.setHasFixedSize(true);
        rvMovies.setAdapter(tvListAdapter);


//        gridLayoutManager = new LinearLayoutManager(getContext());
//        rvMovies.setLayoutManager(gridLayoutManager);
//        rvMovies.setHasFixedSize(true);
//        rvMovies.setAdapter(tvListAdapter);

        addScroll();

        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        loadOfflineData();
        presenter.loadTvShowList(page,false);
    }

    private void  loadOfflineData(){
        if (preferenUtil.getPopularTvList() != null && preferenUtil.getPopularTvList().size() >0){
            tvListAdapter.addAll(preferenUtil.getPopularTvList());
        }

        if (AppUtils.isNetworkAvailableAndConnected(getContext())){

        }
    }

    private void addScroll() {
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager, page, limit) {
            @Override
            public void onLoadMore(int next) {
                page = next;
                presenter.loadTvShowList(page,false);
            }
        };

        rvMovies.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    private void refreshData() {
        if(tvListAdapter != null) {
            tvListAdapter.clear();
        }
        page = 2;

        limit = 20;

        removeScroll();
        addScroll();
        presenter.loadTvShowList(page,true);
    }

    private void removeScroll() {
        rvMovies.removeOnScrollListener(endlessRecyclerOnScrollListener);
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
