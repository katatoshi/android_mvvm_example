package com.katatoshi.mvvmexample.util.databinding.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.katatoshi.mvvmexample.R;

import java.util.Collection;

/**
 * データバインディング対応 RecyclerViewAdapter。セクション分け非対応。
 * <p>
 * 参考： https://github.com/radzio/android-data-binding-recyclerview
 */
public class SimpleRecyclerViewAdapter<T> extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    public SimpleRecyclerViewAdapter(@NonNull Collection<T> itemCollection, @NonNull VariableLayoutBinder binder, @Nullable OnItemClickListener<T> onItemClickListener, @Nullable OnItemLongClickListener<T> onItemLongClickListener) {
        onListChangedCallback = new ObservableList.OnListChangedCallback<ObservableList<T>>() {

            @Override
            public void onChanged(ObservableList<T> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        };

        this.binder = binder;

        this.onItemClickListener = onItemClickListener;

        this.onItemLongClickListener = onItemLongClickListener;

        if (itemCollection instanceof ObservableList) {
            itemList = (ObservableList<T>) itemCollection;
        } else {
            itemList = new ObservableArrayList<>();
            itemList.addAll(itemCollection);
        }
        notifyItemRangeInserted(0, itemList.size());
        itemList.addOnListChangedCallback(onListChangedCallback);
    }

    private final ObservableList.OnListChangedCallback<ObservableList<T>> onListChangedCallback;

    private final ObservableList<T> itemList;

    private final VariableLayoutBinder binder;

    private final OnItemClickListener<T> onItemClickListener;

    private final OnItemLongClickListener<T> onItemLongClickListener;

    private LayoutInflater inflater;

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (itemList == null) {
            return;
        }

        itemList.removeOnListChangedCallback(onListChangedCallback);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        return new ViewHolder(DataBindingUtil.inflate(inflater, viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = itemList.get(position);
        holder.binding.setVariable(binder.variableId, item);
        holder.binding.getRoot().setTag(R.id.key_simple_recycler_view_adapter_item, item);

        if (onItemClickListener != null) {
            holder.binding.getRoot().setOnClickListener(this);
        }

        if (onItemLongClickListener != null) {
            holder.binding.getRoot().setOnLongClickListener(this);
        }

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        return binder.layoutId;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener == null) {
            return;
        }

        onItemClickListener.onClick((T) v.getTag(R.id.key_simple_recycler_view_adapter_item));
    }

    @Override
    public boolean onLongClick(View v) {
        if (onItemLongClickListener == null) {
            return false;
        }

        onItemLongClickListener.onLongClick((T) v.getTag(R.id.key_simple_recycler_view_adapter_item));
        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        final ViewDataBinding binding;
    }
}
