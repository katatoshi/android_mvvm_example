package com.katatoshi.mvvmexample.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.katatoshi.mvvmexample.AppApplication;
import com.katatoshi.mvvmexample.BR;
import com.katatoshi.mvvmexample.R;
import com.katatoshi.mvvmexample.databinding.ActivitySearchRepositoriesBinding;
import com.katatoshi.mvvmexample.util.databinding.recyclerview.DataBindingRecyclerViewUtil;
import com.katatoshi.mvvmexample.viewmodel.SearchRepositoriesViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * GitHub リポジトリ検索 Activity。
 */
public class SearchRepositoriesActivity extends AppCompatActivity {

    private ActivitySearchRepositoriesBinding binding;

    @Inject
    SearchRepositoriesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppApplication) getApplication()).getComponent()
                .searchRepositoriesActivityComponentBuilder()
                .build()
                .inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_repositories);

        binding.setViewModel(viewModel);

        // RecyclerView と ObservableList のバインド。リストアイテムの Variable ID, Layout ID も指定。必要なら ClickListener なども指定。
        DataBindingRecyclerViewUtil.bind(binding.recyclerView, viewModel.repositoryViewModelList, BR.viewModel, R.layout.item_repository,
                item -> viewModel.showRepository(item)
        );

        setSupportActionBar(binding.toolbar);

        // 初回のみ自動で検索する
        viewModel.search();
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.registerEventBus(this);
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

    @Override
    protected void onStop() {
        super.onStop();

        viewModel.unregisterEventBus(this);
    }


    //region EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowRepositoryEvent(SearchRepositoriesViewModel.ShowRepositoryEvent event) {
        RepositoryActivity.show(this, event.item);
    }
    //endregion
}
