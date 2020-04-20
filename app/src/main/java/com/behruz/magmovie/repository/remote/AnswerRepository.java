package com.behruz.magmovie.repository.remote;

import android.app.Application;

import com.behruz.magmovie.network.NetworkInterface;
import com.behruz.magmovie.network.NetworkUtil;

public class AnswerRepository {

    private NetworkInterface networkInterface;
    private String token;

    public AnswerRepository(Application application) {
        networkInterface = NetworkUtil.getNetworkInterface();
//        AppSettings appSettings = AppSettings.getInstance(application);
//        token = Const.BEARER + appSettings.getLogin().getToken();
    }

//    public Single<BaseResponse> submitAnswer(){
//        return networkInterface.getMovieGenres(Constant.API_KEYS);
//    }
}
