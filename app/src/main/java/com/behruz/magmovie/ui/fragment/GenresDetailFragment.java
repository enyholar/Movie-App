package com.behruz.magmovie.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.MovieListAdapter;
import com.behruz.magmovie.base.BaseFragment;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.internal.di.component.DaggerProjectComponent;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.model.GenresModel;
import com.behruz.magmovie.presenter.PresenterView.MovieGenresDetailPresenter;
import com.behruz.magmovie.presenter.PresenterView.MovieGenresDetailView;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.EndlessRecyclerOnScrollListener;
import com.behruz.magmovie.utils.GridMarginDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

public class GenresDetailFragment extends BaseFragment implements MovieGenresDetailView, MovieListAdapter.OnMovieItemSelectedListener {
    private String json;
    private GenresModel genre;
    @Inject
    MovieGenresDetailPresenter presenter;
    private RecyclerView rvMovies;
    private GridLayoutManager gridLayoutManager;
    private MovieListAdapter movieListAdapter;
    private SwipeRefreshLayout refreshLayout;
    private int page = 1;
    private int limit = 20;

    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    public GenresDetailFragment() {
        // Required empty public constructor
    }


    public static GenresDetailFragment newInstance(String json) {
        GenresDetailFragment fragment = new GenresDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constant.GENRES_CONSTANT, json);
        fragment.setArguments(args);
        return fragment;
    }

    private void readArgs() {
        Bundle args = getArguments();
        if (args != null) {
            json = args.getString(Constant.GENRES_CONSTANT);
            Gson gson = new Gson();
            Type type = new TypeToken<GenresModel>() {
            }.getType();

            genre = gson.fromJson(json, type);
        }
    }


    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void injectInjector() {
        DaggerProjectComponent.builder()
                .projectModule(new ProjectModule(this.getActivity()))
                .build()
                .inject(this);
        presenter.setView(this);
        presenter.start();
        initAdapter();
    }


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
                //         refreshData();
            }
        });

        presenter.loadGenresDetails(page);
    }

    @Override
    public void initModels() {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gneres_detail, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        rvMovies = (RecyclerView) view.findViewById(R.id.rv_movies);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        readArgs();
        injectInjector();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void addScroll() {
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(gridLayoutManager, page, limit) {
            @Override
            public void onLoadMore(int next) {
                page = next;
                presenter.loadGenresDetails(page);
            }
        };

        rvMovies.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    @Override
    public GenresModel getGenres() {
        return genre;
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
    public MovieListAdapter getAdapter() {
        return movieListAdapter;
    }

    @Override
    public void onItemClick(View v, int position) {
        presenter.openMovieDetailScreen(movieListAdapter.getItem(position));
    }
}
