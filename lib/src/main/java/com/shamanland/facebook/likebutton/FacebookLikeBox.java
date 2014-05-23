package com.shamanland.facebook.likebutton;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.util.AttributeSet;
import android.widget.Button;

import com.shamanland.facebook.likebutton.FacebookLinkStatProcessor.Result;

public class FacebookLikeBox extends Button {
    private static final HandlerThread THREAD;

    static {
        THREAD = new HandlerThread(FacebookLikeBox.class.getSimpleName(), Process.THREAD_PRIORITY_LOWEST);
        THREAD.start();
    }

    private Handler mHandler;
    private FacebookLinkStatProcessor mProcessor;
    private String mUrl;
    private boolean mAttachedToWindow;

    private CalloutPath mPath;
    private ShapeDrawable mFill;
    private ShapeDrawable mStroke;
    private float mCornerRadius;
    private float mStrokeWidth;

    public void setProcessor(FacebookLinkStatProcessor processor) {
        mProcessor = processor;
    }

    public void setUrl(String url) {
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
        init();
    }

    public FacebookLikeBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FacebookLikeBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mProcessor = new FacebookLinkStatProcessor();

        mHandler = new Handler(THREAD.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                processUrl((String) msg.obj);
                return true;
            }
        });

        mStrokeWidth = getResources().getDimension(R.dimen.com_facebook_like_box_stroke_width);
        mCornerRadius = getResources().getDimension(R.dimen.com_facebook_like_box_corners_radius);

        mPath = new CalloutPath();
        mFill = new ShapeDrawable();
        mFill.getPaint().setStyle(Paint.Style.FILL);
        mFill.getPaint().setColor(getResources().getColor(R.color.com_facebook_like_box_background_color));
        mStroke = new ShapeDrawable();
        mStroke.getPaint().setStyle(Paint.Style.STROKE);
        mStroke.getPaint().setColor(getResources().getColor(R.color.com_facebook_like_box_text_color));
        mStroke.getPaint().setAntiAlias(true);
        mStroke.getPaint().setStrokeWidth(mStrokeWidth);

        Drawable drawable = new LayerDrawable(new Drawable[]{mFill, mStroke});
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
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
        mPath.build(CalloutPath.MARKER_LEFT, w, h, mStrokeWidth, mCornerRadius);
        PathShape shape = new PathShape(mPath, w, h);
        mFill.setShape(shape);
        mStroke.setShape(shape);
    }

    protected void onUrlChanged(String oldValue, String newValue) {
        if (oldValue != null) {
            mHandler.removeMessages(0, oldValue);
        }

        if (newValue != null) {
            Message msg = Message.obtain();
            msg.obj = newValue;
            mHandler.sendMessage(msg);
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
            // ignore
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
