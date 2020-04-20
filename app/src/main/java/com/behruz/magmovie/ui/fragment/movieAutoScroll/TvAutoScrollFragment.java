package com.behruz.magmovie.ui.fragment.movieAutoScroll;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.HomeTvListAdapter;
import com.behruz.magmovie.adapter.HomeTvListner;
import com.behruz.magmovie.adapter.SlidingImage_TvAdapter;
import com.behruz.magmovie.ui.activity.TvFullDetailActivity;
import com.behruz.magmovie.ui.fragment.tv.MostPopularTVActivity;
import com.behruz.magmovie.ui.fragment.tv.OnAiringTVActivity;
import com.behruz.magmovie.ui.fragment.tv.OnTheAirTVActivity;
import com.behruz.magmovie.ui.fragment.tv.TopRatedTvActivity;
import com.behruz.magmovie.utils.AppUtils;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;
import com.google.gson.Gson;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.TvShowResultsPage;
import com.uwetrottmann.tmdb2.services.TvService;
import com.viewpagerindicator.CirclePageIndicator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;


public class TvAutoScrollFragment extends Fragment  {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager mPager;
    private SlidingImage_TvAdapter adapter;
    private HomeTvListAdapter trendingAdapter;
    private HomeTvListAdapter popularAdapter;
    private HomeTvListAdapter airingAdapter;
    private CirclePageIndicator dotsIndicator;
    private int currentPage;
    private RecyclerView mRecyclerView;
    private HomeTvListAdapter mAdapter;
    private AVLoadingIndicatorView progressBarViewPager, progressBarSeries;
    private AVLoadingIndicatorView progressBar;
    private RecyclerView recyclerViewTrending, recyclerViewPopular, recyclerViewAiring;

    List<BaseMovie> movieList;
    private Button button_popular, button_trending, button_airing;
    private TvShowResultsPage movie;
    private PreferenUtil preferenUtil;
    private SwipeRefreshLayout refreshLayout;

    public TvAutoScrollFragment() {
        // Required empty public constructor
    }

    public static TvAutoScrollFragment newInstance() {
        TvAutoScrollFragment fragment = new TvAutoScrollFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        dotsIndicator = (CirclePageIndicator) view.findViewById(R.id.titles);
        progressBarViewPager = view.findViewById(R.id.progressBar_pager);
        progressBarSeries = view.findViewById(R.id.progressBar_series);
        adapter = new SlidingImage_TvAdapter(getActivity(), new HomeTvListner() {
            @Override
            public void ItemClick(BaseTvShow model, int pos) {
//                Intent intent = new Intent(getActivity(), MostPopularTVActivity.class);
//                intent.putExtra("title", "UpComing Movie");
//                startActivity(intent);
                onItemClick(model);
            }
        });
        refreshLayout = view.findViewById(R.id.swipe_torefresh);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMovieList(1);
                loadOnAiringMovieShowList(1);
                loadOnPopularMovieShowList(1);
                loadOnTrendingMovieShowList(1);
            }
        });
        mPager.setAdapter(adapter);
        dotsIndicator.setViewPager(mPager);
        refreshLayout = view.findViewById(R.id.swipe_torefresh);
        progressBar = (AVLoadingIndicatorView) view.findViewById(R.id.progreesbar_home_fragment);
        recyclerViewAiring = (RecyclerView) view.findViewById(R.id.recyclerViewAiring_home_fragment);
        recyclerViewTrending = (RecyclerView) view.findViewById(R.id.recyclerViewTrending_home_fragment);
        recyclerViewPopular = (RecyclerView) view.findViewById(R.id.recyclerViewPopular_home_fragment);
        button_popular = (Button) view.findViewById(R.id.button_popular_home_fragment);
        button_airing = (Button) view.findViewById(R.id.button_airing_home_fragment);
        button_trending = (Button) view.findViewById(R.id.button_trending_home_fragment);

        TextView moreUpComingMovies = view.findViewById(R.id.btn_more);
        moreUpComingMovies.setText("More UpComing Series");
        moreUpComingMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OnAiringTVActivity.class);
                intent.putExtra("title","Upcoming Movies");
                startActivity(intent);
            }
        });

        button_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MostPopularTVActivity.class);
                intent.putExtra("title", "Popular Series");
                startActivity(intent);
            }
        });
        button_airing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OnTheAirTVActivity.class);
                intent.putExtra("title", "Airing Series");
                startActivity(intent);
            }
        });

        button_trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopRatedTvActivity.class);
                intent.putExtra("title", "Trending Series");
                startActivity(intent);
            }
        });

        return view;
    }

//    private void initPagerAdapter(){
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        mRecyclerView.setAdapter(mAdapter);
//    }

    private void initSeriesAdapter() {
        mAdapter = new HomeTvListAdapter(getContext(), new HomeTvListner() {
            @Override
            public void ItemClick(BaseTvShow model, int pos) {
                onItemClick(model);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initPopularAdapter() {
        popularAdapter = new HomeTvListAdapter(getContext(), new HomeTvListner() {
            @Override
            public void ItemClick(BaseTvShow model, int pos) {
                onItemClick(model);
            }
        });
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setAdapter(popularAdapter);
    }

    private void initTrendingAdapter() {
        trendingAdapter = new HomeTvListAdapter(getContext(), new HomeTvListner() {
            @Override
            public void ItemClick(BaseTvShow model, int pos) {
                onItemClick(model);
            }
        });
        recyclerViewTrending.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTrending.setHasFixedSize(true);
        recyclerViewTrending.setAdapter(trendingAdapter);
    }

    private void initAiringAdapter() {
        airingAdapter = new HomeTvListAdapter(getContext(), new HomeTvListner() {
            @Override
            public void ItemClick(BaseTvShow model, int pos) {
                onItemClick(model);
            }
        });
        recyclerViewAiring.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAiring.setHasFixedSize(true);
        recyclerViewAiring.setAdapter(airingAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferenUtil = PreferenUtil.getInstant(getContext());
        initAiringAdapter();
        initPopularAdapter();
        initTrendingAdapter();
        loadOfflineData();
        loadMovieList(1);
        loadOnAiringMovieShowList(1);
        loadOnPopularMovieShowList(1);
        loadOnTrendingMovieShowList(1);
    }

    private void  loadOfflineData(){
        if (preferenUtil.getAiringTvList() != null && preferenUtil.getAiringTvList().size() >0){
            airingAdapter.addAll(preferenUtil.getAiringTvList());
            updateProgressBarSeries(false);
        }
        if (preferenUtil.getPopularTvList() != null && preferenUtil.getPopularTvList().size() >0){
            popularAdapter.addAll(preferenUtil.getPopularTvList());
        }

        if (preferenUtil.getTrendingTvList() != null && preferenUtil.getTrendingTvList().size() >0){
            trendingAdapter.addAll(preferenUtil.getTrendingTvList());
        }

        if (preferenUtil.getUpcomingTvList() != null && preferenUtil.getUpcomingTvList().size() >0){
            adapter.addAll(preferenUtil.getUpcomingTvList());

        }
        if (AppUtils.isNetworkAvailableAndConnected(getContext())){

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void autoSlide() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == adapter.getCount()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);


        dotsIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void loadMovieList(final int page) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                updateProgressBarViewPager(true);
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                TvService tvService = tmdb.tvService();
// Call any of the available endpoints
                try {
                    Response<TvShowResultsPage> response = tvService.onTheAir(page, PreferenUtil.getFormatLocale(getContext())).execute();
                    if (response.isSuccessful()) {
                        movie = response.body();
                        if (adapter != null){
                            adapter.clear();
                        }
                        preferenUtil.saveUpcomingTvList(movie.results);
                        assert movie != null;
                        // adapter.addAll(movie.results);

                    } else {
                        System.out.println(" is awesome!");
                    }
                } catch (Exception e) {
                    System.out.println(e.toString() + " is awesome!");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (movie != null) {
                    adapter.addAll(movie.results);
                }
                adapter.notifyDataSetChanged();
                hideRefresh();
                updateProgressBarViewPager(false);
                autoSlide();
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void loadOnAiringMovieShowList(final int page) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                updateProgressBarSeries(true);
            }

            @Override
            protected Void doInBackground(final Void... unused) {

                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                TvService tvService = tmdb.tvService();
// Call any of the available endpoints
                try {
                    Response<TvShowResultsPage> response = tvService.airingToday(page, PreferenUtil.getFormatLocale(getContext())).execute();
                    if (response.isSuccessful()) {
                        TvShowResultsPage movie = response.body();
                        assert movie != null;
                        if(airingAdapter != null) {
                            airingAdapter.clear();
                        }
                        preferenUtil.saveAiringTvList(movie.results);
                        airingAdapter.addAll(movie.results);

                    } else {
                        System.out.println(" is awesome!");
                    }
                } catch (Exception e) {
                    System.out.println(e.toString() + " is awesome!");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                hideRefresh();
                updateProgressBarSeries(false);
                airingAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void loadOnTrendingMovieShowList(final int page) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                updateProgressBarSeries(true);
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                TvService tvService = tmdb.tvService();
// Call any of the available endpoints
                try {
                    Response<TvShowResultsPage> response = tvService.topRated(page, PreferenUtil.getFormatLocale(getContext())).execute();
                    if (response.isSuccessful()) {
                        TvShowResultsPage movie = response.body();
                        assert movie != null;
                        if(trendingAdapter != null) {
                            trendingAdapter.clear();
                        }
                        trendingAdapter.addAll(movie.results);
                        preferenUtil.saveTrendingTvList(movie.results);

                    } else {
                        System.out.println(" is awesome!");
                    }
                } catch (Exception e) {
                    System.out.println(e.toString() + " is awesome!");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                hideRefresh();
                trendingAdapter.notifyDataSetChanged();
                updateProgressBarSeries(false);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void loadOnPopularMovieShowList(final int page) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                updateProgressBarSeries(true);
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                TvService tvService = tmdb.tvService();
// Call any of the available endpoints
                try {
                    Response<TvShowResultsPage> response = tvService.popular(page, PreferenUtil.getFormatLocale(getContext())).execute();
                    if (response.isSuccessful()) {
                        TvShowResultsPage movie = response.body();
                        assert movie != null;
                        if(popularAdapter != null) {
                            popularAdapter.clear();
                        }
                        popularAdapter.addAll(movie.results);
                        preferenUtil.savePopularTvList(movie.results);

                    } else {
                        System.out.println(" is awesome!");
                    }
                } catch (Exception e) {
                    System.out.println(e.toString() + " is awesome!");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateProgressBarSeries(false);
                hideRefresh();
                popularAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void updateProgressBarViewPager(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void updateProgressBarSeries(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void onItemClick(BaseTvShow model) {
        Intent intent = new Intent(getContext(), TvFullDetailActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(model);
        intent.putExtra("baseMovie", json);
        startActivity(intent);
    }

    private void hideRefresh(){
        if (refreshLayout != null ){
            refreshLayout.setRefreshing(false);
        }
    }
}
