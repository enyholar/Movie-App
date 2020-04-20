package com.behruz.magmovie.utils.PreferenUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.entities.TvShow;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by Tinh Vu on 27/09/2016.
 */

public class PreferenUtil {


    private static final String SKIP_REGISTRATION_KEY = "SKIPREGISTRATIONKEY";
    private static final String NIGHT_MODE_KEY = "NightMode";
    private static final String RADIO = "Radio";
    private static final String ALLRADIOJSONlIST = "AllRadiolist";
    private static final String THEMECOLOR = "ThemeColor";
    public static final String COLOR_KEY = "color";
    private static final String check_first = "Check";
    private static final String MusicPlayed = "MUSICPLAYED";
    private static final String UPCOMINGMOVIELIST = "UPCOMINGMOVIELIST";
    private static final String TRENDINGMOVIELSIT = "TRENDINGMOVIELSIT";
    private static final String AIRINGMOVIELIST = "AIRINGMOVIELIST";
    private static final String POPULARMOVIELIST = "POPULARMOVIELIST";
    private static final String UPCOMINGTVlIST = "UPCOMINGTVlIST";
    private static final String TRENDINGTVLIST = "TRENDINGTVLIST";
    private static final String AIRINGTVLIST = "AIRINGTVLIST";
    private static final String POPULARTVLIST = "POPULARTVLIST";
    private static final String MOVIEDETAIL = "MOVIEDETAIL";
    private static final String SKIP_LANGUANGE_KEY = "SKIP_LANGUANGE_KEY";
    private static final String TVDETAIL = "TVDETAIL";


    private static Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static PreferenUtil mInstant = null;

    private PreferenUtil(Context context) {
        mContext = context;
    }

    public static PreferenUtil getInstant(Context context) {
        if (mInstant == null) {
            mInstant = new PreferenUtil(context);
            PreferenceManager.getDefaultSharedPreferences(context)
                    .registerOnSharedPreferenceChangeListener(mListener);
        }
        return mInstant;
    }


    private static SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }
    };



    public void checkFirstLaunch(Boolean isFirstLaunch) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        if (!getFirstLaunch()) {
            sharedPreferencesEditor.putBoolean(check_first, true);
            sharedPreferencesEditor.apply();
        } else {
            sharedPreferencesEditor.putBoolean(check_first, isFirstLaunch);
            sharedPreferencesEditor.apply();
        }

    }

    public void enableSkipLanguage(boolean isChecked) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putBoolean(SKIP_LANGUANGE_KEY, isChecked);
        sharedPreferencesEditor.apply();
    }

    public boolean checkSkipLanguage() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getBoolean(SKIP_LANGUANGE_KEY, false);
    }



    public boolean getFirstLaunch() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return sharedPreferences.getBoolean(check_first, false);
    }


    public boolean checkFirtLaunch() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String keyCheckFirstLunch = "CheckFirstLunch";
        boolean checkFirstLunch = sharedPreferences.getBoolean(keyCheckFirstLunch, true);
        if (checkFirstLunch) {
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putBoolean(keyCheckFirstLunch, false);
            sharedPreferencesEditor.apply();
        }
        return checkFirstLunch;
    }

    public void saveLastOptionOfPlayed(String option) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(MusicPlayed, option);
        sharedPreferencesEditor.apply();
    }

    public String getLastOptionOfPlayed() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return String.valueOf(sharedPreferences.getString(MusicPlayed, ""));
    }



    public void saveUpcomingMovieList(List<BaseMovie> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(UPCOMINGMOVIELIST, json);
        sharedPreferencesEditor.apply();
    }

    public List<BaseMovie> getUpcomingMovieList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = String.valueOf(sharedPreferences.getString(UPCOMINGMOVIELIST, ""));
        return getJsonMovieList(json);
    }

    public void saveTrendingMovieList(List<BaseMovie> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(TRENDINGMOVIELSIT, json);
        sharedPreferencesEditor.apply();
    }

    public List<BaseMovie> getTrendingMovieList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = String.valueOf(sharedPreferences.getString(TRENDINGMOVIELSIT, ""));
        return getJsonMovieList(json);
    }


    public void savePopularMovieList(List<BaseMovie> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(POPULARMOVIELIST, json);
        sharedPreferencesEditor.apply();
    }

    public List<BaseMovie> getPopularMovieList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = String.valueOf(sharedPreferences.getString(POPULARMOVIELIST, ""));
        return getJsonMovieList(json);
    }


    public void saveAiringMovieList(List<BaseMovie> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(AIRINGMOVIELIST, json);
        sharedPreferencesEditor.apply();
    }

    public List<BaseMovie> getAiringMovieList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = String.valueOf(sharedPreferences.getString(AIRINGMOVIELIST, ""));
        return getJsonMovieList(json);
    }

    public void saveAiringTvList(List<BaseTvShow> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(AIRINGTVLIST, json);
        sharedPreferencesEditor.apply();
    }

    public List<BaseTvShow> getAiringTvList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = String.valueOf(sharedPreferences.getString(AIRINGTVLIST, ""));
        return getJsonTVList(json);
    }


    public void saveTrendingTvList(List<BaseTvShow> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(TRENDINGTVLIST, json);
        sharedPreferencesEditor.apply();
    }

    public List<BaseTvShow> getTrendingTvList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = String.valueOf(sharedPreferences.getString(TRENDINGTVLIST, ""));
        return getJsonTVList(json);
    }

    public void savePopularTvList(List<BaseTvShow> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(POPULARTVLIST, json);
        sharedPreferencesEditor.apply();
    }

    public List<BaseTvShow> getPopularTvList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = String.valueOf(sharedPreferences.getString(POPULARTVLIST, ""));
        return getJsonTVList(json);
    }

    public void saveUpcomingTvList(List<BaseTvShow> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(UPCOMINGTVlIST, json);
        sharedPreferencesEditor.apply();
    }

    public List<BaseTvShow> getUpcomingTvList() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = String.valueOf(sharedPreferences.getString(UPCOMINGTVlIST, ""));
        return getJsonTVList(json);
    }

    private List<BaseMovie> getJsonMovieList(String jsonList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<BaseMovie>>() {
        }.getType();

        return gson.fromJson(jsonList, type);
    }
    private List<BaseTvShow> getJsonTVList(String jsonList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<BaseTvShow>>() {
        }.getType();

        return gson.fromJson(jsonList, type);
    }

    public static String getFormatLocale(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return StringUtils.getFormatLocale(prefs.getString(Constant.PREF_MOVIES_LANGUAGE, ""));
    }


    public void saveMovieDetail(Movie movie, String movieId) {
        Gson gson = new Gson();
        String json = gson.toJson(movie);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(movieId, json);
        sharedPreferencesEditor.apply();
    }

    public Movie getMovieDetail(String movieId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = String.valueOf(sharedPreferences.getString(movieId, ""));
        Gson gson = new Gson();
        Type type = new TypeToken<Movie>() {
        }.getType();

        return gson.fromJson(json, type);
    }

    public void saveTvDetail(TvShow movie,String tvId) {
        Gson gson = new Gson();
        String json = gson.toJson(movie);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(tvId, json);
        sharedPreferencesEditor.apply();
    }

    public TvShow getTvDetail(String tvId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String json = String.valueOf(sharedPreferences.getString(tvId, ""));
        Gson gson = new Gson();
        Type type = new TypeToken<Movie>() {
        }.getType();

        return gson.fromJson(json, type);
    }


}
