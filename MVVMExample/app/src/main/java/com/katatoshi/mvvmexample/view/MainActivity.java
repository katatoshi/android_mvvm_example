package com.katatoshi.mvvmexample.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.katatoshi.mvvmexample.R;
import com.katatoshi.mvvmexample.databinding.ActivityMainBinding;
import com.katatoshi.mvvmexample.util.databinding.recyclerview.DataBindingRecyclerViewUtil;
import com.katatoshi.mvvmexample.util.databinding.recyclerview.OnItemClickListener;
import com.katatoshi.mvvmexample.viewmodel.MainViewModel;
import com.katatoshi.mvvmexample.viewmodel.RepositoryViewModel;

/**
 * メインの Activity。
 */
public class MainActivity extends AppCompatActivity implements MainViewModel.Delegate {

    private ActivityMainBinding binding;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new MainViewModel(this);
        binding.setViewModel(viewModel);

        DataBindingRecyclerViewUtil.bind(binding.recyclerView, viewModel.repositoryViewModelList, BR.viewModel, R.layout.item_sample,
                new OnItemClickListener<RepositoryViewModel>() {
                    @Override
                    public void onClick(RepositoryViewModel item) {
                        viewModel.showRepositoryLanguage(item);
                    }
                }
        );

        viewModel.search();

        setSupportActionBar(binding.toolbar);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //region 移譲されたメソッドたちの実装。
    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    //endregion
}
