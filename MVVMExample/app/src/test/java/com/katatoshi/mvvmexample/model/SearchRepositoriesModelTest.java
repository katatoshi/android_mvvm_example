package com.katatoshi.mvvmexample.model;

import com.google.gson.Gson;
import com.katatoshi.mvvmexample.AppApplication;
import com.katatoshi.mvvmexample.DaggerTestAppComponent;
import com.katatoshi.mvvmexample.TestAppComponent;
import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;
import com.katatoshi.mvvmexample.util.Either;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java8.util.concurrent.CompletableFuture;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(Enclosed.class)
public class SearchRepositoriesModelTest {

    public static class 検索結果が0件の場合 {

        private static final String RESPONSE_JSON = "{\n" +
                "  \"total_count\": 0,\n" +
                "  \"incomplete_results\": false,\n" +
                "  \"items\": [\n" +
                "\n" +
                "  ]\n" +
                "}";

        @Inject
        SearchRepositoriesApi searchRepositoriesApi;

        private CompletableFuture<Either<Integer, SearchRepositoriesApi.Result>> completableFuture;

        private SearchRepositoriesModel sut;

        @Before
        public void setUp() throws Exception {
            DaggerTestAppComponent.create().inject(this);

            completableFuture = new CompletableFuture<>();
            when(searchRepositoriesApi.get(anyString(), any(), any())).thenReturn(completableFuture);

            sut = new SearchRepositoriesModel(searchRepositoriesApi);
        }

        @Test
        public void 検索完了後の検索結果リストのサイズは0() {
            sut.search();

            completableFuture.complete(Either.right(new Gson().fromJson(RESPONSE_JSON, SearchRepositoriesApi.Result.class)));

            assertThat(sut.getRepositoryList(), hasSize(0));
        }

        @Test
        public void 初期化直後は検索中ではなく検索を実行すると検索中になり検索が完了すると検索中ではなくなる() throws Exception {
            assertThat(sut.isSearching(), is(false));

            sut.search();

            assertThat(sut.isSearching(), is(true));

            completableFuture.complete(Either.right(new Gson().fromJson(RESPONSE_JSON, SearchRepositoriesApi.Result.class)));

            assertThat(sut.isSearching(), is(false));
        }
    }

    public static class 検索結果が1件の場合 {

        private static final String RESPONSE_JSON = "{\n" +
                "  \"total_count\": 40,\n" +
                "  \"incomplete_results\": false,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": 3081286,\n" +
                "      \"name\": \"Tetris\",\n" +
                "      \"full_name\": \"dtrupenn/Tetris\",\n" +
                "      \"owner\": {\n" +
                "        \"login\": \"dtrupenn\",\n" +
                "        \"id\": 872147,\n" +
                "        \"avatar_url\": \"https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png\",\n" +
                "        \"gravatar_id\": \"\",\n" +
                "        \"url\": \"https://api.github.com/users/dtrupenn\",\n" +
                "        \"received_events_url\": \"https://api.github.com/users/dtrupenn/received_events\",\n" +
                "        \"type\": \"User\"\n" +
                "      },\n" +
                "      \"private\": false,\n" +
                "      \"html_url\": \"https://github.com/dtrupenn/Tetris\",\n" +
                "      \"description\": \"A C implementation of Tetris using Pennsim through LC4\",\n" +
                "      \"fork\": false,\n" +
                "      \"url\": \"https://api.github.com/repos/dtrupenn/Tetris\",\n" +
                "      \"created_at\": \"2012-01-01T00:31:50Z\",\n" +
                "      \"updated_at\": \"2013-01-05T17:58:47Z\",\n" +
                "      \"pushed_at\": \"2012-01-01T00:37:02Z\",\n" +
                "      \"homepage\": \"\",\n" +
                "      \"size\": 524,\n" +
                "      \"stargazers_count\": 1,\n" +
                "      \"watchers_count\": 1,\n" +
                "      \"language\": \"Assembly\",\n" +
                "      \"forks_count\": 0,\n" +
                "      \"open_issues_count\": 0,\n" +
                "      \"master_branch\": \"master\",\n" +
                "      \"default_branch\": \"master\",\n" +
                "      \"score\": 10.309712\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        @Inject
        SearchRepositoriesApi searchRepositoriesApi;

        private CompletableFuture<Either<Integer, SearchRepositoriesApi.Result>> completableFuture;

        private SearchRepositoriesModel sut;

        @Before
        public void setUp() throws Exception {
            DaggerTestAppComponent.create().inject(this);

            completableFuture = new CompletableFuture<>();
            when(searchRepositoriesApi.get(anyString(), any(), any())).thenReturn(completableFuture);

            sut = new SearchRepositoriesModel(searchRepositoriesApi);
        }

        @Test
        public void 検索完了後の検索結果リストのサイズは1() {
            sut.search();

            completableFuture.complete(Either.right(new Gson().fromJson(RESPONSE_JSON, SearchRepositoriesApi.Result.class)));

            assertThat(sut.getRepositoryList(), hasSize(1));
        }

        @Test
        public void 初期化直後は検索中ではなく検索を実行すると検索中になり検索が完了すると検索中ではなくなる() throws Exception {
            assertThat(sut.isSearching(), is(false));

            sut.search();

            assertThat(sut.isSearching(), is(true));

            completableFuture.complete(Either.right(new Gson().fromJson(RESPONSE_JSON, SearchRepositoriesApi.Result.class)));

            assertThat(sut.isSearching(), is(false));
        }
    }

    public static class レスポンスを受け取ったがステータスコードがエラーコードの場合 {

        @Inject
        SearchRepositoriesApi searchRepositoriesApi;

        private CompletableFuture<Either<Integer, SearchRepositoriesApi.Result>> completableFuture;

        private SearchRepositoriesModel sut;

        @Before
        public void setUp() throws Exception {
            DaggerTestAppComponent.create().inject(this);

            completableFuture = new CompletableFuture<>();
            when(searchRepositoriesApi.get(anyString(), any(), any())).thenReturn(completableFuture);

            sut = new SearchRepositoriesModel(searchRepositoriesApi);
        }

        @Test
        public void 検索完了後の検索結果リストのサイズは0() {
            sut.search();

            completableFuture.complete(Either.left(404));

            assertThat(sut.getRepositoryList(), hasSize(0));
        }

        @Test
        public void 初期化直後は検索中ではなく検索を実行すると検索中になり検索が完了すると検索中ではなくなる() throws Exception {
            assertThat(sut.isSearching(), is(false));

            sut.search();

            assertThat(sut.isSearching(), is(true));

            completableFuture.complete(Either.left(404));

            assertThat(sut.isSearching(), is(false));
        }
    }

    public static class 通信に関する例外が発生した場合 {

        @Inject
        SearchRepositoriesApi searchRepositoriesApi;

        private CompletableFuture<Either<Integer, SearchRepositoriesApi.Result>> completableFuture;

        private SearchRepositoriesModel sut;

        @Before
        public void setUp() throws Exception {
            DaggerTestAppComponent.create().inject(this);

            completableFuture = new CompletableFuture<>();
            when(searchRepositoriesApi.get(anyString(), any(), any())).thenReturn(completableFuture);

            sut = new SearchRepositoriesModel(searchRepositoriesApi);
        }

        @Test
        public void 検索完了後の検索結果リストのサイズは0() {
            sut.search();

            completableFuture.completeExceptionally(new Exception());

            assertThat(sut.getRepositoryList(), hasSize(0));
        }

        @Test
        public void 初期化直後は検索中ではなく検索を実行すると検索中になり検索が完了すると検索中ではなくなる() throws Exception {
            assertThat(sut.isSearching(), is(false));

            sut.search();

            assertThat(sut.isSearching(), is(true));

            completableFuture.completeExceptionally(new Exception());

            assertThat(sut.isSearching(), is(false));
        }
    }

    public static class 検索結果が1件存在する状態でもう一度検索するとエラーコードが返ってきた場合 {

        private static final String RESPONSE_JSON = "{\n" +
                "  \"total_count\": 40,\n" +
                "  \"incomplete_results\": false,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": 3081286,\n" +
                "      \"name\": \"Tetris\",\n" +
                "      \"full_name\": \"dtrupenn/Tetris\",\n" +
                "      \"owner\": {\n" +
                "        \"login\": \"dtrupenn\",\n" +
                "        \"id\": 872147,\n" +
                "        \"avatar_url\": \"https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png\",\n" +
                "        \"gravatar_id\": \"\",\n" +
                "        \"url\": \"https://api.github.com/users/dtrupenn\",\n" +
                "        \"received_events_url\": \"https://api.github.com/users/dtrupenn/received_events\",\n" +
                "        \"type\": \"User\"\n" +
                "      },\n" +
                "      \"private\": false,\n" +
                "      \"html_url\": \"https://github.com/dtrupenn/Tetris\",\n" +
                "      \"description\": \"A C implementation of Tetris using Pennsim through LC4\",\n" +
                "      \"fork\": false,\n" +
                "      \"url\": \"https://api.github.com/repos/dtrupenn/Tetris\",\n" +
                "      \"created_at\": \"2012-01-01T00:31:50Z\",\n" +
                "      \"updated_at\": \"2013-01-05T17:58:47Z\",\n" +
                "      \"pushed_at\": \"2012-01-01T00:37:02Z\",\n" +
                "      \"homepage\": \"\",\n" +
                "      \"size\": 524,\n" +
                "      \"stargazers_count\": 1,\n" +
                "      \"watchers_count\": 1,\n" +
                "      \"language\": \"Assembly\",\n" +
                "      \"forks_count\": 0,\n" +
                "      \"open_issues_count\": 0,\n" +
                "      \"master_branch\": \"master\",\n" +
                "      \"default_branch\": \"master\",\n" +
                "      \"score\": 10.309712\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        @Inject
        SearchRepositoriesApi searchRepositoriesApi;

        private CompletableFuture<Either<Integer, SearchRepositoriesApi.Result>> completableFuture;

        private SearchRepositoriesModel sut;

        @Before
        public void setUp() throws Exception {
            DaggerTestAppComponent.create().inject(this);

            completableFuture = new CompletableFuture<>();
            when(searchRepositoriesApi.get(anyString(), any(), any())).thenReturn(completableFuture);

            sut = new SearchRepositoriesModel(searchRepositoriesApi);
            sut.search();

            completableFuture.complete(Either.right(new Gson().fromJson(RESPONSE_JSON, SearchRepositoriesApi.Result.class)));
        }

        @Test
        public void 検索完了後の検索結果リストのサイズは1() {
            sut.search();

            completableFuture.complete(Either.left(404));

            assertThat(sut.getRepositoryList(), hasSize(1));
        }
    }

    public static class 検索結果が1件存在する状態でもう一度検索すると通信に関する例外が発生した場合 {

        private static final String RESPONSE_JSON = "{\n" +
                "  \"total_count\": 40,\n" +
                "  \"incomplete_results\": false,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": 3081286,\n" +
                "      \"name\": \"Tetris\",\n" +
                "      \"full_name\": \"dtrupenn/Tetris\",\n" +
                "      \"owner\": {\n" +
                "        \"login\": \"dtrupenn\",\n" +
                "        \"id\": 872147,\n" +
                "        \"avatar_url\": \"https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png\",\n" +
                "        \"gravatar_id\": \"\",\n" +
                "        \"url\": \"https://api.github.com/users/dtrupenn\",\n" +
                "        \"received_events_url\": \"https://api.github.com/users/dtrupenn/received_events\",\n" +
                "        \"type\": \"User\"\n" +
                "      },\n" +
                "      \"private\": false,\n" +
                "      \"html_url\": \"https://github.com/dtrupenn/Tetris\",\n" +
                "      \"description\": \"A C implementation of Tetris using Pennsim through LC4\",\n" +
                "      \"fork\": false,\n" +
                "      \"url\": \"https://api.github.com/repos/dtrupenn/Tetris\",\n" +
                "      \"created_at\": \"2012-01-01T00:31:50Z\",\n" +
                "      \"updated_at\": \"2013-01-05T17:58:47Z\",\n" +
                "      \"pushed_at\": \"2012-01-01T00:37:02Z\",\n" +
                "      \"homepage\": \"\",\n" +
                "      \"size\": 524,\n" +
                "      \"stargazers_count\": 1,\n" +
                "      \"watchers_count\": 1,\n" +
                "      \"language\": \"Assembly\",\n" +
                "      \"forks_count\": 0,\n" +
                "      \"open_issues_count\": 0,\n" +
                "      \"master_branch\": \"master\",\n" +
                "      \"default_branch\": \"master\",\n" +
                "      \"score\": 10.309712\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        @Inject
        SearchRepositoriesApi searchRepositoriesApi;

        private CompletableFuture<Either<Integer, SearchRepositoriesApi.Result>> completableFuture;

        private SearchRepositoriesModel sut;

        @Before
        public void setUp() throws Exception {
            DaggerTestAppComponent.create().inject(this);

            completableFuture = new CompletableFuture<>();
            when(searchRepositoriesApi.get(anyString(), any(), any())).thenReturn(completableFuture);

            sut = new SearchRepositoriesModel(searchRepositoriesApi);
            sut.search();

            completableFuture.complete(Either.right(new Gson().fromJson(RESPONSE_JSON, SearchRepositoriesApi.Result.class)));
        }

        @Test
        public void 検索完了後の検索結果リストのサイズは1() {
            sut.search();

            completableFuture.completeExceptionally(new Exception());

            assertThat(sut.getRepositoryList(), hasSize(1));
        }
    }
}