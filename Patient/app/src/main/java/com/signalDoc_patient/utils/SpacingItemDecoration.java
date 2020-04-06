package com.signalDoc_patient.utils;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import androidx.recyclerview.widget.RecyclerView.State;

public class SpacingItemDecoration extends ItemDecoration {
    private int horizontalSpacing;
    private int verticalSpacing;

    public SpacingItemDecoration(int horizontalSpacing, int verticalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        outRect.left = this.horizontalSpacing / 2;
        outRect.right = this.horizontalSpacing / 2;
        outRect.top = this.verticalSpacing / 2;
        outRect.bottom = this.verticalSpacing / 2;
    }
}
