package com.katatoshi.mvvmexample.api.github;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * api.github パッケージのクラスのモジュール。
 */
@Module
public class GitHubApiModule {

    @Singleton
    @Provides
    @Named("GitHubApi")
    public static Retrofit provideGitHubApiRetrofit() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        //region header 設定
        clientBuilder.addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                .header("Accept", "application/vnd.github.v3+json")
                .build()));
        //endregion

        //region ログ設定
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        clientBuilder.addInterceptor(interceptor);
        //endregion

        return new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
                ))
                .client(clientBuilder.build())
                .build();
    }

    @Singleton
    @Provides
    public static SearchRepositoriesApi provideSearchRepositoriesApi(@Named("GitHubApi") Retrofit gitHubApiRetrofit) {
        return new SearchRepositoriesApi(gitHubApiRetrofit);
    }
};
