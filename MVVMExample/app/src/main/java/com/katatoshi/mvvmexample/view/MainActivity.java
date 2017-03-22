package com.katatoshi.mvvmexample.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.katatoshi.mvvmexample.R;
import com.katatoshi.mvvmexample.databinding.ActivityMainBinding;
import com.katatoshi.mvvmexample.util.databinding.recyclerview.DataBindingRecyclerViewUtil;
import com.katatoshi.mvvmexample.util.databinding.recyclerview.OnItemClickListener;
import com.katatoshi.mvvmexample.util.databinding.recyclerview.OnItemLongClickListener;
import com.katatoshi.mvvmexample.viewmodel.MainViewModel;

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

        DataBindingRecyclerViewUtil.bind(binding.recyclerView, viewModel.sampleList, BR.viewModel, R.layout.item_sample,
                new OnItemClickListener<String>() {
                    @Override
                    public void onClick(String item) {
                        viewModel.showClickMessage(item);
                    }
                },
                new OnItemLongClickListener<String>() {
                    @Override
                    public void onLongClick(String item) {
                        viewModel.showLongClickMessage(item);
                    }
                }
        );

        setSupportActionBar(binding.toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.addOnPropertyChangedCallback();

        viewModel.load();
    }

    @Override
    protected void onPause() {
        super.onPause();

        viewModel.removeOnPropertyChangedCallback();
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
        Snackbar.make(binding.fab, message, Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.changeMainText();
                    }
                })
                .show();
    }

    @Override
    public void showClickMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLongClickMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    //endregion
}
