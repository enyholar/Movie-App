package com.behruz.magmovie.network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mishael.harry on 3/30/2018.
 */

public class NetworkClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient;

//    public static Retrofit getClient(){
//        if (okHttpClient == null){
//            initOkHttp();
//        }
//
//        if (retrofit == null){
//            Gson builder = new GsonBuilder()
//                    .serializeNulls()
//                    .registerTypeAdapter(BaseResponse.class, new Deserializer<BaseResponse>())
//                    .create();
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .client(okHttpClient)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create(builder))
//                    .build();
//        }
//        return retrofit;
//    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

//    private static void initOkHttp(){
//        int REQUEST_TIMEOUT = 120;
//        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        if (BuildConfig.DEBUG){
//            httpClient.addInterceptor(interceptor);
//        }
//
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//                Request.Builder requestBuilder = original.newBuilder()
//                        .addHeader("Accept", "application/json")
//                        .addHeader("Content-Type", "application/json");
//
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        });
//
//        okHttpClient = httpClient.build();
//    }

    public static NetworkInterface getNetworkInterface(){
        return NetworkClient.getClient().create(NetworkInterface.class);
    }
}
