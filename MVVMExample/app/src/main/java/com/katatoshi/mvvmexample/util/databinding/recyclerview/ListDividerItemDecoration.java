package com.katatoshi.mvvmexample.util.databinding.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.katatoshi.mvvmexample.R;

/**
 * RecyclerView 用 divider。
 */
public class ListDividerItemDecoration extends RecyclerView.ItemDecoration {

    public ListDividerItemDecoration(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        divider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    private final Drawable divider;

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom() + ((RecyclerView.LayoutParams) child.getLayoutParams()).bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, divider.getIntrinsicHeight());
    }

    public static class ListDividerItemDecorationBindingAdapter {

        private static void addListDividerItemDecoration(RecyclerView recyclerView) {
            if (recyclerView.getTag(R.id.key_list_divider_item_decoration) != null) {
                return;
            }

            ListDividerItemDecoration itemDecoration = new ListDividerItemDecoration(recyclerView.getContext());
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setTag(R.id.key_list_divider_item_decoration, itemDecoration);
        }

        private static void removeListDividerItemDecoration(RecyclerView recyclerView) {
            ListDividerItemDecoration itemDecoration = (ListDividerItemDecoration) recyclerView.getTag(R.id.key_list_divider_item_decoration);
            if (itemDecoration == null) {
                return;
            }

            recyclerView.removeItemDecoration(itemDecoration);
            recyclerView.setTag(R.id.key_list_divider_item_decoration, null);
        }

        @BindingAdapter("listDividerEnabled")
        public static void setListDividerEnabled(RecyclerView recyclerView, boolean enabled) {
            if (enabled) {
                addListDividerItemDecoration(recyclerView);
            } else {
                removeListDividerItemDecoration(recyclerView);
            }
        }
    }
}
