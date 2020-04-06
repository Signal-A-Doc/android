/*
 * @copyright : ToXSL Technologies Pvt. Ltd. < www.toxsl.com >
 * @author     : Shiv Charan Panjeta < shiv@toxsl.com >
 * All Rights Reserved.
 * Proprietary and confidential :  All information contained herein is, and remains
 * the property of ToXSL Technologies Pvt. Ltd. and its partners.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.signalDoc_doctor.utils;

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
