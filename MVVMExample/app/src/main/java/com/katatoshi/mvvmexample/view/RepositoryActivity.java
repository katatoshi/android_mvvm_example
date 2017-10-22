package com.katatoshi.mvvmexample.view;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.katatoshi.mvvmexample.AppApplication;
import com.katatoshi.mvvmexample.R;
import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;
import com.katatoshi.mvvmexample.databinding.ActivityRepositoryBinding;
import com.katatoshi.mvvmexample.viewmodel.RepositoryViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

/**
 * GitHub リポジトリ Activity。
 */
public class RepositoryActivity extends AppCompatActivity {

    private static final String KEY_ITEM = "KEY_ITEM";

    public static void show(Activity activity, SearchRepositoriesApi.Result.Item item) {
        Intent intent = new Intent(activity, RepositoryActivity.class);
        intent.putExtra(KEY_ITEM, item);
        activity.startActivity(intent);
    }

    private ActivityRepositoryBinding binding;

    @Inject
    RepositoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppApplication) getApplication()).getComponent()
                .repositoryActivityComponentBuilder()
                .item((SearchRepositoriesApi.Result.Item) getIntent().getSerializableExtra(KEY_ITEM))
                .build()
                .inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository);

        binding.setViewModel(this.viewModel);

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.registerEventBus(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        viewModel.unregisterEventBus(this);
    }

    //region EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowBrowserEvent(RepositoryViewModel.ShowBrowserEvent event) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.url));
        startActivity(intent);
    }
    //endregion
}
