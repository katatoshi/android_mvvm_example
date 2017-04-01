package com.katatoshi.mvvmexample.view;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.katatoshi.mvvmexample.R;
import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;
import com.katatoshi.mvvmexample.databinding.ActivityRepositoryBinding;
import com.katatoshi.mvvmexample.viewmodel.RepositoryViewModel;

/**
 * GitHub リポジトリ Activity。
 */
public class RepositoryActivity extends AppCompatActivity implements RepositoryViewModel.Delegate {

    private static final String KEY_ITEM = "KEY_ITEM";

    public static void show(Activity activity, SearchRepositoriesApi.Result.Item item) {
        Intent intent = new Intent(activity, RepositoryActivity.class);
        intent.putExtra(KEY_ITEM, item);
        activity.startActivity(intent);
    }

    private ActivityRepositoryBinding binding;

    private RepositoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository);

        viewModel = new RepositoryViewModel(this, (SearchRepositoriesApi.Result.Item) getIntent().getSerializableExtra(KEY_ITEM));
        binding.setViewModel(this.viewModel);

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }


    //region 委譲されたメソッドたちの実装。
    @Override
    public void showBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    //endregion
}
