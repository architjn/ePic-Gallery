package com.architjn.epic.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ImageItemDecoration extends RecyclerView.ItemDecoration {

    private int space, columns, height;

    public ImageItemDecoration(int space, int columns, int height) {
        this.space = space;
        this.columns = columns;
        this.height = height;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildPosition(view);
        if (pos == 0) {
            outRect.right = space;
        } else if ((pos + 1) % columns == 0) {
            outRect.left = space;
        } else if (pos % columns == 0) {
            outRect.right = space;
        }

        if (pos + 1 > columns)
            outRect.top = space;
        else
            outRect.top = height;
    }

}