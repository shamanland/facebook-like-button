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
    private String mPageUrl;
    private String mPageTitle;
    private String mPageText;
    private Bitmap mPagePicture;
    private String mAppId;
    private int mContentViewId;
    private FacebookLikeOptions mOptions;

    public String getPageUrl() {
        return mPageUrl;
    }

    public void setPageUrl(String pageUrl) {
        mPageUrl = pageUrl;
    }

    public String getPageTitle() {
        return mPageTitle;
    }

    public void setPageTitle(String pageTitle) {
        mPageTitle = pageTitle;
    }

    public String getPageText() {
        return mPageText;
    }

    public void setPageText(String pageText) {
        mPageText = pageText;
    }

    public Bitmap getPagePicture() {
        return mPagePicture;
    }

    public void setPagePicture(Bitmap pagePicture) {
        mPagePicture = pagePicture;
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

    public FacebookLikeOptions getOptions() {
        return mOptions;
    }

    public void setOptions(FacebookLikeOptions options) {
        mOptions = options;
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
            mPageUrl = a.getString(R.styleable.FacebookLikeButton_pageUrl);
            mPageTitle = a.getString(R.styleable.FacebookLikeButton_pageTitle);
            mPageText = a.getString(R.styleable.FacebookLikeButton_pageText);

            int pictureId = a.getResourceId(R.styleable.FacebookLikeButton_pagePicture, 0);
            if (pictureId != 0) {
                mPagePicture = BitmapFactory.decodeResource(getResources(), pictureId);
            }

            mAppId = a.getString(R.styleable.FacebookLikeButton_appId);
            mContentViewId = a.getResourceId(R.styleable.FacebookLikeButton_contentViewId, 0);

            mOptions = new FacebookLikeOptions();
            mOptions.titleOpen = getString(a, R.styleable.FacebookLikeButton_optTitleOpen, mOptions.titleOpen);
            mOptions.titleClose = getString(a, R.styleable.FacebookLikeButton_optTitleClose, mOptions.titleClose);
            mOptions.textOpen = getString(a, R.styleable.FacebookLikeButton_optTextOpen, mOptions.textOpen);
            mOptions.textClose = getString(a, R.styleable.FacebookLikeButton_optTextClose, mOptions.textClose);
            mOptions.pictureAttrs = getString(a, R.styleable.FacebookLikeButton_optPictureAttrs, mOptions.pictureAttrs);
            mOptions.layout = FacebookLikeOptions.Layout.values()[a.getInt(R.styleable.FacebookLikeButton_optLayout, 0)];
            mOptions.action = FacebookLikeOptions.Action.values()[a.getInt(R.styleable.FacebookLikeButton_optAction, 0)];
            mOptions.showFaces = a.getBoolean(R.styleable.FacebookLikeButton_optShowFaces, mOptions.showFaces);
            mOptions.share = a.getBoolean(R.styleable.FacebookLikeButton_optShare, mOptions.share);
        } finally {
            a.recycle();
        }
    }

    private String getString(TypedArray a, int index, String defValue) {
        String read = a.getString(index);
        return read != null ? read : defValue;
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

    public void performLike() {
        Intent intent = new Intent(getContext(), FacebookLikeActivity.class);
        intent.putExtra(FacebookLikeActivity.PAGE_URL, mPageUrl);
        intent.putExtra(FacebookLikeActivity.PAGE_TITLE, mPageTitle);
        intent.putExtra(FacebookLikeActivity.PAGE_TEXT, mPageText);
        intent.putExtra(FacebookLikeActivity.PAGE_PICTURE, mPagePicture);
        intent.putExtra(FacebookLikeActivity.APP_ID, mAppId);
        intent.putExtra(FacebookLikeActivity.CONTENT_VIEW_ID, mContentViewId);
        intent.putExtra(FacebookLikeActivity.OPTIONS, mOptions);
        getContext().startActivity(intent);
    }
}
