package com.shamanland.facebook.likebutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.shamanland.facebook.likebutton.FacebookLikeButton.OnPageUrlChangeListener;

public class FacebookLikePlugin extends LinearLayout {
    private int mLikeId;
    private int mBoxId;

    public FacebookLikePlugin(Context context) {
        super(context);
        init(null);
    }

    public FacebookLikePlugin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FacebookLikePlugin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        Context c = getContext();
        if (c == null) {
            return;
        }

        TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.FacebookLikePlugin);
        if (a == null) {
            return;
        }

        try {
            mLikeId = a.getResourceId(R.styleable.FacebookLikePlugin_likeId, R.id.com_facebook_like);
            mBoxId = a.getResourceId(R.styleable.FacebookLikePlugin_boxId, R.id.com_facebook_like_box);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        final View like = findViewById(mLikeId);
        final View box = findViewById(mBoxId);

        if (like instanceof FacebookLikeButton && box instanceof FacebookLikeBox) {
            ((FacebookLikeButton) like).setOnPageUrlChangeListener(new OnPageUrlChangeListener() {
                @Override
                public void onPageUrlChanged(String newValue) {
                    ((FacebookLikeBox) box).setPageUrl(newValue);
                }
            });

            box.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    like.performClick();
                }
            });
        }
    }
}
