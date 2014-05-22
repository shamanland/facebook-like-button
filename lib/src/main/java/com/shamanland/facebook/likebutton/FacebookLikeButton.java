package com.shamanland.facebook.likebutton;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class FacebookLikeButton extends Button {
    private String mUrl;
    private String mTitle;
    private String mText;
    private Bitmap mPicture;
    private String mAppId;
    private int mContentViewId;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Bitmap getPicture() {
        return mPicture;
    }

    public void setPicture(Bitmap picture) {
        mPicture = picture;
    }

    public String getAppId() {
        return mAppId;
    }

    public void setAppId(String appId) {
        mAppId = appId;
    }

    public int getContentViewId() {
        return mContentViewId;
    }

    public void setContentViewId(int contentViewId) {
        mContentViewId = contentViewId;
    }

    public FacebookLikeButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public FacebookLikeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public FacebookLikeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FacebookLikeButton, 0, 0);
        if (a == null) {
            return;
        }

        try {
            mUrl = a.getString(R.styleable.FacebookLikeButton_pageUrl);
            mTitle = a.getString(R.styleable.FacebookLikeButton_pageTitle);
            mText = a.getString(R.styleable.FacebookLikeButton_pageText);

            int pictureId = a.getResourceId(R.styleable.FacebookLikeButton_pagePicture, 0);
            if (pictureId != 0) {
                mPicture = BitmapFactory.decodeResource(getResources(), pictureId);
            }

            mAppId = a.getString(R.styleable.FacebookLikeButton_appId);
            mContentViewId = a.getResourceId(R.styleable.FacebookLikeButton_contentViewId, 0);
        } finally {
            a.recycle();
        }
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        if (attrs != null) {
            initAttrs(context, attrs);
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                performLike();
            }
        });
    }

    protected void performLike() {
        Intent intent = new Intent(getContext(), FacebookLikeActivity.class);
        intent.putExtra(FacebookLikeActivity.PAGE_URL, mUrl);
        intent.putExtra(FacebookLikeActivity.PAGE_TITLE, mTitle);
        intent.putExtra(FacebookLikeActivity.PAGE_TEXT, mText);
        intent.putExtra(FacebookLikeActivity.PAGE_PICTURE, mPicture);
        intent.putExtra(FacebookLikeActivity.APP_ID, mAppId);
        intent.putExtra(FacebookLikeActivity.CONTENT_VIEW_ID, mContentViewId);
        getContext().startActivity(intent);
    }
}
