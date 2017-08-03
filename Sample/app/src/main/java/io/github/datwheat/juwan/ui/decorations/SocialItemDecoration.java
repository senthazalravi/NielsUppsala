package io.github.datwheat.juwan.ui.decorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SocialItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SocialItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (position < 2) {
            outRect.top = space * 2;
        } else {
            outRect.top = space;
        }

        if (position % 2 == 0) {
            outRect.left = space * 2;
            outRect.right = space;
        } else {
            outRect.left = space;
            outRect.right = space * 2;
        }
    }
}
