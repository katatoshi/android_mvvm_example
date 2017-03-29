package com.katatoshi.mvvmexample.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.katatoshi.mvvmexample.R;
import com.katatoshi.mvvmexample.databinding.ActivitySearchRepositoriesBinding;
import com.katatoshi.mvvmexample.util.databinding.recyclerview.DataBindingRecyclerViewUtil;
import com.katatoshi.mvvmexample.util.databinding.recyclerview.OnItemClickListener;
import com.katatoshi.mvvmexample.viewmodel.SearchRepositoriesViewModel;
import com.katatoshi.mvvmexample.viewmodel.RepositoryViewModel;

/**
 * GitHub リポジトリ検索 Model。
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
                new OnItemClickListener<RepositoryViewModel>() {
                    @Override
                    public void onClick(RepositoryViewModel item) {
                        viewModel.showRepositoryLanguage(item);
                    }
                }
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


    //region 移譲されたメソッドたちの実装。
    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    //endregion
}
