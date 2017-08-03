package io.github.datwheat.juwan.utils;

import android.support.v4.graphics.ColorUtils;

public class ArtUtils {
    public static boolean isDark(int color) {
        return ColorUtils.calculateLuminance(color) < 0.5f;
    }
}
