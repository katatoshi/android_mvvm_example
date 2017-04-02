package com.katatoshi.mvvmexample.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.katatoshi.mvvmexample.BR;
import com.katatoshi.mvvmexample.R;
import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;
import com.katatoshi.mvvmexample.databinding.ActivitySearchRepositoriesBinding;
import com.katatoshi.mvvmexample.util.databinding.recyclerview.DataBindingRecyclerViewUtil;
import com.katatoshi.mvvmexample.viewmodel.SearchRepositoriesViewModel;

/**
 * GitHub リポジトリ検索 Activity。
 */
public class SearchRepositoriesActivity extends AppCompatActivity implements SearchRepositoriesViewModel.Delegate {

    private ActivitySearchRepositoriesBinding binding;

    private SearchRepositoriesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_repositories);

        viewModel = new SearchRepositoriesViewModel(this);
        binding.setViewModel(viewModel);

        DataBindingRecyclerViewUtil.bind(binding.recyclerView, viewModel.repositoryViewModelList, BR.viewModel, R.layout.item_repository,
                item -> viewModel.showRepository(item)
        );

        setSupportActionBar(binding.toolbar);

        // 初回のみ自動で検索する
        viewModel.search();
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        viewModel.onPause();
    }


    //region 委譲されたメソッドたちの実装。
    @Override
    public void showRepository(SearchRepositoriesApi.Result.Item item) {
        RepositoryActivity.show(this, item);
    }
    //endregion
}
