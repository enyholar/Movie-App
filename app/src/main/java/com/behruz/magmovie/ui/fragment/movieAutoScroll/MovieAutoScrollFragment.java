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
import com.behruz.magmovie.adapter.HomeMovieListAdapter;
import com.behruz.magmovie.adapter.HomeMovieListner;
import com.behruz.magmovie.adapter.HomeTvListAdapter;
import com.behruz.magmovie.adapter.SlidingImage_Adapter;
import com.behruz.magmovie.ui.activity.MovieFullDetailActivity;
import com.behruz.magmovie.ui.fragment.AiringMovieActivity;
import com.behruz.magmovie.ui.fragment.MostPopularMovieActivity;
import com.behruz.magmovie.ui.fragment.MostRatedMovieActivity;
import com.behruz.magmovie.ui.fragment.UpcomingMovieActivity;
import com.behruz.magmovie.utils.AppUtils;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.Method;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;
import com.google.gson.Gson;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.services.MoviesService;
import com.viewpagerindicator.CirclePageIndicator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;


public class MovieAutoScrollFragment extends Fragment  {


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager mPager;
    private SlidingImage_Adapter adapter;
    private HomeMovieListAdapter trendingAdapter;
    private HomeMovieListAdapter popularAdapter;
    private HomeMovieListAdapter airingAdapter;
    private CirclePageIndicator dotsIndicator;
    private int currentPage;
    private RecyclerView mRecyclerView;
    private HomeTvListAdapter mAdapter;
    private AVLoadingIndicatorView progressBarViewPager, progressBarSeries;
    private MovieResultsPage movie;
    private AVLoadingIndicatorView progressBar;
    private RecyclerView recyclerViewTrending,recyclerViewPopular,recyclerViewAiring;
    private PreferenUtil preferenUtil;
    private SwipeRefreshLayout refreshLayout;

    List<BaseMovie> movieList;
    private Method method;
    private int columnWidth;

    public MovieAutoScrollFragment() {
        // Required empty public constructor
    }

    public static MovieAutoScrollFragment newInstance() {
        MovieAutoScrollFragment fragment = new MovieAutoScrollFragment();
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
        refreshLayout = view.findViewById(R.id.swipe_torefresh);
        progressBarViewPager = view.findViewById(R.id.progressBar_pager);
        progressBarSeries = view.findViewById(R.id.progressBar_series);
        adapter = new SlidingImage_Adapter(getActivity(), new HomeMovieListner() {
            @Override
            public void ItemClick(BaseMovie model, int pos) {
                Intent intent = new Intent(getContext(), MovieFullDetailActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(model);
                intent.putExtra("baseMovie", json);
                startActivity(intent);
            }
        });
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

        progressBar = (AVLoadingIndicatorView) view.findViewById(R.id.progreesbar_home_fragment);
        recyclerViewAiring = (RecyclerView) view.findViewById(R.id.recyclerViewAiring_home_fragment);
        recyclerViewTrending = (RecyclerView) view.findViewById(R.id.recyclerViewTrending_home_fragment);
        recyclerViewPopular = (RecyclerView) view.findViewById(R.id.recyclerViewPopular_home_fragment);
        Button button_popular = (Button) view.findViewById(R.id.button_popular_home_fragment);
        Button button_airing = (Button) view.findViewById(R.id.button_airing_home_fragment);
        Button button_trending = (Button) view.findViewById(R.id.button_trending_home_fragment);
        TextView moreUpComingMovies = view.findViewById(R.id.btn_more);
        moreUpComingMovies.setText("More UpComing Movies");
        moreUpComingMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpcomingMovieActivity.class);
                intent.putExtra("title","Upcoming Movies");
                startActivity(intent);
            }
        });

        button_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MostPopularMovieActivity.class);
                intent.putExtra("title","Popular Movies");
                startActivity(intent);            }
        });
        button_airing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AiringMovieActivity.class);
                intent.putExtra("title","Airing Movies");
                startActivity(intent);
            }
        });

        button_trending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MostRatedMovieActivity.class);
                intent.putExtra("title","Trending Movies");
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

//    private void initSeriesAdapter(){
//        mAdapter = new HomeTvListAdapter(getContext(), new HomeTvListner() {
//            @Override
//            public void ItemClick(BaseTvShow model, int pos) {
//
//            }
//        });
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setAdapter(mAdapter);
//    }

    private void initPopularAdapter(){
        popularAdapter = new HomeMovieListAdapter(getContext(), new HomeMovieListner() {
            @Override
            public void ItemClick(BaseMovie model, int pos) {
                Intent intent = new Intent(getContext(), MovieFullDetailActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(model);
                intent.putExtra("baseMovie", json);
                startActivity(intent);
            }
        });
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setAdapter(popularAdapter);
    }

    private void initTrendingAdapter(){
        trendingAdapter = new HomeMovieListAdapter(getContext(), new HomeMovieListner() {
            @Override
            public void ItemClick(BaseMovie model, int pos) {
                Intent intent = new Intent(getContext(), MovieFullDetailActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(model);
                intent.putExtra("baseMovie", json);
                startActivity(intent);
            }
        });
        recyclerViewTrending.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTrending.setHasFixedSize(true);
        recyclerViewTrending.setAdapter(trendingAdapter);
    }

    private void initAiringAdapter(){
        airingAdapter = new HomeMovieListAdapter(getContext(), new HomeMovieListner() {
            @Override
            public void ItemClick(BaseMovie model, int pos) {
                Intent intent = new Intent(getContext(), MovieFullDetailActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(model);
                intent.putExtra("baseMovie", json);
                startActivity(intent);
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
        if (preferenUtil.getAiringMovieList() != null && preferenUtil.getAiringMovieList().size() >0){
            updateProgressBarSeries(false);
            airingAdapter.addAll(preferenUtil.getAiringMovieList());
        }
        if (preferenUtil.getPopularMovieList() != null && preferenUtil.getPopularMovieList().size() >0){
            popularAdapter.addAll(preferenUtil.getPopularMovieList());
        }

        if (preferenUtil.getTrendingMovieList() != null && preferenUtil.getTrendingMovieList().size() >0){
            trendingAdapter.addAll(preferenUtil.getTrendingMovieList());
        }

        if (preferenUtil.getUpcomingMovieList() != null && preferenUtil.getUpcomingMovieList().size() >0){
            adapter.addAll(preferenUtil.getUpcomingMovieList());

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
                MoviesService moviesService = tmdb.moviesService();
// Call any of the available endpoints
                try {
                    Response<MovieResultsPage> response = moviesService.upcoming(page, PreferenUtil.getFormatLocale(getContext())).execute();
                    if (response.isSuccessful()) {
                        if(adapter != null) {
                            adapter.clear();
                        }
                         movie = response.body();
                         preferenUtil.saveUpcomingMovieList(movie.results);
                        assert movie != null;

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
                if (movie != null){
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
                MoviesService moviesService = tmdb.moviesService();
// Call any of the available endpoints
                try {
                    Response<MovieResultsPage> response = moviesService.nowPlaying(page,PreferenUtil.getFormatLocale(getContext())).execute();
                    if (response.isSuccessful()) {
                        if(airingAdapter != null) {
                            airingAdapter.clear();
                        }
                        MovieResultsPage movie = response.body();
                        assert movie != null;
                        airingAdapter.addAll(movie.results);
                        preferenUtil.saveAiringMovieList(movie.results);

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
                MoviesService moviesService = tmdb.moviesService();
// Call any of the available endpoints
                try {
                    Response<MovieResultsPage> response = moviesService.topRated(page,PreferenUtil.getFormatLocale(getContext())).execute();
                    if (response.isSuccessful()) {
                        if(trendingAdapter != null) {
                            trendingAdapter.clear();
                        }
                        MovieResultsPage movie = response.body();
                        assert movie != null;
                        trendingAdapter.addAll(movie.results);
                        preferenUtil.saveTrendingMovieList(movie.results);
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
                MoviesService moviesService = tmdb.moviesService();
// Call any of the available endpoints
                try {
                    Response<MovieResultsPage> response = moviesService.popular(page,PreferenUtil.getFormatLocale(getContext())).execute();
                    if (response.isSuccessful()) {
                        if(popularAdapter != null) {
                            popularAdapter.clear();
                        }
                        MovieResultsPage movie = response.body();
                        assert movie != null;
                        popularAdapter.addAll(movie.results);
                        preferenUtil.savePopularMovieList(movie.results);
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
                hideRefresh();
                updateProgressBarSeries(false);
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

    private void hideRefresh(){
        if (refreshLayout != null ){
            refreshLayout.setRefreshing(false);
        }
    }

//    @Override
//    public void onItemClick(View v, int position) {
//        Intent intent = new Intent(getContext(), MovieFullDetailActivity.class);
//        Gson gson = new Gson();
//        String json = gson.toJson(mAdapter.getItem(position));
//        intent.putExtra("baseMovie", json);
//        startActivity(intent);
//    }
}
