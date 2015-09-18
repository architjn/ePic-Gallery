package com.architjn.epic.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

public class NoScrollGridLayoutManager extends GridLayoutManager {

    public NoScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);

    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

}