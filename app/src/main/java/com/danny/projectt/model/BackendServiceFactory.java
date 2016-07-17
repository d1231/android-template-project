package com.danny.projectt.model;

import com.danny.projectt.model.objects.Player;
import com.danny.projectt.model.objects.Stats;
import com.danny.projectt.model.objects.TeamHistory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class BackendServiceFactory {

    static final String BASE_URL = "http://projectt-mydanny.rhcloud.com/";

    public static BackendService create() {

        final HttpLoggingInterceptor.Logger logger = message -> Timber.tag("OkHttp").d(message);

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger)
                .setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new AutoValueTypeAdapterFactory())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient)
                .build();

        return retrofit.create(BackendService.class);
    }

    public static class AutoValueTypeAdapterFactory implements TypeAdapterFactory {

        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<? super T> rawType = type.getRawType();

            if (rawType.equals(Player.class)) {
                return (TypeAdapter<T>) Player.typeAdapter(gson);
            } else if (rawType.equals(TeamHistory.class)) {
                return (TypeAdapter<T>) TeamHistory.typeAdapter(gson);
            } else if (rawType.equals(Stats.class)) {
                return (TypeAdapter<T>) Stats.typeAdapter(gson);
            }

            return null;
        }
    }
}
