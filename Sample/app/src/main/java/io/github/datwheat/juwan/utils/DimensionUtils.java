package io.github.datwheat.juwan.utils;

import android.content.Context;

public class DimensionUtils {
    public static int pxToDp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px * density);
    }
}
