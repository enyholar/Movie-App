package com.behruz.magmovie.ui.fragment.discover;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.DiscoverMovieListAdapter;
import com.behruz.magmovie.adapter.DiscoverTvListAdapter;
import com.behruz.magmovie.model.FilterData;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.CustomErrorView;
import com.behruz.magmovie.utils.EndlessRecyclerOnScrollListener;
import com.behruz.magmovie.utils.GridMarginDecoration;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.DiscoverFilter;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.entities.TvShowResultsPage;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import com.uwetrottmann.tmdb2.services.DiscoverService;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Response;

public class DiscoverResultFragment extends Fragment {

    private static final String TAG = DiscoverResultFragment.class.getSimpleName();

    private static final String ARG_FILTER_DATA = "org.asdtm.fas.discover.filter_date";

    public static final int TYPE_MOVIES = 0;
    public static final int TYPE_TV = 1;

    private FilterData mFilterData;
    private String mGenres;
    private String mSortBy;
    private String mMinRating;
    private String mLang;

    private DiscoverMovieListAdapter mMoviesAdapter;
   // private List<Movie> mMovies;
    private DiscoverTvListAdapter mTvAdapter;
    //private List<TV> mTVs;
    private int mPage = 1;

    private Unbinder unbinder;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    AVLoadingIndicatorView progressBar;
    @BindView(R.id.error)
    CustomErrorView mCustomErrorView;

    public DiscoverResultFragment newInstance(FilterData data) {
        DiscoverResultFragment fragment = new DiscoverResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILTER_DATA, data);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilterData = (FilterData) getArguments().getSerializable(ARG_FILTER_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        unbinder = ButterKnife.bind(this, v);

        mLang = PreferenUtil.getFormatLocale(getActivity());
        final int type = mFilterData.getType();
        mGenres = mFilterData.getGenres();
        mGenres = (mGenres == null) ? "" : mGenres;
        mSortBy = mFilterData.getSortType();
        mMinRating = mFilterData.getMinRating();

        if (type == TYPE_MOVIES) {
//            mMovies = new ArrayList<>();
            mMoviesAdapter = new DiscoverMovieListAdapter(getContext());
        } else {
//            mTVs = new ArrayList<>();
            mTvAdapter = new DiscoverTvListAdapter(getContext());
        }

      //  LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setAdapter(type == TYPE_MOVIES ? mMoviesAdapter : mTvAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addItemDecoration(new GridMarginDecoration(getContext(), 1, 1, 1, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager,mPage,20) {
            @Override
            public void onLoadMore(int page) {
                mPage = page;

                if (type == TYPE_MOVIES) {
                    discoverMovieShowList(mPage);
                } else {
                   loadTvShowList(mPage);
                }
            }
        });

        updateProgressBar(true);
        if (type == TYPE_MOVIES) {
            discoverMovieShowList(1);
        } else {
            loadTvShowList(1);
        }

        return v;
    }


    @SuppressLint("StaticFieldLeak")
    public void loadTvShowList(final int page) {
        new AsyncTask<Void, Void, Void>() {
            private DiscoverFilter discoverFilter;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
             updateProgressBar(true);
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                DiscoverService tvService = tmdb.discoverService();
// Call any of the available endpoints
                try {
                    if (mGenres.length() > 0){
                        String[] g = mGenres.split(",");
                        Integer arrayGenres [] = new Integer[g.length];
                        for (int i = 0; i <g.length ; i++) {
                            arrayGenres[i]= Integer.valueOf(g[i]);
                        }
                        discoverFilter = new DiscoverFilter(arrayGenres);
                    }
                    //SortBy
                    SortBy sortBy = SortBy.valueOf(mSortBy);

                    Response<TvShowResultsPage> response = tvService.discoverTv(PreferenUtil.getFormatLocale(getContext()),SortBy.ORIGINAL_TITLE_DESC,null,null,
                            null,null,null,null,null, Float.valueOf(mMinRating),null,discoverFilter,null,null,
                            null,null,null,null,null).execute();
//                    Response<MovieResultsPage> response = moviesService.movies(mView.getGenres().id
//                    ,"en-US",true,SortBy.ORIGINAL_TITLE_ASC).execute();
                    if (response.isSuccessful()) {
                        if(mTvAdapter != null) {
                            mTvAdapter.clear();
                        }
                        TvShowResultsPage movie = response.body();
                        assert movie != null;
                        mTvAdapter.addAll(movie.results);

                    } else {
                        System.out.println(" is awesome!");
                    }
                } catch (Exception e) {
                    System.out.println(e.toString() + " is awesome!");
                    // see execute() javadoc
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateProgressBar(false);
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void discoverMovieShowList(final int page) {
        new AsyncTask<Void, Void, Void>() {
            private DiscoverFilter discoverFilter;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                updateProgressBar(true);
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                DiscoverService tvService = tmdb.discoverService();

// Call any of the available endpoints
                try {
                    if (mGenres.length() > 0){
                        String[] g = mGenres.split(",");
                        Integer arrayGenres [] = new Integer[g.length];
                        for (int i = 0; i <g.length ; i++) {
                            arrayGenres[i]= Integer.valueOf(g[i]);
                        }
                         discoverFilter = new DiscoverFilter(arrayGenres);
                    }


                    //SortBy
                    Response<MovieResultsPage> response = tvService.discoverMovie(PreferenUtil.getFormatLocale(getContext()), null,SortBy.ORIGINAL_TITLE_DESC,
                            null,null,null,null,null,null,null,null,
                            null,null,null,null,null,Float.valueOf(mMinRating),
                            null,null,null,null,discoverFilter,null,null,
                            null,null,null,null,null,null,null).execute();
                    if (response.isSuccessful()) {
                        if(mMoviesAdapter != null) {
                            mMoviesAdapter.clear();
                        }
                        MovieResultsPage movie = response.body();
                        assert movie != null;
                        mMoviesAdapter.addAll(movie.results);

                    } else {
                        System.out.println(" is awesome!");
                    }
                } catch (Exception e) {
                    System.out.println(e.toString() + " is awesome!");
                    // see execute() javadoc
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateProgressBar(false);
            }
        }.execute();
    }


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
