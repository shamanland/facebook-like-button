package com.shamanland.facebook.likebutton;

import android.graphics.Path;
import android.graphics.RectF;

public class CalloutPath extends Path {
    public static final int MARKER_NONE = 0x0;
    public static final int MARKER_LEFT = 0x1;
    public static final int MARKER_TOP = 0x2;
    public static final int MARKER_RIGHT = 0x4;
    public static final int MARKER_BOTTOM = 0x8;
    public static final int MARKER_ALL = 0xf;

    private final RectF oval = new RectF();

    /**
     * @param m marker
     * @param w width
     * @param h height
     * @param s stroke thickness
     * @param r corners radius
     */
    public void build(int m, float w, float h, float s, float r) {
        int fl = factor(m, MARKER_LEFT);
        int ft = factor(m, MARKER_TOP);
        int fr = factor(m, MARKER_RIGHT);
        int fb = factor(m, MARKER_BOTTOM);

        float x0 = s + 0f;
        float x1 = s + r * fl;
        float x2 = s + r + r * fl;
        float x3 = w / 2f - r;
        float x4 = w / 2f;
        float x5 = w / 2f + r;
        float x6 = w - s - r - r * fr;
        float x7 = w - s - r * fr;
        float x8 = w - s;
        float y0 = s + 0f;
        float y1 = s + r * ft;
        float y2 = s + r + r * ft;
        float y3 = h / 2f - r;
        float y4 = h / 2f;
        float y5 = h / 2f + r;
        float y6 = h - s - r - r * fb;
        float y7 = h - s - r * fb;
        float y8 = h - s;

        reset();

        moveTo(x1, y2);

        oval.set(x2 - r, y2 - r, x2 + r, y2 + r);
        arcTo(oval, 180f, 90f);

        if (ft != 0) {
            lineTo(x3, y1);
            lineTo(x4, y0);
            lineTo(x5, y1);
        }

        lineTo(x6, y1);

        oval.set(x6 - r, y2 - r, x6 + r, y2 + r);
        arcTo(oval, 270f, 90f);

        if (fr != 0) {
            lineTo(x7, y3);
            lineTo(x8, y4);
            lineTo(x7, y5);
        }

        lineTo(x7, y6);

        oval.set(x6 - r, y6 - r, x6 + r, y6 + r);
        arcTo(oval, 0f, 90f);

        if (fb != 0) {
            lineTo(x5, y7);
            lineTo(x4, y8);
            lineTo(x3, y7);
        }

        lineTo(x2, y7);

        oval.set(x2 - r, y6 - r, x2 + r, y6 + r);
        arcTo(oval, 90f, 90f);

        if (fl != 0) {
            lineTo(x1, y5);
            lineTo(x0, y4);
            lineTo(x1, y3);
        }

        close();
    }

    public static int factor(int marker, int mask) {
        return (marker & mask) != 0 ? 1 : 0;
    }
}
