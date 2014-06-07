package com.shamanland.facebook.likebutton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.shamanland.facebook.likebutton.FacebookLinkStatProcessor.Result;

import static com.shamanland.facebook.likebutton.BuildConfig.LOGGING;
import static com.shamanland.facebook.likebutton.CalloutPath.MARKER_BOTTOM;
import static com.shamanland.facebook.likebutton.CalloutPath.MARKER_LEFT;
import static com.shamanland.facebook.likebutton.CalloutPath.MARKER_NONE;
import static com.shamanland.facebook.likebutton.CalloutPath.MARKER_RIGHT;
import static com.shamanland.facebook.likebutton.CalloutPath.MARKER_TOP;
import static com.shamanland.facebook.likebutton.CalloutPath.factor;

public class FacebookLikeBox extends Button {
    private static final String LOG_TAG = FacebookLikeBox.class.getSimpleName();
    private static final Looper BACKGROUND;

    static {
        HandlerThread thread = new HandlerThread(FacebookLikeBox.class.getSimpleName(), Process.THREAD_PRIORITY_LOWEST);
        thread.start();
        BACKGROUND = thread.getLooper();
    }

    private Handler mHandler;
    private FacebookLinkStatProcessor mProcessor;
    private String mUrl;
    private boolean mAttachedToWindow;

    private CalloutPath mPath;
    private ShapeDrawable mFill;
    private ShapeDrawable mStroke;
    private float mCornerRadius;
    private int mCalloutMarker;

    public void setProcessor(FacebookLinkStatProcessor processor) {
        mProcessor = processor;
    }

    public void setPageUrl(String url) {
        String old = mUrl;
        mUrl = url;

        if (old == null && url != null || old != null && !old.equals(url)) {
            onUrlChanged(old, url);
        }
    }

    @Override
    public boolean isAttachedToWindow() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return mAttachedToWindow;
        } else {
            return super.isAttachedToWindow();
        }
    }

    public FacebookLikeBox(Context context) {
        super(context);
        init(null);
    }

    public FacebookLikeBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FacebookLikeBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mProcessor = new FacebookLinkStatProcessor();

        mHandler = new Handler(BACKGROUND, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                processUrl((String) msg.obj);
                return true;
            }
        });

        if (attrs == null) {
            return;
        }

        Context c = getContext();
        if (c == null) {
            return;
        }

        TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.FacebookLikeBox);
        if (a == null) {
            return;
        }

        Resources r = getResources();
        if (r == null) {
            return;
        }

        try {
            mPath = new CalloutPath();
            mFill = new ShapeDrawable();
            mFill.getPaint().setStyle(Paint.Style.FILL);
            mFill.getPaint().setColor(a.getColor(R.styleable.FacebookLikeBox_boxFillColor, r.getColor(R.color.com_facebook_like_box_background_color)));
            mStroke = new ShapeDrawable();
            mStroke.getPaint().setStyle(Paint.Style.STROKE);
            mStroke.getPaint().setColor(a.getColor(R.styleable.FacebookLikeBox_boxStrokeColor, r.getColor(R.color.com_facebook_like_box_text_color)));
            mStroke.getPaint().setAntiAlias(true);
            mStroke.getPaint().setStrokeWidth(a.getDimension(R.styleable.FacebookLikeBox_boxStrokeWidth, r.getDimension(R.dimen.com_facebook_like_box_stroke_width)));
            mCornerRadius = a.getDimension(R.styleable.FacebookLikeBox_boxCornersRadius, r.getDimension(R.dimen.com_facebook_like_corners_radius));
            mCalloutMarker = a.getInt(R.styleable.FacebookLikeBox_calloutMarker, MARKER_NONE);

            initBackground();
        } finally {
            a.recycle();
        }
    }

    @SuppressWarnings("deprecation")
    private void initBackground() {
        int pl = (int) (getPaddingLeft() + factor(mCalloutMarker, MARKER_LEFT) * mCornerRadius);
        int pt = (int) (getPaddingTop() + factor(mCalloutMarker, MARKER_TOP) * mCornerRadius);
        int pr = (int) (getPaddingRight() + factor(mCalloutMarker, MARKER_RIGHT) * mCornerRadius);
        int pb = (int) (getPaddingBottom() + factor(mCalloutMarker, MARKER_BOTTOM) * mCornerRadius);

        Drawable drawable = new LayerDrawable(new Drawable[]{mFill, mStroke});
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }

        setPadding(pl, pt, pr, pb);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAttachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAttachedToWindow = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mPath.build(mCalloutMarker, w, h, mStroke.getPaint().getStrokeWidth(), mCornerRadius);
        PathShape shape = new PathShape(mPath, w, h);
        mFill.setShape(shape);
        mStroke.setShape(shape);
    }

    protected void onUrlChanged(String oldValue, String newValue) {
        setText(R.string.com_facebook_like_box_text_default);

        if (oldValue != null) {
            mHandler.removeMessages(0, oldValue);
        }

        if (newValue != null) {
            Message msg = Message.obtain();
            if (msg != null) {
                msg.obj = newValue;
                mHandler.sendMessage(msg);
            }
        }
    }

    /**
     * Background thread
     */
    protected void processUrl(final String url) {
        try {
            final Result result = mProcessor.processUrl(url);
            post(new Runnable() {
                @Override
                public void run() {
                    if (isAttachedToWindow()) {
                        postProcessUrl(url, result);
                    }
                }
            });
        } catch (Throwable ex) {
            if (LOGGING) {
                Log.wtf(LOG_TAG, ex);
            }
        }
    }

    protected void postProcessUrl(String url, Result result) {
        if (url.equals(mUrl)) {
            onUrlProcessed(result);
        }
    }

    protected void onUrlProcessed(Result result) {
        setText(prettyNumber(result.shares));
    }

    protected String prettyNumber(long number) {
        if (number > 1000000000L) {
            return number / 1000000000L + "." + (number % 1000000000L) / 100000000L + "b";
        } else if (number > 1000000L) {
            return number / 1000000L + "." + (number % 1000000L) / 100000L + "m";
        } else if (number > 1000L) {
            return number / 1000L + "." + (number % 1000L) / 100L + "k";
        } else {
            return String.valueOf(number);
        }
    }
}
