package com.katatoshi.mvvmexample.api.github;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.katatoshi.mvvmexample.util.Either;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import java8.util.concurrent.CompletableFuture;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * GitHub リポジトリ検索 API。
 */
@Singleton
public class SearchRepositoriesApi {

    @Inject
    SearchRepositoriesApi(Retrofit gitHubApiRetrofit) {
        api = gitHubApiRetrofit.create(Api.class);
    }

    private final Api api;

    public CompletableFuture<Either<Integer, Result>> get(@Nullable String q, @Nullable Sort sort, @Nullable Order order) {
        final CompletableFuture<Either<Integer, Result>> completableFuture = new CompletableFuture<>();

        Map<String, String> parameters = new HashMap<>();
        if (q != null) {
            parameters.put("q", q);
        }
        if (sort != null) {
            parameters.put("sort", sort.parameterValue);
        }
        if (order != null) {
            parameters.put("order", order.parameterValue);
        }

        api.get(parameters).enqueue(new Callback<Result>() {

            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                if (response.isSuccessful()) {
                    completableFuture.complete(Either.<Integer, Result>right(response.body()));
                } else {
                    completableFuture.complete(Either.<Integer, Result>left(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                completableFuture.completeExceptionally(t);
            }
        });

        return completableFuture;
    }

    private interface Api {

        @GET("/search/repositories")
        Call<Result> get(@QueryMap(encoded = true) Map<String, String> parameters);
    }

    public enum Sort {

        STARS("stars"),

        FORKS("forks"),

        UPDATED("updated");

        Sort(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public final String parameterValue;
    }

    public enum Order {

        ASC("asc"),

        DESC("desc");

        Order(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public final String parameterValue;
    }

    public static class Result {

        public int totalCount;

        public boolean incompleteResults;

        public List<Item> items;

        public static class Item implements Serializable {

            public int id;

            public String name;

            public String fullName;

            public Owner owner;

            public static class Owner implements Serializable {

                public String login;

                public int id;

                public String avatarUrl;

                public String gravatarId;

                public String url;

                public String receivedEventsUrl;

                public String type;
            }

            @SerializedName("private")
            public boolean privateRepository;

            public String htmlUrl;

            public String description;

            public boolean fork;

            public String url;

            public String createdAt;

            public String updatedAt;

            public String pushedAt;

            public String homepage;

            public int size;

            public int stargazersCount;

            public int watchersCount;

            public String language;

            public int forkCount;

            public int openIssuesCount;

            public String masterBranch;

            public String defaultBranch;

            public float score;
        }
    }
}
